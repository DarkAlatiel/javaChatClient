package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Client;

import java.io.File;
import java.io.IOException;

public class Main extends Application {

    public static Main main;

    Client client;
    Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        main = this;
        this.stage = primaryStage;
        Parent root = new FXMLLoader().load(getClass().getResource(
                ".." + File.separator + "view" + File.separator + "loginForm.fxml"));
        primaryStage.setTitle("Подключение");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public void createClient(String ip, String login, String password) throws IOException {
        client = new Client(stage, ip, login, password);
    }

    public static Main getInstance(){
        return main;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public Stage getStage() {
        return stage;
    }

}
