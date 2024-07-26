#Task Management version 2

This project is a Task Management System that integrates JPA and Hibernate for data persistence and manages interactions with a PostgreSQL database.

The system includes entities for User, Task, and Project, and provides CRUD operations through JPA repositories.

Entities

User:
Has a One-to-Many relationship with the Task entity.
Has a Many-to-Many relationship with the Project entity.

Task:
Has a Many-to-One relationship with the User entity.
Has a Many-to-One relationship with the Project entity.

Project:
Has a Many-to-Many relationship with the User entity.
Has a One-to-Many relationship with the Task entity.
Layers

Controller:
A REST API that exposes various functionalities to the end-user.
Controllers are built for each entity (User, Task, Project).
Uses OpenAPI for documentation purposes. The YAML file for it is provided in this repository. You can copy it and paste it into Swagger Editor(https://editor.swagger.io/) for a user-friendly view of the APIs.

Service:
Contains all business logic to handle requests from the controller layer and return appropriate responses.
Services are built for each entity (User, Task, Project).

Repository:
Handles communication with the database.
Repositories are built for each entity (User, Task, Project).

 *The application.yml include the configuration required for connecting with the pstgresql database it includes the "url, username, password" to postgresql, and also the dialect in which we communicate with the database (how SQL is generated)

 *the pom.xml includes all dependecies needed for my application to work.
