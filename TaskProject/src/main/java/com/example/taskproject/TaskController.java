package com.example.taskproject;
//This class will control the view and perform the requests into the database
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskController {

    @FXML
    private Text softwareTitle;
    @FXML
    private Button createTask;
    @FXML
    private Button editTask;
    @FXML
    private Button DeleteTask;

    @FXML
    private TableView<Task> tablev;

    @FXML
    private TableColumn<Task, Integer> taskid;

    @FXML
    private TableColumn<Task, String> taskName;
    @FXML
    private TableColumn<Task, String> taskDesc;
    @FXML
    private TableColumn<Task, String> taskStatus;
    @FXML
    private TableColumn<Task, String> taskDate;
//load the table
    public void initialize(){
        taskid.setCellValueFactory(new PropertyValueFactory<>("task_id"));
        taskName.setCellValueFactory(new PropertyValueFactory<>("task_name"));
        taskDesc.setCellValueFactory(new PropertyValueFactory<>("task_description"));
        taskStatus.setCellValueFactory(new PropertyValueFactory<>("task_status"));
        taskDate.setCellValueFactory(new PropertyValueFactory<>("task_date"));

        loadTableData();
    }
//load the data into the table
    private void loadTableData(){
        try (Connection connection = DBConnection.connect();
             ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM tasks")){
            while (resultSet.next()){
                tablev.getItems().add(new Task(
                        resultSet.getInt("task_id"),
                        resultSet.getString("task_name"),
                        resultSet.getString("task_description"),
                        resultSet.getString("task_status"),
                        resultSet.getString("task_date")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //method which takes you to create form
    @FXML
    public void gotoCreate(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("newTask.fxml"));
        Parent createParent = loader.load();
        Scene createScene = new Scene(createParent);

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(createScene);
        currentStage.show();
    }

    //method which takes you to update form

    @FXML
    public void goToUpdate(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("updateTask.fxml"));
        Parent createParent = loader.load();
        Scene createScene = new Scene(createParent);

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(createScene);
        currentStage.show();
    }

    //method which takes you to delete form
    @FXML
    public void goToDelete(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("deleteTask.fxml"));
        Parent createParent = loader.load();
        Scene createScene = new Scene(createParent);

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(createScene);
        currentStage.show();
}


} //End of class
