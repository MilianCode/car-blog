package com.miliancode.carblog.dao;

import com.miliancode.carblog.models.Post;
import com.miliancode.carblog.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDAO {
    void save(User user);
    User findById(Integer id);
    List<User> findAll();
    List<User> findByUsername(String username);
    @Query("SELECT u FROM User u WHERE u.email = :email")
    User findByEmail(@Param("email") String email);
    @Query("SELECT EXISTS (SELECT 1 FROM User WHERE email = ?)")
    boolean checkIfExist(@Param("email") String email);
    String correctPassword(String email);
}
