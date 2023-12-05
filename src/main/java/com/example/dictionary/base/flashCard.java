package com.example.dictionary.base;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class flashCard {
    private static Map<String, String> flashcards;

    public flashCard() {
        this.flashcards = new HashMap<>();
    }

    public static void addFlashcard(String question, String answer) {
        flashcards.put(question, answer);
    }

    public static void retrieveFlashcardsFromDatabase() throws SQLException {
        DatabaseConnect.loadDatabase();
        try {
            String sql = "SELECT target, definition FROM dictionary WHERE usage_time > 0";
            PreparedStatement statement = DatabaseConnect.getConn().prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String question = resultSet.getString("target");
                String answer = resultSet.getString("definition");
                flashcards.put(question, answer);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static String getQuestion() {
        if (flashcards.isEmpty()) {
            return "No flashcards available.";
        }

        // Lấy một câu hỏi ngẫu nhiên từ danh sách flashcards
        int randomIndex = (int) (Math.random() * flashcards.size());
        String question = (String) flashcards.keySet().toArray()[randomIndex];

        return question;
    }

    public static String getAnswer(String question) {
        if (flashcards.isEmpty()) {
            return "No flashcards available.";
        }

        if (flashcards.containsKey(question)) {
            return flashcards.get(question);
        } else {
            return "Answer not found.";
        }
    }
    public static Map<String, String> getFlashcards() {
        return flashcards;
    }

//    public static void main(String[] args) {
//        flashCard flashcards = new flashCard();
//
//        // Thêm các flashcard vào đối tượng flashcards
//        flashcards.addFlashcard("Question 1", "Answer 1");
//        flashcards.addFlashcard("Question 2", "Answer 2");
//        flashcards.addFlashcard("Question 3", "Answer 3");
//
//        try {
//            // Lấy danh sách flashcard từ cơ sở dữ liệu
//            flashcards.retrieveFlashcardsFromDatabase();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        // Lấy và in ra một câu hỏi ngẫu nhiên
//        String randomQuestion = flashcards.getQuestion();
//        System.out.println("Random Question: " + randomQuestion);
//
//        // Lấy và in ra câu trả lời cho câu hỏi đó
//        String answer = flashcards.getAnswer(randomQuestion);
//        System.out.println("Answer: " + answer);
//    }
}