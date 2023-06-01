package com.miliancode.carblog.repo;

import com.miliancode.carblog.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
