package com.example.dictionary.Controller;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import static com.example.dictionary.Controller.QuizzControl.answerforallList;

public class ResultControl {

    @FXML
    public Label congrat;

    @FXML
    public Label result;

    @FXML
    public Label trueans;

    @FXML
    public Label falseans;

    @FXML
    public Label answerforall;

    @FXML
    public Button backButton;

    @FXML
    public Button replayButton;

    @FXML
    public ImageView background;

    private static List<String> getQuizResultList = new ArrayList<>();

    int correct;
    int wrong;

    @FXML
    private void initialize() {


        correct = QuizzControl.correct;
        wrong = QuizzControl.wrong;

        trueans.setText( correct + " / 10" );
        falseans.setText(wrong + " / 10");

        StringBuilder allAnswers = new StringBuilder();
        int size = answerforallList.size();
        for (int i = 0; i < size; i++) {
            allAnswers.append(i + 1).append(": ").append(answerforallList.get(i));
        }

        answerforall.setText(allAnswers.toString());





        replayButton.setOnAction(event -> {
            openFXMLWindow("/com/example/dictionary/Quizz.fxml");
            QuizzControl.resetAnswersList();
        });
        backButton.setOnAction(event -> {
            openFXMLWindow("/com/example/dictionary/Menu.fxml");
            QuizzControl.resetAnswersList();
        });



    }

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

    public static void addResultList(String answer) {
        getQuizResultList.add(answer);
    }

    public static String getQuizResult() {
        int cor_ = 0;
        int wr_ = 0;

        for (String answer : answerforallList) {
            if (answer.equals("Correct")) {
                cor_++;
            } else {
                wr_++;
            }
        }

        StringBuilder result = new StringBuilder();
        result.append("Tổng kết quả:\n");
        result.append("Số câu đúng: ").append(cor_).append("\n");
        result.append("Số câu sai: ").append(wr_);

        return result.toString();
    }

}
