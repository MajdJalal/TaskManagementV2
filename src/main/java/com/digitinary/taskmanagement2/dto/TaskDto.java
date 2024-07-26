package com.digitinary.taskmanagement2.dto;

import com.digitinary.taskmanagement2.enums.TaskStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;


@Data
@Builder
public class TaskDto {

    @NotBlank
    private String title;
    @NotNull
    private String description;
    @NotNull
    private TaskStatus status;
    private int priority = 0;//default is 0
    @NotNull
    @FutureOrPresent
    private LocalDate dueDate;

}
