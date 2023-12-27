package com.blog.services;

import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payload.UserDto;
import com.blog.repositories.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;

    // Convert UserDto into User model
    public User dtoToUser(UserDto u) {
        return modelMapper.map(u, User.class);
    }

    // Convert User into UserDto model
    public UserDto userToDto(User u) {
        return modelMapper.map(u, UserDto.class);
    }

    // Retrieve all users
    @Override
    public List<UserDto> getAllUser() {
        List<User> userList = userRepo.findAll();
        List<UserDto> userDtoList = userList.stream().map((user) -> userToDto(user)).collect(Collectors.toList());
        return userDtoList;
    }

    // Retrieve a user by ID
    @Override
    public UserDto getUserById(Integer userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        UserDto userDto1 = this.userToDto(user);
        return userDto1;
    }

    // Create a new user
    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.dtoToUser(userDto);
        User savedUser = userRepo.save(user);
        return this.userToDto(savedUser);
    }

    // Update a user by ID
    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user = userRepo.findById(userId).orElseThrow();

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());

        User savedUser = userRepo.save((user));
        UserDto userDto1 = this.userToDto(savedUser);
        return userDto1;
    }

    // Delete a user by ID
    @Override
    public void deleteUser(Integer userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        userRepo.delete(user);
    }
}