package com.example.taskproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class CreateTaskController {

    @FXML
    private Text instructions;
    @FXML
    private Label nameLabel;
    @FXML
    private TextField nameValue;
    @FXML
    private Label descLabel;
    @FXML
    private TextField descValue;
    @FXML
    private Label statusLabel;
    @FXML
    private ComboBox<String> statusBox;
    @FXML
    private Label dateLabel;
    @FXML
    private DatePicker dateValue;

    @FXML
    private Button createButton;
    @FXML
    private Button backButton;

    @FXML
    private Text message;

    public void initialize() {
            statusBox.getItems().addAll("Pending","In Progress","Done");
    }

    public void insertRecord(){

        String taskName = nameValue.getText();
        String taskDescription = descValue.getText();
        String taskStatus = (String) statusBox.getValue();
        LocalDate taskDate = dateValue.getValue();

        String SQL_INSERT = "INSERT INTO tasks(task_name, task_description, task_status, task_date) VALUES (?,?,?,?)";
        //Connecting to the database
        try(Connection connection = DBConnection.connect();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT)){
            preparedStatement.setString(1,taskName);
            preparedStatement.setString(2,taskDescription);
            preparedStatement.setString(3,taskStatus);
            preparedStatement.setDate(4, Date.valueOf(taskDate));
            //Execute
            preparedStatement.executeUpdate();

            message.setText("Record successfully inserted!");
            message.setVisible(true);

        } catch (SQLException e ){
            e.printStackTrace();
        }
    }

    @FXML
    public void backToMain(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TaskView.fxml"));
        Parent createParent = loader.load();
        Scene createScene = new Scene(createParent);

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(createScene);
        currentStage.show();
    }



}
