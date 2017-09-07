package model;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.MainFormController;
import view.MainFormControllerListener;

import java.io.*;

/**
 * Обеспечивает работу программы в режиме клиента
 */
public class Client implements ConnectionListener, MainFormControllerListener {

	public static final int PORT = 8283;
	public static final String EXIT_CODE = "/exit";
/*
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_RESET = "\u001B[0m";
*/
	private Connection connection;
	private MainFormController controller;

	/**
	 * Запрашивает у пользователя ник и организовывает обмен сообщениями с
	 * сервером
	 */
	public Client(Stage stage, String ip, String login, String password) throws IOException {
		try {
			connection = new Connection(this, ip, PORT, login);
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    ".." + File.separator + "view" + File.separator + "mainForm.fxml"));
            Parent root = loader.load();
            controller = loader.getController();
            controller.addListener(this);
            stage.setTitle("Лучший в мире чат");
            stage.setScene(new Scene(root));
            stage.show();
		} catch (IOException e) {
			printMsg("Connection exception: " + e);
		}
	}

	private synchronized void printMsg(String msg) {
		System.out.println(msg);
	}

	@Override
	public void onConnectionReady(Connection connection) {
        connection.sendString(connection.getLogin());
	}

	@Override
	public void onReceiveString(Connection connection, String message) {
        controller.showMessage(message);
    }

	@Override
	public void onDisconnect(Connection connection) {
        connection.sendString(connection.getLogin());
	}

	@Override
	public void onException(Connection connection, Exception e) {

	}

    @Override
    public void sendMessage(String message) {
        connection.sendString(message);
    }
}