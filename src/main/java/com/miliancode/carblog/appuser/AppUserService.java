package com.miliancode.carblog.appuser;

import com.miliancode.carblog.repo.AppUserRepository;
import com.miliancode.carblog.services.token.ConfirmationToken;
import com.miliancode.carblog.services.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MESSAGE = "user with email %s not found";
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        return appUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, email)));
    }

    public String signUpUser(AppUser appUser){
        boolean userExist = appUserRepository.findByEmail(appUser.getEmail()).isPresent();
        if (userExist){
            throw new IllegalStateException("email already taken");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());

        appUser.setPassword(encodedPassword);

        appUserRepository.save(appUser);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), appUser);
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return token;
    }

    public String recoverPasswordToken(AppUser appUser){
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), appUser);
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return token;
    }

    public boolean recoverPassword(AppUser appUser, String newPassword){
        boolean userExist = appUserRepository.findByEmail(appUser.getEmail()).isPresent();
        if (userExist){
            throw new IllegalStateException("user does not exist");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(newPassword);

        appUserRepository.updatePassword(appUser.getEmail(), encodedPassword);

        return true;
    }

    public int enableAppUser(String email) {
        return appUserRepository.enableAppUser(email);
    }

//    public boolean isEnabled(String email){
//        if (appUserRepository.isEnabled(email) == 1){
//            return true;
//        }else{
//            return false;
//        }
//    }
}
