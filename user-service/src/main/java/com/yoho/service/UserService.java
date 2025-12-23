package com.yoho.service;

import com.yoho.exception.UserException;
import com.yoho.model.User;

import java.util.List;

public interface UserService {

    User createUser(User user);
    User getUserById(Long id) throws Exception;
    List<User> getAllUsers();
    void deleteUser(Long id) throws UserException;
    User updateUser(Long id, User user) throws UserException;
    User getUserFromJwt(String jwt) throws Exception;
}
