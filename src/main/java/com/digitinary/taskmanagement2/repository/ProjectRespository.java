package com.digitinary.taskmanagement2.repository;


import com.digitinary.taskmanagement2.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRespository extends JpaRepository<Project, Long> {


    List<Project> findAllByUsersId(Long userId);
}
