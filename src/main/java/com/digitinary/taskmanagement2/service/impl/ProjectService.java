package com.digitinary.taskmanagement2.service.impl;

import com.digitinary.taskmanagement2.dto.ProjectDto;
import com.digitinary.taskmanagement2.dto.ProjectResponseDto;
import com.digitinary.taskmanagement2.entity.Project;
import com.digitinary.taskmanagement2.entity.Task;
import com.digitinary.taskmanagement2.entity.User;
import com.digitinary.taskmanagement2.enums.TaskStatus;
import com.digitinary.taskmanagement2.exception.*;
import com.digitinary.taskmanagement2.mapper.ProjectMapper;
import com.digitinary.taskmanagement2.repository.ProjectRespository;
import com.digitinary.taskmanagement2.repository.TaskRespository;
import com.digitinary.taskmanagement2.repository.UserRespository;
import com.digitinary.taskmanagement2.service.IProjectService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class ProjectService implements IProjectService {

    private static final Logger logger = LoggerFactory.getLogger(ProjectService.class);
    private final ProjectRespository projectRespository;
    private final ProjectMapper projectMapper;
    private  final TaskRespository taskRespository;
    private final UserRespository userRespository;

    public ProjectService(ProjectRespository projectRespository, ProjectMapper projectMapper, TaskRespository taskRespository, UserRespository userRespository) {
        this.projectRespository = projectRespository;
        this.projectMapper = projectMapper;
        this.taskRespository = taskRespository;
        this.userRespository = userRespository;
    }


    /**
     * maps the passed projectDto to project
     * validation of projectDto happened at the controller layer
     * @throws InvalidProjectDetailsException if any exception was thrown while saving the new project
     * @param projectDto
     */
    @Override
    public void createProject(ProjectDto projectDto) {
        Project project = projectMapper.toProject(projectDto);
        try {
            projectRespository.save(project);
        } catch (Exception e) {
            logger.error(e.getMessage() + " please try again");
            throw  new InvalidProjectDetailsException(e.getMessage());
        }

    }

    /**
     * bind the project with the task
     * @throws NotFoundProjectException if no project has an id same as the passed one
     * @throws NotFoundTaskException if no task has an id same as the passed one
     * @throws TaskAlreadyAssignedToAnotherProject task is already assigned to another project
     * @param projectId
     * @param taskId
     */
    @Override
    @Transactional
    public void addTaskToProject(Long projectId, Long taskId) {
        //get the project
        Optional<Project> optionalProject =  projectRespository.findById(projectId);
        if(optionalProject.isEmpty()) throw new NotFoundProjectException("there is no project with such Id");
        //get the task
        Optional<Task> optionalTask =  taskRespository.findById(taskId);
        if(optionalTask.isEmpty()) throw  new NotFoundTaskException("there is no task with such taskId");
        //add task to project tasks
        Project project = optionalProject.get();
        Task task = optionalTask.get();
        if(task.getProject() != null && !task.getProject().getId().equals(project.getId()) ) throw  new TaskAlreadyAssignedToAnotherProject("task is already assigned to another project");
        task.setProject(project);//update the foreign key in the task table
        taskRespository.save(task);
    }

    /**
     * @throws NotFoundProjectException if no project has an id same as the passed one
     * @param projectId
     * maps the project to a projectResponseDto
     * @return ProjectResponseDto
     */
    @Override
    public ProjectResponseDto getProject(Long projectId) {
        Optional<Project> optionalProject = projectRespository.findById(projectId);
        if(optionalProject.isEmpty()) throw new NotFoundProjectException("no project with such id ");
        Project project = optionalProject.get();
        return  projectMapper.toProjectResponseDto(project);
    }

    /**
     * decompose the project from the task
     * @throws NotFoundProjectException if no project has an id same as the passed one
     * @throws NotFoundTaskException if no task has an id same as the passed one
     * @throws TaskNotAssignedToProject if task not assigned to anu project or assigned to another project
     * @param projectId
     * @param taskId
     */
    @Override
    @Transactional
    public void removeTaskFromProject(Long projectId, Long taskId) {
        //get the project
        Optional<Project> optionalProject =  projectRespository.findById(projectId);
        if(optionalProject.isEmpty()) throw new NotFoundProjectException("there is no project with such Id");
        //get the task
        Optional<Task> optionalTask =  taskRespository.findById(taskId);
        if(optionalTask.isEmpty()) throw  new NotFoundTaskException("there is no task with such taskId");
        //
        Project project = optionalProject.get();
        Task task = optionalTask.get();
        if(task.getProject() == null || !task.getProject().getId().equals(projectId)) throw new TaskNotAssignedToProject("this task isn't assigned to this project");
        task.setProject(null); //decompose  the project from task
        taskRespository.save(task);//save changes in tasks tables
    }

    /**
     * remove the project with id == projectId
     * and remove all tasks that are associated with this project(CascadeType.REMOVE)
     * @param projectId
     */
    @Override
    public void deleteProject(Long projectId) {
        Optional<Project> optionalProject = projectRespository.findById(projectId);
        if(optionalProject.isEmpty()) throw new NotFoundProjectException("there is no project with such projectId");
        projectRespository.deleteById(projectId);
    }

    /**
     * add the user of id userId to the project of id projectId
     * @throws NotFoundProjectException if no project has an id same as the passed one
     * @throws NotFoundUserException if no user has an id same as the passed one
     * @param projectId
     * @param userId
     */
    @Override
    public void addUserToProject(Long projectId, Long userId) {
        //get the project
        Optional<Project> optionalProject =  projectRespository.findById(projectId);
        if(optionalProject.isEmpty()) throw new NotFoundProjectException("there is no project with such Id");
        //get the user
        Optional<User> optionalUser = userRespository.findById(userId);
        if(optionalUser.isEmpty()) throw  new NotFoundUserException("there is no user with such userId");
        Project project = optionalProject.get();
        //update the owning side of the relationship
        User user = optionalUser.get();
        user.getProjects().add(project);
        userRespository.save(user);
    }

    /**
     * @throws NotFoundProjectException if no project has an id same as the passed one
     * @param projectId
     * @return a rate of completeness of the tasks in the project with id projectId
     */
    @Override
    public String getProjectCompletenessRate(Long projectId) {
        //get the project
        Optional<Project> optionalProject = projectRespository.findById(projectId);
        if(optionalProject.isEmpty()) throw new NotFoundProjectException("there is no project with such Id");
        //get size of all tasks associated with this project and the num of DONE tasks
        Project project = optionalProject.get();
        Set<Task> tasks = project.getTasks();
        logger.info(String.valueOf(tasks.size()));
        int tasksSize = tasks.size();
        int doneTasksCount = (int) tasks.stream().filter(task -> task.getStatus().equals(TaskStatus.DONE)).count();
        if(tasksSize == 0)  return "Rate is 0.00%%";
        //calculate the rate
        double rate = ((double) doneTasksCount / tasksSize) * 100;
        return String.format("Rate is %.2f%%", rate);

    }

    /**
     * used to remove a user of id userId from the project of id projectId if it is already assigned
     * @throws NotFoundProjectException if no project has an id same as the passed one
     * @throws NotFoundUserException if no user has an id same as the passed one
     * @throws UserNotAssignedToThisProject if user isn't assigned to this project with is projectId
     * @param projectId
     * @param userId
     */
    @Override
    public void removeUserFromProject(Long projectId, Long userId) {
        //get the project
        Optional<Project> optionalProject = projectRespository.findById(projectId);
        if(optionalProject.isEmpty()) throw new NotFoundProjectException("there is no project with such Id");
        //get the user
        Optional<User> optionalUser =  userRespository.findById(userId);
        if(optionalUser.isEmpty()) throw new NotFoundUserException("there is no user with such userId");
        //
        Project project = optionalProject.get();
        User user = optionalUser.get();
        if(!user.getProjects().contains(project)) throw new UserNotAssignedToThisProject("user not assigned to this project");
        user.getProjects().remove(project); //decompose  the user from task
        userRespository.save(user);//save changes in tasks tables
    }
}
