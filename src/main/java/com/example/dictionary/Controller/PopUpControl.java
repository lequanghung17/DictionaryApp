package com.example.dictionary.Controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;

public class PopUpControl {
    @FXML
    private ImageView background;

    @FXML
    private Button QUIZZ;

    @FXML
    private Button BACKTOMENU;

    @FXML
    private Button WORDLE;
    private Popup popup;

    public void initialize() {
        // Khởi tạo Popup

        // Xử lý sự kiện khi nhấp vào button "Quizz"
        QUIZZ.setOnAction(event -> openFXMLWindow("/com/example/dictionary/Quizz.fxml"));

        // Xử lý sự kiện khi nhấp vào button "Wordle"
        WORDLE.setOnAction(event -> openFXMLWindow("/com/example/dictionary/Wordle.fxml"));
        BACKTOMENU.setOnAction(event -> openFXMLWindow("/com/example/dictionary/Menu.fxml"));
    }

    @FXML
    private void openFXMLWindow(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            AnchorPane root = loader.load();
            Scene scene = new Scene(root);
            Stage primaryStage = new Stage();
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
            primaryStage.setOnCloseRequest(e -> {
                Platform.exit();
                System.exit(0);
            });
            Stage currentStage = (Stage) background.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
