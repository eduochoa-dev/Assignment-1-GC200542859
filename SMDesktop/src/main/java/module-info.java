module com.example.smdesktop {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.smdesktop to javafx.fxml;
    exports com.example.smdesktop;
}