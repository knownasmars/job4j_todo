# Web-application - TODO list

## Table of contents
* [Summary](#summary)
* [Tech Stack](#tech-stack)
* [Environment](#environment)
* [Project launching](#project-launching)
* [Contacts](#contacts)
* [Screenshots](#screenshots)

## Summary
This web application is a simple version of Todo list.
User can add new tasks, delete and mark them as completed. User is unlimited in adding
new tasks. The task list is executed with scrolling function for comfortable usage. 
This app stores tasks in database and you can see all kinds of tasks on the main view
in tabs such as: all / active / completed.

## Tech Stack:
- Java 17
- Spring Core 5.3.22
- Spring boot 2.7.3
- Hibernate 5.6.11
- PostrgreSQL 42.2.9
- SLF4j 1.7.36
- Liquibase 4.15.0
- Mockito 4.0.0
- Lombok 1.18.22
- Thymeleaf 3.0.15
- Bootstrap 5.2.3
- HTML, CSS

## Environment
JDK 17, IntelliJ IDEA CE 2021.3.3, Maven 3.8.1, PostrgreSQL 15, Windows 11

## Project launching
#### 1. Create SQL database 
```
    CREATE DATABASE todo;
```

#### 2. Create SQL table 
```
    CREATE TABLE tasks(
    id SERIAL PRIMARY KEY,
    description TEXT,
    created TIMESTAMP,
    done BOOLEAN,
    title TEXT
    );
```

#### 3. Add the dependencies into the project pom.xml

#### 4. To run this project, install it locally using npm:
```
    # Clone this repository
    $ git clone https://github.com/knownasmars/job4j_todo
    
    # Go into the repository
    $ cd job4j_todo
    
    # Install dependencies
    $ npm install
    
    # Run the app
    $ npm start
```

## Contacts
telegram: @knownasmars

## Screenshots 

### - Picking all tasks:
<img width="959" alt="#web_app_todo_all" src="https://user-images.githubusercontent.com/106254908/230772477-2db2d4fd-75e7-457e-9a51-0b8f5bfb52b8.png">

### - Picking active tasks:
<img width="956" alt="#web_app_todo_active" src="https://user-images.githubusercontent.com/106254908/230776549-0211b7f8-9742-44a0-8bec-1fa70ce3fc96.png">

### - Picking completed tasks:
<img width="960" alt="#web_app_todo_completed" src="https://user-images.githubusercontent.com/106254908/230776554-43ccae74-876a-425c-b9e6-faa7f664521d.png">

### - Getting an error
<img width="960" alt="#web_app_todo_404" src="https://user-images.githubusercontent.com/106254908/230772539-add1ef5f-8c57-4522-9a8c-6afec9cff762.png">