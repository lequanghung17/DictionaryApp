package com.example.dictionary.Controller;

import com.example.dictionary.base.History;
import com.example.dictionary.base.TranslateParagraph;
import com.example.dictionary.base.Trie;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.sql.SQLException;

import java.util.ArrayList;

import java.util.List;

public class LichSuControl {
        @FXML
        private ListView<String> his;
        private ObservableList<String> searchResults ;
        @FXML
        private Button DICHVANBANBUTTON;

        @FXML
        private Button GAMEBUTTON;

        @FXML
        private Button HUONGDANBUTTON;

        @FXML
        private Button LICHSUBUTTON;

        @FXML
        private Button CHINHSUABUTTON;

        @FXML
        private Button THONGKEBUTTON;

        @FXML
        private Button TRATUBUTTON;

        @FXML
        private Button BACKTOMENU;

        @FXML
        private ImageView background;

        @FXML
        private TextArea wordMean;

        @FXML
        private Button flashcardButton;

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
        public void initialize() throws SQLException {
                loadHistory();
                TRATUBUTTON.setOnAction(event -> openFXMLWindow("/com/example/dictionary/TraTu.fxml"));
                CHINHSUABUTTON.setOnAction(event -> openFXMLWindow("/com/example/dictionary/ChinhSua.fxml"));
                HUONGDANBUTTON.setOnAction(event -> openFXMLWindow("/com/example/dictionary/HuongDan.fxml"));
                THONGKEBUTTON.setOnAction(event -> openFXMLWindow("/com/example/dictionary/flashCard.fxml"));
                GAMEBUTTON.setOnAction(event -> openFXMLWindow("/com/example/dictionary/PopUp.fxml"));
                BACKTOMENU.setOnAction(event -> openFXMLWindow("/com/example/dictionary/Menu.fxml"));
                DICHVANBANBUTTON.setOnAction(event -> openFXMLWindow("/com/example/dictionary/DichVanBan.fxml"));
                flashcardButton.setOnAction(actionEvent -> openFXMLWindow("/com/example/dictionary/flashCard.fxml"));

        }
        private void loadHistory() throws SQLException {
                his.getItems().setAll(History.getAllHis());
        }
}
