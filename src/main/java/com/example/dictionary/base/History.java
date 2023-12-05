package com.example.dictionary.base;
/**
 * Goi load -> add -> export
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class History {
    private static final ArrayList<String> history = new ArrayList<>();

    public static ArrayList<String> getAllHis() throws SQLException {
        final String QUERY = "SELECT target FROM dictionary WHERE usage_time > 0 ORDER BY usage_time DESC";

        ArrayList<String> history = new ArrayList<>();

        try (PreparedStatement statement = DatabaseConnect.getConn().prepareStatement(QUERY);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String word = resultSet.getString("target");
                history.add(word);
            }
        }

        HashSet<String> uniqueSet = new HashSet<>(history);  // Tạo một Set để lưu trữ các phần tử duy nhất
        history.clear();  // Xóa tất cả các phần tử trong danh sách history
        history.addAll(uniqueSet);  // Thêm lại các phần tử duy nhất vào danh sách history

        return history;
    }


    public static void main(String[] args) throws SQLException {
        System.out.println(History.getAllHis());
    }

}