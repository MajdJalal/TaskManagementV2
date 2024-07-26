package com.digitinary.taskmanagement2.repository;

import com.digitinary.taskmanagement2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRespository extends JpaRepository<User, Long> {
}
