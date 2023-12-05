package com.example.dictionary.Controller;
import com.example.dictionary.base.DatabaseConnect;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

 import java.io.IOException;
 import java.net.URL;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.sql.SQLException;

import static com.example.dictionary.Controller.ResultControl.addResultList;

public class QuizzControl {
    @FXML
    private ImageView background;
    @FXML
    private Label questionLabel;
    @FXML
    private Button optionA;
    @FXML
    private Button optionB;
    @FXML
    private Button optionC;
    @FXML
    private Button optionD;
    @FXML
    private Button Back;

    private static Connection connection;

    private String Correct_Ans;

    public static List<String> answerforallList = new ArrayList<>();

    static int counter = 0;
    static int correct = 0;
    static int wrong = 0;


    @FXML
    public void initialize() {
        counter = 0;
        correct = 0;
        wrong = 0;
        initializeDatabaseConnection();
        loadQuestions();

        Back.setOnAction(event -> {
            openFXMLWindow();
            QuizzControl.resetAnswersList();
        });
    }

    public void loadQuestions() {



        if (counter == 0) { //Question 1
            initquestion();
           /* String[] choice_ = getchoice();

            questionLabel.setText(quizz_.getquestion());
            optionA.setText(choice_[0]);
            optionB.setText(choice_[1]);
            optionC.setText(choice_[2]);
            optionD.setText(choice_[3]);*/
        }
        if (counter == 1) { //Question 2
            initquestion();

        }
        if (counter == 2) { //Question 3
            initquestion();

        }
        if (counter == 3) { //Question 4
            initquestion();

        }
        if (counter == 4) {//Question 5
            initquestion();

        }
        if (counter == 5) { //Question 6
            initquestion();

        }
        if (counter == 6) { //Question 7
            initquestion();

        }
        if (counter == 7) { //Question 8
            initquestion();

        }
        if (counter == 8) { //Question 9
            initquestion();

        }
        if (counter == 9) { //Question 10
            initquestion();

        }

    }


    @FXML
    public void optionAchosen(ActionEvent event) {
        checkAnswer(optionA.getText());
        if (checkAnswer(optionA.getText())) {
            correct = correct + 1;
            addResultList("Correct");

        } else {
            wrong = wrong + 1;
            addResultList("Wrong");
        }
        if (counter == 9) {
            openFXMLWindowResult();

        } else {
            counter++;
            loadQuestions();
        }

    }



    @FXML
    public void optionBchosen(ActionEvent event) {
        checkAnswer(optionB.getText());
        if (checkAnswer(optionB.getText())) {
            correct = correct + 1;
            addResultList("Correct");
        } else {
            wrong = wrong + 1;
            addResultList("Wrong");
        }
        if (counter == 9) {
            openFXMLWindowResult();
        } else {
            counter++;
            loadQuestions();
        }
    }

    @FXML
    public void optionCchosen(ActionEvent event) {
        checkAnswer(optionC.getText());
        if (checkAnswer(optionC.getText())) {
            correct = correct + 1;
            addResultList("Correct");
        } else {
            wrong = wrong + 1;
            addResultList("Wrong");
        }
        if (counter == 9) {
            openFXMLWindowResult();
        } else {
            counter++;
            loadQuestions();
        }
    }

    @FXML
    public void optionDchosen(ActionEvent event) {
        checkAnswer(optionD.getText());
        if (checkAnswer(optionD.getText())) {
            correct = correct + 1;
            addResultList("Correct");
        } else {
            wrong = wrong + 1;
            addResultList("Wrong");
        }
        if (counter == 9) {
            openFXMLWindowResult();
        } else {
            counter++;
            loadQuestions();
        }
    }

    public boolean checkAnswer(String answer) {

        if (counter == 0) {
            return answer.equals(Correct_Ans);
        }
        if (counter == 1) {
            return answer.equals(Correct_Ans);
        }
        if (counter == 2) {
            return answer.equals(Correct_Ans);
        }
        if (counter == 3) {
            return answer.equals(Correct_Ans);
        }
        if (counter == 4) {
            return answer.equals(Correct_Ans);
        }
        if (counter == 5) {
            return answer.equals(Correct_Ans);
        }
        if (counter == 6) {
            return answer.equals(Correct_Ans);
        }
        if (counter == 7) {
            return answer.equals(Correct_Ans);
        }
        if (counter == 8) {
            return answer.equals(Correct_Ans);
        }
        if (counter == 9) {
            return answer.equals(Correct_Ans);
        }
        return false;


    }


    public void initializeDatabaseConnection() {
        try {



            connection = DatabaseConnect.getConn();

            System.out.println("Connected to the database!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeDatabaseConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Disconnected from the database!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void initquestion() {
        try {
            // Tạo truy vấn SQL để lấy từ và nghĩa từ database
            String sql = "SELECT Vocabulary, Meaning FROM vocab ORDER BY RAND() LIMIT 1";

            // Thực hiện truy vấn
            try ( Connection connection = DatabaseConnect.getConn();
                 PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {

                // Kiểm tra xem có dữ liệu từ truy vấn hay không
                if (resultSet.next()) {
                    // Gán giá trị từ và nghĩa
                    String word = resultSet.getString("Vocabulary");
                    String correctMeaning = resultSet.getString("Meaning");

                    // Tạo danh sách nghĩa để làm lựa chọn
                    List<String> meanings = getOtherMeanings(word, correctMeaning);

                    // Trộn danh sách nghĩa
                    Collections.shuffle(meanings);

                    // Lấy 3 nghĩa ngẫu nhiên từ danh sách để làm lựa chọn
                    List<String> choices = new ArrayList<>(4);
                    choices.add(correctMeaning);
                    choices.addAll(meanings.subList(0, 3));

                    // Trộn lại để đảm bảo vị trí của nghĩa đúng
                    Collections.shuffle(choices);

                    // Gán giá trị câu hỏi và lựa chọn
                    questionLabel.setText("What is the meaning of the word '" + word + "'?");
                    questionLabel.setTextFill(Color.RED);
                    optionA.setText(choices.get(0));
                    optionB.setText(choices.get(1));
                    optionC.setText(choices.get(2));
                    optionD.setText(choices.get(3));
                    Correct_Ans = correctMeaning;
                    answerforallList.add(word + " : " + correctMeaning );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getOtherMeanings(String word, String correctMeaning) {
        List<String> allMeanings = getMeaningsFromWord(word);

        // Loại bỏ nghĩa đúng từ danh sách
        allMeanings.remove(correctMeaning);

        return allMeanings;
    }

    public static List<String> getMeaningsFromWord(String word) {
        List<String> meanings = new ArrayList<>();

        try {
            // Tạo truy vấn SQL để lấy nghĩa của từ từ cơ sở dữ liệu
            String sql = "SELECT Meaning FROM vocab";

            // Thực hiện truy vấn
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {

                // Lặp qua các dòng kết quả và thêm nghĩa vào danh sách
                while (resultSet.next()) {
                    meanings.add(resultSet.getString("Meaning"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return meanings;
    }

    public static void resetAnswersList() {
        answerforallList.clear();
    }

    @FXML
    private void openFXMLWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/dictionary/Menu.fxml"));
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

    private void openFXMLWindowResult() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/dictionary/Result.fxml"));
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

}

