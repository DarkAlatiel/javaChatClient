package model;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.MainFormController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;

/**
 * Обеспечивает работу программы в режиме клиента
 */
public class Client implements ConnectionListener {

	public static final int PORT = 8189;
	public static final String EXIT_CODE = "/exit";
/*
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_RESET = "\u001B[0m";
*/
	private Connection connection;

	/**
	 * Запрашивает у пользователя ник и организовывает обмен сообщениями с
	 * сервером
	 */
	public Client(Stage stage, String ip, String login, String password) throws IOException {
		try {
			connection = new Connection(this, ip, PORT);
		} catch (IOException e) {
			printMsg("Connection exception: " + e);
		}
		FXMLLoader loader = new FXMLLoader(getClass().getResource(
                ".." + File.separator + "view" + File.separator + "mainForm.fxml"));
		Parent root = loader.load();
		MainFormController controller = loader.getController();
		stage.setTitle("Лучший в мире чат");
		stage.setScene(new Scene(root));
		stage.show();
	}

	private synchronized void printMsg(String msg) {
		System.out.println(msg);
	}

	@Override
	public void onConnectionReady(Connection connection) {

	}

	@Override
	public void onReceiveString(Connection connection, String value) {

	}

	@Override
	public void onDisconnect(Connection connection) {

	}

	@Override
	public void onException(Connection connection, Exception e) {

	}
}