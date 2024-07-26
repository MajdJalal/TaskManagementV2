package com.digitinary.taskmanagement2.service.impl;


import com.digitinary.taskmanagement2.dto.UserDto;
import com.digitinary.taskmanagement2.dto.UserResponseDto;
import com.digitinary.taskmanagement2.entity.Project;
import com.digitinary.taskmanagement2.entity.Task;
import com.digitinary.taskmanagement2.entity.User;
import com.digitinary.taskmanagement2.exception.*;
import com.digitinary.taskmanagement2.mapper.UserMapper;
import com.digitinary.taskmanagement2.repository.ProjectRespository;
import com.digitinary.taskmanagement2.repository.TaskRespository;
import com.digitinary.taskmanagement2.repository.UserRespository;
import com.digitinary.taskmanagement2.service.IProjectService;
import com.digitinary.taskmanagement2.service.IUserService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
public class UserService implements IUserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRespository userRespository;
    private final UserMapper userMapper;
    private final TaskRespository taskRespository;
    private  final ProjectRespository projectRespository;
    private final IProjectService iProjectService;

    public UserService(UserRespository userRespository, UserMapper userMapper, TaskRespository taskRespository, ProjectRespository projectRespository, IProjectService iProjectService) {
        this.userRespository = userRespository;
        this.userMapper = userMapper;
        this.taskRespository = taskRespository;
        this.projectRespository = projectRespository;

        this.iProjectService = iProjectService;
    }

    /**
     *  first it maps the userDto to User
     *  then saves it using userRepository
     * @throws Exception if user details are invalid
     * @param userDto
     */
    @Override
    public void createUser(UserDto userDto) {
        User user = userMapper.toUser(userDto);
        try{
            //to catch any exception that would occur when inserting a new record
            userRespository.save(user);
        } catch (Exception e) {
            logger.error(e.getMessage() + " please try again");
            throw new InvalidUserDetailsException(e.getMessage());
        }

    }

    /**
     * @throws NotFoundUserException if no user exists with the passed id
     * decompose the user from related tasks and projects then deletes the user
     * @param userId
     */
    @Override
    @Transactional
    public void deleteUser(Long userId) {
        //get the user
        Optional<User> optionalUser = userRespository.findById(userId);
        //uncheck exception, no need to throw it in signature
        if(optionalUser.isEmpty()) throw  new NotFoundUserException("there is no user with such userId");
        //if there is a task associated with this user then u must remove the user from the task first, same applies for associated projects
        //check tasks and projects related
        List<Task> tasks = taskRespository.findAllByUserId(userId);
        List<Project> projects = projectRespository.findAllByUsersId(userId);
        //remove the user from the tasks related
        tasks.forEach(task -> removeTaskFromUser(userId, task.getId()));
        //remove the user from th projects related
        projects.forEach(project -> iProjectService.removeUserFromProject(project.getId(), userId));
        //delete the user
        userRespository.deleteById(userId);
    }

    /**
     * @throws NotFoundUserException if no use has the passed id
     * updtaes user with the userDto details(name and email)
     * @throws Exception if passed details for name or email are invalid
     * @param userId
     * @param userDto
     */
    @Transactional
    @Override
    public void updateUser(Long userId, UserDto userDto) {
        //get the user
        Optional<User> optionalUser = userRespository.findById(userId);
        if(optionalUser.isEmpty()) throw new NotFoundUserException("there is no user with such userId");
        User user = optionalUser.get();
        try{
            user.setName(userDto.getName());
            user.setEmail(userDto.getEmail());
        } catch (Exception e) {
            logger.error(e.getMessage() + " please try again");
            throw new InvalidUserDetailsException(e.getMessage());
        }
        userRespository.save(user);
    }

    /**
     * bind an existing task to an existing user
     * @throws NotFoundUserException if there is no user with the passed id
     * @throws NotFoundTaskException if there is no task with the passed id
     * @throws TaskAlreadyAssignedToAnotherUser if the task already has a userId that is not the same as the userId passed
     * @param userId
     * @param taskId
     */
    @Override
    @Transactional
    public void addTaskToUser(Long userId, Long taskId) {
        //get the user
        Optional<User> optionalUser =  userRespository.findById(userId);
        if(optionalUser.isEmpty()) throw new NotFoundUserException("there is no user with such userId");
        //get the task
        Optional<Task> optionalTask =  taskRespository.findById(taskId);
        if(optionalTask.isEmpty()) throw  new NotFoundTaskException("there is no task with such taskId");

        //add task to user tasks
        User user = optionalUser.get();
        Task task = optionalTask.get();
        if(task.getUser() != null && !task.getUser().getId().equals(user.getId()) ) throw  new TaskAlreadyAssignedToAnotherUser("task is already assigned to another user");
        task.setUser(user);//update the foreign key in the task table
        taskRespository.save(task);
    }

    /**
     * decompose an existing task from an existing user
     * @throws NotFoundUserException if there is no user with the passed id
     * @throws NotFoundTaskException if there is no task with the passed id
     * @throws TaskNotAssignedToUser if the task isn't assigned to any user or it is assigned to a user with different id
     * @param userId
     * @param taskId
     */
    @Override
    @Transactional
    public void removeTaskFromUser(Long userId, Long taskId) {
        //get the user
        Optional<User> optionalUser =  userRespository.findById(userId);
        if(optionalUser.isEmpty()) throw new NotFoundUserException("there is no user with such userId");
        //get the task
        Optional<Task> optionalTask =  taskRespository.findById(taskId);
        if(optionalTask.isEmpty()) throw  new NotFoundTaskException("there is no task with such taskId");
        //
        User user = optionalUser.get();
        Task task = optionalTask.get();
        if(task.getUser() == null || !task.getUser().getId().equals(userId)) throw new TaskNotAssignedToUser("this task isn't assigned to this user");
        task.setUser(null); //decompose  the user from task
        taskRespository.save(task);//save changes in tasks tables
    }

    /**
     * @throws NotFoundUserException if no user is associated with the passed userId
     * @param userId
     * finds the user with the id userId, map the fetched user to UserResponseDto
     * @return UserResponseDto filled with user details
     */
    @Override
    public UserResponseDto getUser(Long userId) {
        Optional<User>  optionalUser = userRespository.findById(userId);
        if(optionalUser.isEmpty()) throw  new NotFoundUserException("no user with such id");
        User user = optionalUser.get();
        return userMapper.toUserResponseDto(user);

    }
}
