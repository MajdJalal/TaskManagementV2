package com.digitinary.taskmanagement2;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Task Management REST API Documentation",
				description = "task management REST API Documentation",
				version = "v2",
				contact = @Contact(
						name = "majd alkhawaja",
						email = "majdjalkhawaja@gmail.com"
				)
		)
)
public class Taskmanagement2Application {

	public static void main(String[] args) {
		SpringApplication.run(Taskmanagement2Application.class, args);
	}

}
