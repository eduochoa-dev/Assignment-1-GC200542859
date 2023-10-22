# Task Manager App
Simple CRUD app using JavaFX, MVC model, Mysql and Maven dependencies. 

It allows you to create, update, read, and delete tasks. 

Controls used:
-DatePicker
-ComboBox
-Button
-TextField


Prior to execute the code please:

Open XAMPP or your database admin, and execute this: 

create database task_db;

use task_db;

create table tasks(
task_id INT not null primary key auto_increment, 
task_name varchar(50) not null, 
task_description varchar(255) not null, 
task_status enum ("pending","in progress","done"),
task_date datetime default now()
);


SELECT * FROM task_db.tasks;


Then open the project code and execute it. 

Thanks!
