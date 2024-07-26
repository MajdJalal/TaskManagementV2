package com.digitinary.taskmanagement2.service;

import com.digitinary.taskmanagement2.dto.UserDto;
import com.digitinary.taskmanagement2.dto.UserResponseDto;
import com.digitinary.taskmanagement2.entity.User;

public interface IUserService {
    void createUser(UserDto userDto);

    void deleteUser(Long userId);

    void updateUser(Long userId, UserDto userDto);

    void addTaskToUser(Long userId, Long taskId);

    void removeTaskFromUser(Long userId, Long taskId);

    UserResponseDto getUser(Long userId);
}
