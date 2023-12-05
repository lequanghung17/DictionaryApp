package com.example.dictionary;

import com.example.dictionary.Controller.MenuControl;
import com.example.dictionary.Controller.DichVanBanControl;
import com.example.dictionary.Controller.ChinhSuaControl;
import com.example.dictionary.Controller.LichSuControl;
import com.example.dictionary.Controller.TraTuControl;


import com.example.dictionary.base.History;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;


import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class Aplication extends Application {
    public static Stage AppStage;


    public void Application() throws SQLException {

    }

    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        try {
            AnchorPane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Menu.fxml")));
            Scene scene = new Scene(root, 1000, 668);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
            AppStage = primaryStage;
            primaryStage.setOnCloseRequest(e -> {

                primaryStage.close();
                System.exit(0);
            });

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {

        launch();
    }

}
