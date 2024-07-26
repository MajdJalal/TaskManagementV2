package com.digitinary.taskmanagement2.service.impl;

import com.digitinary.taskmanagement2.dto.UserDto;
import com.digitinary.taskmanagement2.dto.UserResponseDto;
import com.digitinary.taskmanagement2.entity.Task;
import com.digitinary.taskmanagement2.entity.User;
import com.digitinary.taskmanagement2.exception.NotFoundUserException;
import com.digitinary.taskmanagement2.mapper.UserMapper;
import com.digitinary.taskmanagement2.repository.TaskRespository;
import com.digitinary.taskmanagement2.repository.UserRespository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {


    @InjectMocks
    private UserService userService;

    @Mock
    private UserRespository userRespository;

    @Mock
    private TaskRespository taskRespository;

    @Mock
    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * checks that save is called once on the mapped userDto
     */
    @Test
    void should_call_save_on_user_once() {
        UserDto userDto = UserDto.builder().name("mjd").email("mjd@com").build();
        User user = User.builder().name("mjd").email("mjd@com").build();
        when(userMapper.toUser(userDto)).thenReturn(user);

        userService.createUser(userDto);
        verify(userMapper, times(1)).toUser(userDto);
        verify(userRespository, times(1)).save(user);
    }

    @Test
    void should_throw_error_when_delete_invalid_userId() {
        Optional<User> emptyUser = Optional.empty();
        when(userRespository.findById((long)1)).thenReturn(emptyUser);
        assertThrows(NotFoundUserException.class, () -> userService.deleteUser((long)1));
    }

    @Test
    void should_update_user_with_userdto_details_and_save_user_once(){
        UserDto userDto = UserDto.builder().name("fadi").email("fdi@gmail").build();
        Optional<User> user = Optional.of(User.builder().name("mjd").email("mjd@com").build());
        when(userRespository.findById((long)1)).thenReturn(user);
        userService.updateUser((long)1, userDto);
        assertEquals(user.get().getName(), userDto.getName());
        assertEquals(user.get().getEmail(), userDto.getEmail());

        verify(userRespository, times(1)).save(user.get());

    }
    @Test
    void value_of_user_should_be_updated_for_the_task(){
        Optional<User> user = Optional.of(User.builder().id((long)1).name("mjd").email("mjd@com").build());
        Optional<Task> task = Optional.of(Task.builder().title("exam").build());
        when(userRespository.findById((long)1)).thenReturn(user);
        when(taskRespository.findById((long)1)).thenReturn(task);

        userService.addTaskToUser((long)1, (long)1);
        assertNotNull(task.get().getUser());
        assertEquals(task.get().getUser().getId(), user.get().getId());
        verify(taskRespository, times(1)).save(task.get());

    }

    @Test
    void value_of_user_should_be_null_for_the_task_after_removal(){
        Optional<User> user = Optional.of(User.builder().id((long)1).name("mjd").email("mjd@com").build());
        Optional<Task> task = Optional.of(Task.builder().title("exam").build());
        when(userRespository.findById((long)1)).thenReturn(user);
        when(taskRespository.findById((long)1)).thenReturn(task);

        userService.addTaskToUser((long)1, (long)1);
        assertNotNull(task.get().getUser());
        assertEquals(task.get().getUser().getId(), user.get().getId());

        verify(taskRespository, times(1)).save(task.get());

    }

    @Test
    void should_get_userResponseDto_when_fetching_with_userId(){
        Optional<User> user = Optional.of(User.builder().id((long)1).name("mjd").email("mjd@com").build());
        UserResponseDto userResponseDto = UserResponseDto.builder().name("mjd").email("mjd@com").build();
        when(userRespository.findById((long)1)).thenReturn(user);
        when(userMapper.toUserResponseDto(user.get())).thenReturn(userResponseDto);

        userService.getUser((long)1);
        assertEquals(user.get().getName(), userResponseDto.getName());
        assertEquals(user.get().getEmail(), userResponseDto.getEmail());



    }



}