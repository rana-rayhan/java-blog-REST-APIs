package com.blog.services;

import com.blog.payload.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<UserDto> getAllUser();

    UserDto getUserById(Integer userId);

    UserDto createUser(UserDto userDto);

    UserDto updateUser(UserDto userDto, Integer userId);

    void deleteUser(Integer userId);

}
