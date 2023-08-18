# BackendApplication

Online shopping Backend-based spring-boot application

## Features
* User registration and login with JWT authentication
* Password encryption using BCrypt
* Role-based authorization with Spring Security
* Customized access denied handling
* Logout mechanism

## Technologies
* Spring Boot 3.0
* Spring Security
* JSON Web Tokens (JWT)
* BCrypt
* Maven
* Docker


## Getting Started
To get started with this project, you will need to have the following installed on your local machine:

* JDK 17+
* Maven 3+


To build and run the project, follow these steps:
* Navigate to the project directory: cd Application
* Add database "master" database to your mssql server. If you want to connect to your own DB make the required changes in application.properties file.
* Build the project: mvn clean install command in git bash.
* Run the project: mvn spring-boot:run or use the docker compose files if desktop docker is available in your System.

-> The application will be available at http://localhost:8080.