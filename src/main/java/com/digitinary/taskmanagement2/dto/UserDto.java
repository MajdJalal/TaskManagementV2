package com.digitinary.taskmanagement2.dto;



import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    @NotBlank
    private String name;
    @Email(message = "email should be valid")
    private String email;
}
