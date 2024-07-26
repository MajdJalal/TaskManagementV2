package com.digitinary.taskmanagement2.entity;


import com.digitinary.taskmanagement2.enums.TaskStatus;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * Task class
 */
@Entity
@Table(name = "tasks")
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Task {

    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private int priority;
    private LocalDate dueDate;
    private User user;
    private Project project;

    @Id
    @Column(name = "task_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Long getId() {
        return id;
    }


    @Column(name = "task_title", nullable = false)
    public String getTitle() {
        return title;
    }

    @Column(name = "task_description")
    public String getDescription() {
        return description;
    }


    @Enumerated(EnumType.STRING)
    @Column(name = "task_status")
    public TaskStatus getStatus() {
        return status;
    }



    @Column(name = "task_priority")
    public int getPriority() {
        return priority;
    }

    @Column(name = "task_dueDate")
    public LocalDate getDueDate() {
        return dueDate;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    @ManyToOne
    @JoinColumn(name = "project_id")
    public Project getProject() {
        return project;
    }
}
