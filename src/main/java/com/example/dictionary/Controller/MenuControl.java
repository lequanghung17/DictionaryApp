package com.example.dictionary.Controller;

import com.example.dictionary.base.DatabaseConnect;
import com.example.dictionary.base.History;
import com.example.dictionary.base.Trie;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;


public class MenuControl {

    @FXML
    private Button ChinhSua;

    @FXML
    private Button DichVanBan;

    @FXML
    private Button Game;

    @FXML
    private Button HuongDan;

    @FXML
    private Button LichSu;

    @FXML
    private Button ThongKe;

    @FXML
    private ImageView background;

    @FXML
    private ListView<String> listHistory;

    @FXML
    private TextField search;


    private String word;



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
            Stage currentStage = (Stage) background.getScene().getWindow(); // lấy cửa sổ hiện tại từ background
            currentStage.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private void openFXMLWindowTraTu(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            AnchorPane root = loader.load();
            Scene scene = new Scene(root);
            Stage primaryStage = new Stage();
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            TraTuControl controller = loader.getController();
            controller.setword(word);
            primaryStage.show();
            primaryStage.setOnCloseRequest(e -> {
                Platform.exit();
                System.exit(0);
            });
            Stage currentStage = (Stage) background.getScene().getWindow(); // lấy cửa sổ hiện tại từ background
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void setGame() {}

    public void open(){
        listHistory.setVisible(true);
        listHistory.requestLayout();

    }

    public void close(){
            listHistory.setVisible(false);
            listHistory.requestLayout();
    }

    @FXML
    public void initialize() {
        new DatabaseConnect(); // sẽ lâu khi mở cửa sổ này vì phải load database
        try {
            DatabaseConnect.loadDatabase();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        listHistory.setVisible(false);
        //searchResults = FXCollections.observableArrayList();
        //listHistory.setItems(searchResults);
        search.setOnKeyReleased(event -> {
            String input = search.getText();
            if (!input.isEmpty()) {
                open();
                listHistory.getItems().setAll(Trie.searchList(input)); //khi nhập thì hiện gợi ý
            } else {
                try {
                    listHistory.getItems().setAll(History.getAllHis()); // khi ko nhập j thì hiện lịch sử
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        listHistory.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                word = listHistory.getSelectionModel().getSelectedItem();
                openFXMLWindowTraTu("/com/example/dictionary/TraTu.fxml");
            }
        });
        background.setOnMouseClicked(event -> {  // muốn ẩn list view thì nhấn vào background
            close();
        });
        search.setOnMouseClicked(event -> {  //nhấn vào search thì hiện lịch sử
            String input = search.getText();
            if (input.isEmpty()) {
                open();
                try {
                    listHistory.getItems().setAll(History.getAllHis());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        search.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                word = search.getText();
                openFXMLWindowTraTu("/com/example/dictionary/TraTu.fxml");
            }
        });
        DichVanBan.setOnAction(event -> openFXMLWindow("/com/example/dictionary/DichVanBan.fxml"));
        ChinhSua.setOnAction(event -> openFXMLWindow("/com/example/dictionary/ChinhSua.fxml"));
        LichSu.setOnAction(event -> openFXMLWindow("/com/example/dictionary/LichSu.fxml"));
        ThongKe.setOnAction(event -> openFXMLWindow("/com/example/dictionary/flashCard.fxml"));
        HuongDan.setOnAction(event -> openFXMLWindow("/com/example/dictionary/HuongDan.fxml"));
        Game.setOnAction(event -> openFXMLWindow("/com/example/dictionary/PopUp.fxml"));
    }

}


