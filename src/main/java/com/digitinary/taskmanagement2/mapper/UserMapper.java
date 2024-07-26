package com.digitinary.taskmanagement2.mapper;

import com.digitinary.taskmanagement2.dto.UserDto;
import com.digitinary.taskmanagement2.dto.UserResponseDto;
import com.digitinary.taskmanagement2.entity.User;
import org.springframework.stereotype.Component;


@Component
public class UserMapper {


    public User toUser(UserDto userDto) {
        return User.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .build();
    }

    public UserDto toUserDto(User user) {
        return UserDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public UserResponseDto toUserResponseDto(User user) {
        return UserResponseDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .tasks(user.getTasks())
                .projects(user.getProjects())
                .build();
    }
}
