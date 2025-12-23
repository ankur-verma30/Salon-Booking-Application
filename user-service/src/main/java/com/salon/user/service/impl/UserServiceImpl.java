package com.salon.user.service.impl;

import com.salon.user.exception.UserException;
import com.salon.user.entity.User;
import com.salon.user.payload.response.dto.KeyCloakUserDTO;
import com.salon.user.repository.UserRepository;
import com.salon.user.service.KeycloakService;
import com.salon.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    private final KeycloakService keycloakService;

    @Override
    public User getUserFromJwt(String jwt) throws Exception {
        KeyCloakUserDTO keyCloakUserDTO = keycloakService.fetchUserProfileByJwt(jwt);
        return userRepository.findByEmail(keyCloakUserDTO.getEmail());
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) throws Exception{
        return userRepository.findById(id)
                .orElseThrow(() -> new UserException("User not found"));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Long id) throws UserException {
        Optional<User> otp = userRepository.findById(id);
        if(otp.isEmpty()){
            throw new UserException("User not found with id " + id);
        }
        userRepository.deleteById(id);

    }

    @Override
    public User updateUser(Long id, User user) throws UserException {
        Optional<User> otp = userRepository.findById(id);
        if(otp.isEmpty()){
            throw new UserException("User not found with id " + id);
        }
        User existingUser = otp.get();
        existingUser.setFullName(user.getFullName());
        existingUser.setEmail(user.getEmail());
        existingUser.setRole(user.getRole());
        existingUser.setUsername(user.getUsername());

        return userRepository.save(existingUser);
    }
}
