# Social App Backend
Backend Application for the Social App to host the REST APIs. 
Hosted at https://social-app-apis.herokuapp.com/

## Features 
* Uses Spring Security to authorize using JSON Web Tokens
* Uses BCrypt Password Encoder to store the password in the database
* Uses Swagger UI for API documentation
* Uses Pagination for APis returning lists to reduce load on the database
* Standardized error response object structure
* Allows user to login, register, view their details, edit their details, change their password and delete their profile
* Allows user to follow other users, unfollow them, view the users they followed and get a page of users they can follow
* Allows user to add posts, delete them and view posts from users they followed

## Tech Stack
* Java 
* Spring Boot Framework
* MySQL Database
