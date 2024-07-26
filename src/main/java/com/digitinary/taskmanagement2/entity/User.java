package com.digitinary.taskmanagement2.entity;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.util.List;
import java.util.Set;


/**
 * user class
 */
@Entity
@Table(name =  "users")
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id") //indicates that a property of the object will be used as the identifier.helps to eliminate the circular reference
public class User {

    private  Long id;
    private String name;
    private String email;
    private Set<Task> tasks;
    private Set<Project> projects;

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Long getId() {
        return id;
    }

    @Column(name = "user_name", nullable = false, unique = true)
    public String getName() {
        return name;
    }

    @Column(name = "user_email", unique = true, nullable = false)
    public String getEmail() {
        return email;
    }

    @OneToMany(mappedBy = "user")
    public Set<Task> getTasks() {
        return tasks;
    }

    @ManyToMany
    @JoinTable(
            name = "users_projects",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    public Set<Project> getProjects() {
        return projects;
    }
}
