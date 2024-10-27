package com.sysdistribue.auth_api.controllers;

import com.sysdistribue.auth_api.dto.RegisterUserDto;
import com.sysdistribue.auth_api.dto.UserDto;
import com.sysdistribue.auth_api.entities.User;
import com.sysdistribue.auth_api.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequestMapping("/api/users")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();
        UserDto userDto = new UserDto();
        userDto.setId(currentUser.getId());
        userDto.setEmail(currentUser.getEmail());
        userDto.setFullName(currentUser.getFullName());

        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDto>> allUsers() {
        List <User> users = userService.allUsers();

        List<UserDto> userDtos = users.stream().map(user -> {
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setEmail(user.getEmail());
            userDto.setFullName(user.getFullName());
            return userDto;
        }).toList();

        return ResponseEntity.ok(userDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Integer id) {
        User user = userService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setFullName(user.getFullName());

        return ResponseEntity.ok(userDto);

    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Integer id, @RequestBody RegisterUserDto registerUserDto) {
        User updatedUser = userService.updateUser(id, registerUserDto);

        UserDto userDto = new UserDto();
        userDto.setId(updatedUser.getId());
        userDto.setEmail(updatedUser.getEmail());
        userDto.setFullName(updatedUser.getFullName());

        return ResponseEntity.ok(userDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
