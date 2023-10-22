package com.example.taskproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class UpdateTaskController {

    @FXML
    private Label taskLabel;
    @FXML
    private ComboBox<String> taskBox;
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
    private Button updateButton;
    @FXML
    private Button backButton;
    @FXML
    private Text message;


    public void initialize() {
        // Llena el ComboBox taskComboBox con las tareas disponibles
        fillTaskComboBox();
        // Llena el ComboBox statusBox con los valores del ENUM
        statusBox.getItems().addAll("Pending", "In Progress", "Done");
    }

    private void fillTaskComboBox() {
        try {
            String query = "SELECT task_name FROM tasks";
            Connection connection = DBConnection.connect();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<String> taskNames = new ArrayList<>();
            while (resultSet.next()) {
                taskNames.add(resultSet.getString("task_name"));
            }
            taskBox.getItems().addAll(taskNames);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadTaskDetails() {
        String selectedTaskName = taskBox.getValue();
        if (selectedTaskName != null) {
            try {
                String query = "SELECT task_description, task_status, task_date FROM tasks WHERE task_name = ?";
                Connection connection = DBConnection.connect();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, selectedTaskName);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    String taskDescription = resultSet.getString("task_description");
                    String taskStatus = resultSet.getString("task_status");
                    String taskDate = resultSet.getString("task_date");

                    // Llena los campos con los datos de la tarea seleccionada
                    nameValue.setText(selectedTaskName);
                    descValue.setText(taskDescription);
                    statusBox.setValue(taskStatus);
                    dateValue.setValue(LocalDate.parse(taskDate));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateRecord() {
        String selectedTaskName = taskBox.getValue();
        String taskName = nameValue.getText();
        String taskDescription = descValue.getText();
        String taskStatus = statusBox.getValue();
        String taskDate = dateValue.getValue().toString();

        String SQL_UPDATE = "UPDATE tasks SET task_name = ?, task_description = ?, task_status = ?, task_date = ? WHERE task_name = ?";

        try (Connection connection = DBConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE)) {
            preparedStatement.setString(1, taskName);
            preparedStatement.setString(2, taskDescription);
            preparedStatement.setString(3, taskStatus);
            preparedStatement.setString(4, taskDate);
            preparedStatement.setString(5, selectedTaskName);
            preparedStatement.executeUpdate();
            message.setText("Record successfully updated!");
            message.setVisible(true);
        } catch (SQLException e) {
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

} // end of the class
