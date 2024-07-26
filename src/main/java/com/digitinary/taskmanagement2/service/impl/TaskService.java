package com.digitinary.taskmanagement2.service.impl;


import com.digitinary.taskmanagement2.dto.TaskDto;
import com.digitinary.taskmanagement2.dto.TaskResponseDto;
import com.digitinary.taskmanagement2.entity.Task;
import com.digitinary.taskmanagement2.entity.User;
import com.digitinary.taskmanagement2.enums.TaskStatus;
import com.digitinary.taskmanagement2.exception.*;
import com.digitinary.taskmanagement2.mapper.TaskMapper;
import com.digitinary.taskmanagement2.repository.TaskRespository;
import com.digitinary.taskmanagement2.repository.UserRespository;
import com.digitinary.taskmanagement2.runnable.RunnableTask;
import com.digitinary.taskmanagement2.service.ITaskService;
import com.sun.source.util.TaskListener;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Service
public class TaskService  implements ITaskService {

    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);
    private final TaskRespository taskRespository;
    private final TaskMapper taskMapper;


    public TaskService(TaskRespository taskRespository, TaskMapper taskMapper, UserRespository userRespository) {
        this.taskRespository = taskRespository;
        this.taskMapper = taskMapper;
    }

    /**
     * create a new task based on the taskDto details
     * @throws InvalidTaskDetailsException if any exception was thrown while saving the task
     * @param taskDto
     */
    @Override
    public void createTask(TaskDto taskDto) {
        Task task = taskMapper.toTask(taskDto);
        try {
            taskRespository.save(task);
        } catch (Exception e) {
            logger.error(e.getMessage() + " please try again");
            throw  new InvalidTaskDetailsException(e.getMessage());
        }

    }

    /**
     * delete an existing task with id taskId
     * @throws NotFoundTaskException if no tasks found with the passed  taskId
     * @param taskId
     */
    @Override
    public void deleteTask(Long taskId) {
        //get the task
        Optional<Task> optionalTask = taskRespository.findById(taskId);
        if(optionalTask.isEmpty()) throw new NotFoundTaskException("there is no task with such userId");
        taskRespository.deleteById(taskId);
    }

    /**
     * update task with id --> taskId based on the taskDto details
     * taskDto values are already validated in the controller
     * @throws NotFoundTaskException if no tasks found with the passed  taskId
     * @param taskId
     * @param taskDto
     */
    @Override
    @Transactional
    public void updateTask(Long taskId, TaskDto taskDto) {
        //get the task
        Optional<Task> optionalTask = taskRespository.findById(taskId);
        if(optionalTask.isEmpty()) throw  new NotFoundTaskException("there is no task with such userId");
        Task task = optionalTask.get();
        //update values
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setStatus(taskDto.getStatus());
        task.setPriority(taskDto.getPriority());
        task.setDueDate(taskDto.getDueDate());
        //save
        taskRespository.save(task);
    }

    /**
     * @throws NotFoundTaskException if no tasks found with the passed  taskId
     * @param taskId
     * @return TaskResponseDto with the task details
     */
    @Override
    public TaskResponseDto getTask(Long taskId) {
        Optional<Task> optionalTask = taskRespository.findById(taskId);
        if(optionalTask.isEmpty()) throw new NotFoundTaskException("no task with such id");
        Task task = optionalTask.get();
        return taskMapper.toTaskResponseDto(task);
    }

    /**
     * @throws NotFoundTaskException if no tasks found with the passed  taskId
     * map the task into a runnableTask, assigning one thread
     * from the existing thread pool, to handle the runnableTask run method
     * which contains the logic for processing the task
     * @param taskId
     */
    @Override
    public void processTask(Long taskId) {
        //get the task
        Optional<Task> optionalTask = taskRespository.findById(taskId);
        if(optionalTask.isEmpty()) throw  new NotFoundTaskException("no task with such id");
        Task task = optionalTask.get();
        RunnableTask runnableTask = taskMapper.toRunnableTask(task);
        executorService.execute(runnableTask);
    }

    /**
     * @return a list of all done tasks
     */
    @Override
    public List<Task> getDoneTasks() {
        return taskRespository.findAllByStatus(TaskStatus.DONE);
    }

    /**
     * @return a list of all waiting tasks
     */
    @Override
    public List<Task> getWaitingTasks() {
        return taskRespository.findAllByStatus(TaskStatus.WAITING);
    }

    /**
     * @return a list of all expired tasks
     */
    @Override
    public List<Task> getExpiredTasks() {
        return taskRespository.findAllByDueDateBeforeAndStatus(LocalDate.now(), TaskStatus.WAITING);
    }

    /**
     * @param priority
     * @return a list of all tasks of the passed priority
     */
    @Override
    public List<Task> getTasksOfPriority(Integer priority) {
        return taskRespository.findAllByPriority(priority);
    }
}
