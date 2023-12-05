package com.example.dictionary.Controller;

import com.example.dictionary.base.TranslateParagraph;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class HuongDanControl {

        @FXML
        private Button BACKTOMENU;

        @FXML
        private ImageView background;

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
    @FXML
    public void initialize() {

        BACKTOMENU.setOnAction(event -> openFXMLWindow("/com/example/dictionary/Menu.fxml"));
    }


}
