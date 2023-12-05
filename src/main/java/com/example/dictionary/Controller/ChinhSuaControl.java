package com.example.dictionary.Controller;
import com.example.dictionary.base.DatabaseConnect;
import com.example.dictionary.base.TranslateParagraph;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ChinhSuaControl {

    @FXML
    private Button DICHVANBANBUTTON;

    @FXML
    private Button GAMEBUTTON;

    @FXML
    private Button HUONGDANBUTTON;

    @FXML
    private Button LICHSUBUTTON;


    @FXML
    private Button THONGKEBUTTON;

    @FXML
    private Button TRATUBUTTON;

    @FXML
    private TextArea attributeInput;

    @FXML
    private Button BACKTOMENU;

    @FXML
    private Button ADD;

    @FXML
    private TextArea explainInput;

    @FXML
    private TextArea wordInput;

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
    private void openFXMLWindow1(String fxmlFile) { // không đóng cửa sổ hiện tại
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            AnchorPane root = loader.load();
            Scene scene = new Scene(root);
            Stage primaryStage = new Stage();
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void initialize() {
        ADD.setOnAction(event -> {
            if (DatabaseConnect.isempty(wordInput.getText())) {
                insertWord();
            } else {
                updateWord();
                openFXMLWindow1("/com/example/dictionary/PopUp1.fxml");
            }
        });
        TRATUBUTTON.setOnAction(event -> openFXMLWindow("/com/example/dictionary/TraTu.fxml"));
        DICHVANBANBUTTON.setOnAction(event -> openFXMLWindow("/com/example/dictionary/DichVanBan.fxml"));
        HUONGDANBUTTON.setOnAction(event -> openFXMLWindow("/com/example/dictionary/HuongDan.fxml"));
        LICHSUBUTTON.setOnAction(event -> openFXMLWindow("/com/example/dictionary/LichSu.fxml"));
        THONGKEBUTTON.setOnAction(event -> openFXMLWindow("/com/example/dictionary/flashCard.fxml"));
        GAMEBUTTON.setOnAction(event -> openFXMLWindow("/com/example/dictionary/PopUp.fxml"));
        BACKTOMENU.setOnAction(event -> openFXMLWindow("/com/example/dictionary/Menu.fxml"));
    }
    private void insertWord() {
        DatabaseConnect.insertWord(wordInput.getText(),explainInput.getText());
    }

    private void updateWord() {
        String explain = attributeInput.getText() + "<br>" + explainInput.getText();
        DatabaseConnect.updateWord(wordInput.getText(),explain);
    }
}
