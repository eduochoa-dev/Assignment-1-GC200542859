package com.example.taskproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DeleteTaskController {

    @FXML
    private Text instructions;
    @FXML
    private Label taskLabel;
    @FXML
    private ComboBox<String> taskBox;
    @FXML
    private Button deleteButton;
    @FXML
    private Button backButton;
    @FXML
    private Text message;

    public void initialize() {

        fillTaskComboBox();
    }
    // Fill the task combobox with the available tasks
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


    public void deleteTask(){
        String selectedTaskName = taskBox.getValue();
        String SQL_DELETE = "DELETE FROM tasks where task_name = ?";
        try (Connection connection = DBConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE)) {
            preparedStatement.setString(1, selectedTaskName);
            preparedStatement.executeUpdate();
            message.setText("Record successfully Deleted!");
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

} //End of class
