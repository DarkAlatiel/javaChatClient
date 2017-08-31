package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

/**
 * Обеспечивает работу программы в режиме клиента
 */
public class Client {

	public static final int PORT = 8283;
	public static final String EXIT_CODE = "/exit";

/*
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_RESET = "\u001B[0m";
*/

	private BufferedReader in;
	private PrintWriter out;
	private Socket socket;

	/**
	 * Запрашивает у пользователя ник и организовывает обмен сообщениями с
	 * сервером
	 */
	public Client(String ip, String login) {
        try {
			// Подключаемся в серверу и получаем потоки(in и out) для передачи сообщений
			socket = new Socket(ip, PORT);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);

			out.println(login);

			// Запускаем вывод всех входящих сообщений в консоль
			Resender resend = new Resender();
			resend.start();

			// Пока пользователь не введёт "exit" отправляем на сервер всё, что
			// введено из консоли
			String str = "";
/*			while (!str.equals(EXIT_CODE)) {
                str = scan.nextLine();
				out.println(str);
			}*/
			resend.setStop();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	/**
	 * Закрывает входной и выходной потоки и сокет
	 */
	private void close() {
		try {
			in.close();
			out.close();
			socket.close();
		} catch (Exception e) {
			System.err.println("Потоки не были закрыты!");
		}
	}

	/**
	 * Класс в отдельной нити пересылает все сообщения от сервера в консоль.
	 * Работает пока не будет вызван метод setStop().
	 *
	 * @author Влад
	 */
	private class Resender extends Thread {

		private boolean stoped;

		/**
		 * Прекращает пересылку сообщений
		 */
		public void setStop() {
			stoped = true;
		}

		/**
		 * Считывает все сообщения от сервера и печатает их в консоль.
		 * Останавливается вызовом метода setStop()
		 *
		 * @see Thread#run()
		 */
		@Override
		public void run() {
            try {
                String str = "";
                while (!stoped) {
				    try {
                        str = in.readLine();
                    } catch (SocketException e){
                        System.out.println(/*ANSI_RED + */
								"Произошло отключение от сервера!"
								/* + ANSI_RESET*/);
                    }
					System.out.println(str);
				}
			} catch (IOException e) {
				System.err.println("Ошибка при получении сообщения.");
				e.printStackTrace();
			}
		}
	}

}