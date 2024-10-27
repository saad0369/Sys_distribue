package com.sysdistribue.auth_api.services;

import com.sysdistribue.auth_api.dto.RegisterUserDto;
import com.sysdistribue.auth_api.entities.User;
import com.sysdistribue.auth_api.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> allUsers() {
        List<User> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return users;
    }

    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    public User updateUser(Integer id, RegisterUserDto registerUserDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        user.setEmail(registerUserDto.getEmail());
        user.setFullName(registerUserDto.getFullName());
        user.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));

        return userRepository.save(user);
    }

    public void deleteUser(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        userRepository.delete(user);
    }
}
