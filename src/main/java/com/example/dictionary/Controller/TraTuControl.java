package com.example.dictionary.Controller;

import com.example.dictionary.base.DatabaseConnect;
import com.example.dictionary.base.SynonymAPI;
import com.example.dictionary.base.TextToSpeech;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.stage.Stage;



import java.sql.SQLException;
import java.util.ArrayList;

public class TraTuControl {

    @FXML
    private Button CHINHSUABUTTON;

    @FXML
    private TextField search;

    @FXML
    private WebView meaning;


    @FXML
    private Button LICHSUBUTTON;

    @FXML
    private Button BACKTOMENU;

    @FXML
    private Button GAMEBUTTON;

    @FXML
    private Button DICHVANBANBUTTON;

    @FXML
    private Button TRATUBUTTON;

    @FXML
    private Button HUONGDANBUTTON;

    @FXML
    private Button THONGKEBUTTON;

    @FXML
    private ImageView background;

    @FXML
    private Button speaker;

    private String word = " ";



    public void setword(String w) {
        this.word = w;
        if (!word.equals(" ")) {
            search.setText(word);
            ArrayList<String> s = SynonymAPI.getSynonym(word);
            String synonym = SynonymAPI.convert(s);
            meaning.getEngine().loadContent(DatabaseConnect.lookUpWord(word) + "<br>" + synonym, "text/html");
            word = " ";
        }
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

    @FXML
    public void initialize() {
        new DatabaseConnect(); // sẽ lâu khi mở cửa sổ này vì phải load database
        try {
            DatabaseConnect.loadDatabase();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        search.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String word = search.getText();
                ArrayList<String> s = SynonymAPI.getSynonym(word);
                String synonym = SynonymAPI.convert(s);
                meaning.getEngine().loadContent(DatabaseConnect.lookUpWord(word) + "<br>" + synonym, "text/html");
            }
        });
        speaker.setOnAction(actionEvent -> playVoice());
        DICHVANBANBUTTON.setOnAction(event -> openFXMLWindow("/com/example/dictionary/DichVanBan.fxml"));
        CHINHSUABUTTON.setOnAction(event -> openFXMLWindow("/com/example/dictionary/ChinhSua.fxml"));
        HUONGDANBUTTON.setOnAction(event -> openFXMLWindow("/com/example/dictionary/HuongDan.fxml"));
        LICHSUBUTTON.setOnAction(event -> openFXMLWindow("/com/example/dictionary/LichSu.fxml"));
        THONGKEBUTTON.setOnAction(event -> openFXMLWindow("/com/example/dictionary/flashCard.fxml"));
        GAMEBUTTON.setOnAction(event -> openFXMLWindow("/com/example/dictionary/PopUp.fxml"));
        BACKTOMENU.setOnAction(event -> openFXMLWindow("/com/example/dictionary/Menu.fxml"));
    }
    @FXML
    public void playVoice() {
        String source = search.getText();
        TextToSpeech.playVoice(source);
    }
}