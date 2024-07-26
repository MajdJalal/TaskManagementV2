package com.digitinary.taskmanagement2.entity;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

/**
 * project class
 */

@Entity
@Table(name = " projects")
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Project {


    private Long id;
    private String name;
    private Set<User> users;
    private Set<Task> tasks;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "project_id")
    public Long getId() {
        return id;
    }
    @Column(name = "project_name")
    public String getName() {
        return name;
    }

    @ManyToMany(mappedBy = "projects")
    public Set<User> getUsers() {
        return users;
    }

    @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
    public Set<Task> getTasks() {
        return tasks;
    }
}
