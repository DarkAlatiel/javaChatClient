package model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    public static Main main;

    Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        main = this;
        this.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource(
                ".." + File.separator + "view" + File.separator + "loginForm.fxml"));
        primaryStage.setTitle("Подключение");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public void createMainFormWindow() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource(
                ".." + File.separator + "view" + File.separator + "mainForm.fxml"));
        primaryStage.setTitle("Лучший в мире чат");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static Main getInstance(){
        return main;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
