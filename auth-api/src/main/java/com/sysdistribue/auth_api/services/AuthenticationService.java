package com.sysdistribue.auth_api.services;

import com.sysdistribue.auth_api.dto.LoginUserDto;
import com.sysdistribue.auth_api.dto.RegisterUserDto;
import com.sysdistribue.auth_api.entities.Role;
import com.sysdistribue.auth_api.entities.User;
import com.sysdistribue.auth_api.repositories.RoleRepository;
import com.sysdistribue.auth_api.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signup(RegisterUserDto input) {
        User user = new User();
        user.setFullName(input.getFullName());
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));

        Role userRole = roleRepository.findByName(input.getRole());
        if (userRole == null) {
            throw new IllegalArgumentException("Role not found: " + input.getRole());
        }

        user.setRoles(new HashSet<>(Set.of(userRole)));

        return userRepository.save(user);
    }

    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow();
    }

}
