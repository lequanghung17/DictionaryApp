package com.example.dictionary.Controller;

import com.example.dictionary.base.History;
import com.example.dictionary.base.Trie;
import com.example.dictionary.base.flashCard;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.geometry.Pos;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FlashcardControl {
    @FXML
    private Button BACKTOMENU;

    @FXML
    private Button BackButton;

    @FXML
    private Button CHINHSUABUTTON;

    @FXML
    private Button DICHVANBANBUTTON;

    @FXML
    private Button GAMEBUTTON;

    @FXML
    private Button HUONGDANBUTTON;

    @FXML
    private Button LICHSUBUTTON;

    @FXML
    private Button NextButton;

    @FXML
    private Button THONGKEBUTTON;

    @FXML
    private Button TRATUBUTTON;

    @FXML
    private ImageView background;

    @FXML
    private WebView flashcard;
    private flashCard newFlashcard;
    private boolean isQuestionDisplayed = true;

    public void setFlashcard (flashCard newFlashcard) {
        this.newFlashcard= newFlashcard;
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
    public void initialize() throws SQLException {
        flashcard.getStyleClass().add("flashcard-text-area-question");
        newFlashcard = new flashCard();
        newFlashcard.retrieveFlashcardsFromDatabase();

        // Lấy và hiển thị câu hỏi ban đầu trong flashcard

        WebEngine webEngine = flashcard.getEngine();
        flashcard.setOnMouseClicked(event -> handleFlashcardClicked());
        String initialQuestion = newFlashcard.getQuestion();
        webEngine.loadContent(initialQuestion);

        NextButton.setOnAction(event -> handleNextButtonClicked());

        // Đặt hành động cho nút "Back"
        BackButton.setOnAction(event -> handleBackButtonClicked());
        TRATUBUTTON.setOnAction(event -> openFXMLWindow("/com/example/dictionary/TraTu.fxml"));
        CHINHSUABUTTON.setOnAction(event -> openFXMLWindow("/com/example/dictionary/ChinhSua.fxml"));
        HUONGDANBUTTON.setOnAction(event -> openFXMLWindow("/com/example/dictionary/HuongDan.fxml"));
        THONGKEBUTTON.setOnAction(event -> openFXMLWindow("/com/example/dictionary/flashCard.fxml"));
        GAMEBUTTON.setOnAction(event -> openFXMLWindow("/com/example/dictionary/PopUp.fxml"));
        BACKTOMENU.setOnAction(event -> openFXMLWindow("/com/example/dictionary/Menu.fxml"));
        DICHVANBANBUTTON.setOnAction(event -> openFXMLWindow("/com/example/dictionary/DichVanBan.fxml"));
        LICHSUBUTTON.setOnAction(event -> openFXMLWindow("/com/example/dictionary/LichSu.fxml"));

    }



    private void handleFlashcardClicked() {
        WebEngine webEngine = flashcard.getEngine();
        String currentContent = (String) webEngine.executeScript("document.documentElement.innerText");

        if (isQuestionDisplayed) {
            // Nếu đang hiển thị câu hỏi, thay đổi sang đáp án
            String answer = newFlashcard.getAnswer(currentContent);
            webEngine.loadContent(answer);
            flashcard.getStyleClass().remove("flashcard-text-area-question");
            flashcard.getStyleClass().add("flashcard-text-area-answer");
        } else {
            // Nếu đang hiển thị đáp án, thay đổi sang câu hỏi mới
            String newQuestion = newFlashcard.getQuestion();
            webEngine.loadContent(newQuestion);
            flashcard.getStyleClass().remove("flashcard-text-area-answer");
            flashcard.getStyleClass().add("flashcard-text-area-question");
        }

        isQuestionDisplayed = !isQuestionDisplayed;
    }

    @FXML
    private void handleNextButtonClicked() {
        WebEngine webEngine = flashcard.getEngine();
        String currentContent = (String) webEngine.executeScript("document.documentElement.innerText");
        String nextQuestion = getNextQuestion(currentContent);
        webEngine.loadContent(nextQuestion);
        isQuestionDisplayed = true;
    }

    @FXML
    private void handleBackButtonClicked() {
        WebEngine webEngine = flashcard.getEngine();
        String currentContent = (String) webEngine.executeScript("document.documentElement.innerText");
        String previousQuestion = getPreviousQuestion(currentContent);
        webEngine.loadContent(previousQuestion);
        isQuestionDisplayed = true;
    }

    private String getNextQuestion(String currentQuestion) {
        boolean foundCurrentQuestion = false;
        String nextQuestion = null;

        for (String question : newFlashcard.getFlashcards().keySet()) {
            if (foundCurrentQuestion) {
                nextQuestion = question;
                break;
            }

            if (question.equals(currentQuestion)) {
                foundCurrentQuestion = true;
            }
        }

        if (nextQuestion == null && !newFlashcard.getFlashcards().isEmpty()) {
            // Nếu không tìm thấy câu hỏi tiếp theo, quay lại câu hỏi đầu tiên
            nextQuestion = newFlashcard.getFlashcards().keySet().iterator().next();
        }

        return nextQuestion;
    }

    private String getPreviousQuestion(String currentQuestion) {
        boolean foundCurrentQuestion = false;
        String previousQuestion = null;

        for (String question : newFlashcard.getFlashcards().keySet()) {
            if (question.equals(currentQuestion)) {
                break;
            }

            previousQuestion = question;
        }

        if (previousQuestion == null && !newFlashcard.getFlashcards().isEmpty()) {
            // Nếu không tìm thấy câu hỏi trước đó, quay lại câu hỏi cuối cùng
            previousQuestion = newFlashcard.getFlashcards().keySet().iterator().next();
        }

        return previousQuestion;
    }

    public static String removeTextBetweenTags(String text, String tag) {
        // Tạo biểu thức chính quy để tìm và loại bỏ văn bản nằm trong các thẻ
        String regex = Pattern.quote("@" + tag + "<-br>");
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        // Thay thế văn bản nằm trong các thẻ bằng chuỗi rỗng
        String result = matcher.replaceAll("");

        return result;
    }
}