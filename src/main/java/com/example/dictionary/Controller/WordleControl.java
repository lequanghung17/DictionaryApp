package com.example.dictionary.Controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;


    public class WordleControl {
        @FXML
        private Label stats;

        @FXML
        private TextField userText1;

        @FXML
        private TextField userText2;

        @FXML
        private TextField userText3;

        @FXML
        private TextField userText4;

        @FXML
        private TextField userText5;

        @FXML
        private Button enterButton;

        @FXML
        private Label label1;

        @FXML
        private Label label2;

        @FXML
        private Label label3;

        @FXML
        private Label label4;

        @FXML
        private Label label5;

        @FXML
        private ImageView background;

        @FXML
        private Button returnHome;



        private String[] possibleWords;
        private int tries;
        private char[] input;
        private long startTime;
        private char[] answer;
        private boolean done;
        private String answerChosen;

        private TextFlow textFlow;

        private Text text;

        public static final String ANSI_RESET = "\u001B[0m";
        public static final String ANSI_YELLOW = "\u001B[33m";
        public static final String ANSI_GREEN = "\u001B[32m";




        public void open1() {
            userText1.setVisible(true);
            userText1.requestLayout();
        }

        public void open2() {
            userText2.setVisible(true);
            userText2.requestLayout();
        }

        public void open3() {
            userText3.setVisible(true);
            userText3.requestLayout();
        }

        public void open4() {
            userText4.setVisible(true);
            userText4.requestLayout();
        }

        public void open5() {
            userText5.setVisible(true);
            userText5.requestLayout();
        }

        public void close1() {
            userText1.setVisible(false);
            userText1.requestLayout();
        }

        public void close2() {
            userText2.setVisible(false);
            userText2.requestLayout();
        }

        public void close3() {
            userText3.setVisible(false);
            userText3.requestLayout();
        }

        public void close4() {
            userText4.setVisible(false);
            userText4.requestLayout();
        }

        public void close5() {
            userText5.setVisible(false);
            userText5.requestLayout();
        }


        @FXML
        public void initialize() {



            startWordle();
            userText1.setVisible(true);
            userText2.setVisible(false);
            userText3.setVisible(false);
            userText4.setVisible(false);
            userText5.setVisible(false);
            //enterButton.setOnAction(event -> handleEnterButtonClick(userText1));

            userText1.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    handleEnterKeyPress(userText1, userText2);
                }
            });
            //enterButton.setOnAction(event -> handleEnterButtonClick(userText2));
            userText2.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    handleEnterKeyPress(userText2, userText3);
                }
            });
            //enterButton.setOnAction(event -> handleEnterButtonClick(userText3));
            userText3.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    handleEnterKeyPress(userText3, userText4);
                }
            });
            //enterButton.setOnAction(event -> handleEnterButtonClick(userText4));
            userText4.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    handleEnterKeyPress(userText4, userText5);
                }
            });
            //enterButton.setOnAction(event -> handleEnterButtonClick(userText5));
            userText5.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    handleEnterKeyPress(userText5, null);
                }
            });



            enterButton.setOnAction(event -> openFXMLWindow("/com/example/dictionary/Wordle.fxml"));
            returnHome.setOnAction(event -> openFXMLWindow("/com/example/dictionary/Menu.fxml"));



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
                Stage currentStage = (Stage) background.getScene().getWindow(); // lấy cửa sổ hiện tại từ background
                currentStage.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }



        public void startWordle() {
            possibleWords = new String[12947];
            try {
                File myObj = new File("user/wordleWords.txt");
                Scanner myReader = new Scanner(myObj);
                int indexCounter = 0;
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    possibleWords[indexCounter] = data;
                    indexCounter++;
                }
                myReader.close();
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

            startTime = System.currentTimeMillis();
            tries = 0;
            System.out.println("Wordle: Type A Five Letter Word");
            answerChosen = returnRandomWord();
            answer = new char[5];
            for (int i = 0; i < 5; i++)
                answer[i] = answerChosen.charAt(i);

            input = new char[5];

        }

        public void endWordle() {
            // Your Wordle ending code here
            System.out.println("Wordle: The Answer Was: " + new String(answerChosen));
            System.out.println("Wordle: You Found The Answer in "
                    + ((System.currentTimeMillis() - startTime) / 1000) + " seconds and " + tries
                    + " tries.");




            if (!done) {
                stats.setText("The Answer Was: " + new String(answerChosen) + ". You wasted "
                        + ((System.currentTimeMillis() - startTime) / 1000) + " seconds ");
                stats.setFont(Font.font("Arial", 20));
                stats.setTextFill(Color.RED);
                //stats.setLayoutX(260);
                //stats.setLayoutY(128);
                //stats.setPrefWidth(1000);
                //stats.setPrefHeight(70);



            } else {
                stats.setText(
                        "You Found The Answer in " + ((System.currentTimeMillis() - startTime) / 1000)
                                + " seconds and " + tries + " tries.");
                stats.setFont(Font.font("Arial", 20));
                stats.setTextFill(Color.DARKGREEN);
                //stats.setLayoutX(260);
               // stats.setLayoutY(128);
                //stats.setPrefWidth(1000);
                //stats.setPrefHeight(70);
            }
        }

        private void handleEnterButtonClick(TextField userText) {
            if (userText.isVisible()) {
                handleEnterButtonClickForTextField(userText);
                closeCurrentAndOpenNext(userText);
            }
        }

        private void handleEnterKeyPress(TextField currentUserText, TextField nextUserText) {
            if (currentUserText.getText().length() == 5 && isValidWord(currentUserText.getText(), possibleWords)) {
                closeCurrentAndOpenNext(currentUserText);
            }
        }

        private void closeCurrentAndOpenNext(TextField currentUserText) {
            currentUserText.setVisible(false);
            currentUserText.requestLayout();
            buttonPressed(currentUserText);
            openNextTextField(currentUserText);
        }

        private void openNextTextField(TextField currentUserText) {
            if (currentUserText == userText1) {
                open2();
            } else if (currentUserText == userText2) {
                open3();
            } else if (currentUserText == userText3) {
                open4();
            } else if (currentUserText == userText4) {
                open5();
            }
        }




        private void handleEnterButtonClickForTextField(TextField textField) {
            String input = textField.getText();
            if (isValidWord(input, possibleWords)) {
                buttonPressed(textField);
            } else {
                System.out.println("Wordle: That is not a valid word");
            }
        }





        public boolean isValidWord(String input, String[] possibleWords) {
            if (input.length() < 5) {
                System.out.println("Wordle: The Word You Entered Was Not Long Enough");
                return false;
            }
            for (String string : possibleWords) {
                if (string.equals(input)) {
                    return true;
                }
            }

            return false;
        }

        public void buttonPressed(TextField userText) {
            String userInput = userText.getText();
            int[] colorOfLetters = playWordle(userInput);

            done = true;
            for (int i : colorOfLetters) {
                if (i != 2)
                    done = false;
            }

            if (done || tries > 4)
                endWordle();

            textFlow = new TextFlow();
            for (int i = 0; i < 5; i++) {
                text = new Text(String.valueOf(userInput.charAt(i)) + " ");
                text.setFont(Font.font("Arial", 35));

                // Set màu sắc dựa trên colorOfLetters
                if (colorOfLetters[i] == 0) {
                    text.setFill(Color.BLACK);
                } else if (colorOfLetters[i] == 1) {
                    text.setFill(Color.YELLOW);
                } else if (colorOfLetters[i] == 2) {
                    text.setFill(Color.GREEN);
                }

                textFlow.getChildren().add(text);
            }

            setNextLabel(textFlow);

            userText.setText("");
        }

        public int[] playWordle(String inputWordleWord) {
            done = false;
            tries++;
            String R1 = inputWordleWord.toLowerCase();
            if (!isValidWord(R1, possibleWords)) {
                System.out.println("wasn't a good word");
            } else {
                for (int i = 0; i < 5; i++) {
                    input[i] = R1.charAt(i);
                }
            }

            for (int i = 0; i < 5; i++)
                answer[i] = answerChosen.charAt(i);
            return returnColorOfLetters(input, answer);
        }

        public void setNextLabel(TextFlow textFlow) {
            if (label1.getGraphic() == null) {
                label1.setText(" ");
                label1.setGraphic(textFlow);
            } else if (label2.getGraphic() == null) {
                label2.setText(" ");
                label2.setGraphic(textFlow);
            } else if (label3.getGraphic() == null) {
                label3.setText(" ");
                label3.setGraphic(textFlow);
            } else if (label4.getGraphic() == null) {
                label4.setText(" ");
                label4.setGraphic(textFlow);
            } else if (label5.getGraphic() == null) {
                label5.setText(" ");
                label5.setGraphic(textFlow);
            }
        }


        public int[] returnColorOfLetters(char[] inputWord, char[] correctWord) {
            char[] answerTemp = correctWord;
            int[] colorForLetter = new int[5];

            for (int i = 0; i < 5; i++) {
                if (inputWord[i] == answerTemp[i]) {
                    answerTemp[i] = '-';
                    colorForLetter[i] = 2;
                }
            }

            for (int j = 0; j < 5; j++) {
                for (int k = 0; k < 5; k++) {
                    if (inputWord[j] == answerTemp[k] && colorForLetter[j] != 2) {
                        colorForLetter[j] = 1;
                        answerTemp[k] = '-';
                    }
                }
            }

            for (int m = 0; m < 5; m++) {
                if (colorForLetter[m] == 0)
                    System.out.print(inputWord[m]);
                if (colorForLetter[m] == 1)
                    System.out.print(ANSI_YELLOW + inputWord[m]);
                if (colorForLetter[m] == 2)
                    System.out.print(ANSI_GREEN + inputWord[m]);
            }

            System.out.println("");
            return colorForLetter;
        }

        private String returnRandomWord() {
            String[] answerList = new String[2315];
            try {
                File myObj = new File(
                        "user/wordleAnswers.txt");
                Scanner myReader = new Scanner(myObj);
                int indexCounter = 0;
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    answerList[indexCounter] = data;
                    indexCounter++;
                }
                myReader.close();
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

            return answerList[new Random().nextInt(answerList.length)];
        }
    }

       








