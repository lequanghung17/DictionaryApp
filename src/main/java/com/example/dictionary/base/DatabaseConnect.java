package com.example.dictionary.base;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.sql.*;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;


public class DatabaseConnect {

    private static final String USER_NAME = "root";

    private static final String PASSWORD = "Chichich1!";
    private static final String DatabaseURL = "jdbc:mysql://localhost:3307/classicmodels";
//    private static final String PASSWORD = "12345678";
//    private static final String DatabaseURL = "jdbc:mysql://localhost:3306/en-vi-dictionary";


    private static Connection conn = null;


    public static void loadDatabase() throws SQLException {
        conn = DriverManager.getConnection(DatabaseURL, USER_NAME, PASSWORD);
        ArrayList<String> allTargets = loadAllTargets();
        for (String c : allTargets) {
            Trie.addNode(c);
        }
    }

    public static Connection getConn() throws SQLException {
        return conn = DriverManager.getConnection(DatabaseURL, USER_NAME, PASSWORD);
    }

    public static String lookUpWord(String wordTarget) {
        final String QUERY = "SELECT definition FROM dictionary WHERE target = ?";
        try (PreparedStatement prst = conn.prepareStatement(QUERY)) {
            prst.setString(1, wordTarget);
            try (ResultSet rs = prst.executeQuery()) {
                if (rs.next()) {
                    updateTime(wordTarget);
                    return rs.getString("definition");
                } else {
                    return "not found any definition";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "print";
    }

    public static boolean isempty(String target)  { //kiểm tra xem có tỏng database ko
        final String QUERY3 = "SELECT definition FROM dictionary WHERE target = ?";
        try (PreparedStatement prst = conn.prepareStatement(QUERY3)) {
            prst.setString(1, target);
            try (ResultSet rs = prst.executeQuery()) {
                if (rs.next()) {
                    return false;
                } else {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
    public static void updateTime(String target) throws SQLException {
        final String QUERY2 = "UPDATE dictionary SET usage_time = ? WHERE target = ? LIMIT 1";
        try (PreparedStatement pr = conn.prepareStatement(QUERY2)) {
            java.util.Date time = new Date(System.currentTimeMillis());
            long seconds = time.getTime() / 1000;
            int unsignedSeconds = (int) (seconds & 0xFFFFFFFFL);
            pr.setInt(1, unsignedSeconds);
            pr.setString(2, target);
            pr.executeUpdate();
            System.out.println("\naaa");
        }
    }


    public static String insertWord(String wordTarget, String wordExplain) {
        wordTarget = wordTarget.toLowerCase();
        wordTarget = wordTarget.trim();
        String In_QUERY = "INSERT INTO dictionary (target, definition) VALUES (?, ?)";
        try {
            PreparedStatement statement = conn.prepareStatement(In_QUERY);
            statement.setString(1, wordTarget);
            statement.setString(2, wordExplain);
            statement.executeUpdate();
            try {
                statement.executeUpdate();
            } catch (SQLIntegrityConstraintViolationException e) {
                return "your word is already inserted";
            } finally {
                {
                    try {
                        if (statement != null) {
                            statement.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            Trie.addNode(wordExplain);
            return "Your word is inserted";
        } catch (SQLException e) {
            e.printStackTrace();
            return "error in insertWord";
        }
    }

    public static String deleteWord(String wordTarget) {
        wordTarget = wordTarget.toLowerCase();
        wordTarget = wordTarget.trim();
        String Dele_QUERY = "DELETE FROM dictionary WHERE target = ?";
        try {
            PreparedStatement prst = conn.prepareStatement(Dele_QUERY);
            prst.setString(1, wordTarget);
            try {
                int deleRows = prst.executeUpdate();
                if (deleRows == 0) {
                    return "Your word isn't in our database";
                }
            } finally {
                try {
                    if (prst != null) {
                        prst.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            Trie.delete(wordTarget);
            return "Your word is deleted";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error";
        }
    }

    public static String updateWord(String wordTarget, String wordDefinition) {
        wordTarget = wordTarget.toLowerCase();
        wordTarget = wordTarget.trim();
        String Up_QUERY = "UPDATE dictionary SET definition = ? WHERE target = ?";
        try {
            PreparedStatement prst = conn.prepareStatement(Up_QUERY);
            prst.setString(1, wordDefinition);
            prst.setString(2, wordTarget);
            try {
                int updatedRows = prst.executeUpdate();
                if (updatedRows == 0) {
                    return "This word is not exist";
                }
            } finally {
                try {
                    if (prst != null) {
                        prst.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Change success";
    }


    public static ArrayList<String> loadAllTargets() {
        final String SQL_QUERY = "SELECT * FROM dictionary";
        try {
            PreparedStatement prst = conn.prepareStatement(SQL_QUERY);
            try {
                ResultSet rs = prst.executeQuery();
                try {
                    ArrayList<String> targets = new ArrayList<>();
                    while (rs.next()) {
                        targets.add(rs.getString(2));
                    }
                    return targets;

                } finally {
                    try {
                        if (rs != null) {
                            rs.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } finally {
                try {
                    if (prst != null) {
                        prst.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}


