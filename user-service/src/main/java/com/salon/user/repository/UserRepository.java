package com.salon.user.repository;

import com.salon.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email); // optional class should have been used to prevent from null pointer exception
}
