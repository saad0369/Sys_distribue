package com.sysdistribue.auth_api.controllers;

import com.sysdistribue.auth_api.entities.User;
import com.sysdistribue.auth_api.dto.LoginUserDto;
import com.sysdistribue.auth_api.dto.RegisterUserDto;
import com.sysdistribue.auth_api.dto.UserDto;
import com.sysdistribue.auth_api.responses.LoginResponse;
import com.sysdistribue.auth_api.services.AuthenticationService;
import com.sysdistribue.auth_api.services.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/users/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;


    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }


    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);

        UserDto userDto = new UserDto();
        userDto.setId(registeredUser.getId());
        userDto.setEmail(registeredUser.getEmail());
        userDto.setFullName(registeredUser.getFullName());

        return ResponseEntity.ok(userDto);
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }
}
