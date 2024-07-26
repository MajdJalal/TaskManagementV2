package com.digitinary.taskmanagement2.repository;

import com.digitinary.taskmanagement2.entity.Task;
import com.digitinary.taskmanagement2.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface TaskRespository  extends JpaRepository<Task, Long> {
    Optional<Task> findByUserId(Long userId);

    Optional<Task> findByProjectId(Long projectId);

    List<Task> findAllByStatus(TaskStatus done);

    List<Task> findAllByDueDateBeforeAndStatus(LocalDate now, TaskStatus taskStatus);

    List<Task> findAllByPriority(Integer priority);


    List<Task> findAllByUserId(Long userId);


}
