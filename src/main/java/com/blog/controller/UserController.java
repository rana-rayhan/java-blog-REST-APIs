package com.blog.controller;

import com.blog.payload.UserDto;
import com.blog.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Retrieve all users
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        // Retrieve the list of user DTOs from the UserService
        List<UserDto> userDtoList = userService.getAllUser();
        // Return the list of user DTOs with an ACCEPTED HTTP status
        return new ResponseEntity<>(userDtoList, HttpStatus.ACCEPTED);
    }

    // Retrieve a user by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") int id) {
        // Retrieve a user DTO by ID from the UserService
        UserDto userDto = userService.getUserById(id);
        // Return the user DTO with an ACCEPTED HTTP status
        return new ResponseEntity<>(userDto, HttpStatus.ACCEPTED);
    }

    // Create a new user
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto data) {
        // Create a new user using the provided user data
        UserDto userData = userService.createUser(data);
        // Return the created user DTO with a CREATED HTTP status
        return new ResponseEntity<>(userData, HttpStatus.CREATED);
    }

    // Delete a user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") int id) {
        // Delete a user by ID using the UserService
        userService.deleteUser(id);
        return new ResponseEntity<>("User is deleted successfully", HttpStatus.OK);
    }

    // Update a user by ID
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto data, @PathVariable("id") int id) {
        // Update a user by ID using the provided user data
        UserDto userData = userService.updateUser(data, id);
        // Return the updated user DTO with an ACCEPTED HTTP status
        return new ResponseEntity<>(userData, HttpStatus.ACCEPTED);
    }

}
