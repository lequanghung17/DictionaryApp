package com.example.dictionary.Controller;
import com.example.dictionary.base.TextToSpeech ;
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

public class DichVanBanControl {


    @FXML
    private Button GAMEBUTTON;

    @FXML
    private Button HUONGDANBUTTON;

    @FXML
    private Button LICHSUBUTTON;

    @FXML
    private TextArea LangOut;

    @FXML
    private Button CHINHSUABUTTON;

    @FXML
    private Button THONGKEBUTTON;

    @FXML
    private Button TRATUBUTTON;

    @FXML
    private Button BACKTOMENU;

    @FXML
    private Button TransButton;

    @FXML
    private Button lang1;

    @FXML
    private Button lang2;
    @FXML
    private Button speaker;

    @FXML
    private TextArea LangIn;
    @FXML
    private Button swapLang;
    @FXML
    private ImageView background;



    private boolean EnToVi = true;



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
        TransButton.setOnAction(event -> {
            String textIn = LangIn.getText();
            String translatedText;
            if(!EnToVi){
                translatedText = TranslateParagraph.translateParagraphVitoEn(textIn);
            }
            else {
                translatedText = TranslateParagraph.translateParagraphEntoVi(textIn);
            }
            LangOut.setText(translatedText);
        });
        speaker.setOnAction(actionEvent -> playVoice());
        swapLang.setOnAction(event -> swapLang() );
        TRATUBUTTON.setOnAction(event -> openFXMLWindow("/com/example/dictionary/TraTu.fxml"));
        CHINHSUABUTTON.setOnAction(event -> openFXMLWindow("/com/example/dictionary/ChinhSua.fxml"));
        HUONGDANBUTTON.setOnAction(event -> openFXMLWindow("/com/example/dictionary/HuongDan.fxml"));
        LICHSUBUTTON.setOnAction(event -> openFXMLWindow("/com/example/dictionary/LichSu.fxml"));
        THONGKEBUTTON.setOnAction(event -> openFXMLWindow("/com/example/dictionary/flashCard.fxml"));
        GAMEBUTTON.setOnAction(event -> openFXMLWindow("/com/example/dictionary/PopUp.fxml"));
        BACKTOMENU.setOnAction(event -> openFXMLWindow("/com/example/dictionary/Menu.fxml"));
    }

//
    @FXML
    public  void  translateParagraphEntoVi() {
        String source = LangIn.getText();
        LangOut.setText(EnToVi ? TranslateParagraph.translateParagraphEntoVi(source) : TranslateParagraph.translateParagraphVitoEn(source));
    }

    @FXML
    public void playVoice() {
        String source = LangIn.getText();
        TextToSpeech.playVoice(source);
    }

    @FXML
    public  void  swapLang() {
        EnToVi = !EnToVi;
        if ( EnToVi) {
            speaker.setVisible(true);
            lang1.setText("English");
            lang2.setText("Vietnamese");
        } else {
            speaker.setVisible(false);
            lang2.setText("English");
            lang1.setText("Vietnamese");
        }
    }
}
