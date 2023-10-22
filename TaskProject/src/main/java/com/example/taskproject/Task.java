package com.example.taskproject;
//This class is the blueprint for the tasks to be included in the dataView
public class Task {

    private int task_id;
    private String task_name;
    private String task_description;
    private String task_status;
    private String task_date;
//Constructor
    public Task(int id, String name, String description, String status, String date){
        this.task_id = id;
        this.task_name = name;
        this.task_description = description;
        this.task_status = status;
        this.task_date = date;
    }

//Since we are going to handle the insertions into the database using the controller, this class only needs getters  to display the tasks
// in the tableView
    public int getTask_id() {
        return task_id;
    }


    public String getTask_name() {
        return task_name;
    }

    public String getTask_description() {
        return task_description;
    }


    public String getTask_status() {
        return task_status;
    }

    public String getTask_date() {
        return task_date;
    }


}
