package model;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;

public class Connection {

    private String login;
    private final Socket socket;
    private final Thread thread;
    private final ConnectionListener eventListener;
    private final BufferedReader in;
    private final BufferedWriter out;

    public Connection(ConnectionListener eventListener, String ipAddr, int port, String login) throws IOException {
        this.eventListener = eventListener;
        this.socket = new Socket(ipAddr, port);
        this.login = login;

        in = new BufferedReader(new InputStreamReader(socket.getInputStream(), Charset.forName("UTF-8")));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), Charset.forName("UTF-8")));
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    eventListener.onConnectionReady(Connection.this);
                    while (!thread.isInterrupted()) {
                        String message = in.readLine();
                        eventListener.onReceiveString(Connection.this, message);
                    }
                } catch (IOException e) {
                    eventListener.onException(Connection.this, e);
                } finally {
                    eventListener.onDisconnect(Connection.this);
                }
            }
        });
        thread.start();
    }

    public synchronized void sendString(String value) {
        try {
            out.write(value + "\r\n");
            out.flush();
        } catch (IOException e) {
            eventListener.onException(this, e);
            disconnect();
        }
    }

    public synchronized void disconnect() {
        thread.interrupt();
        try {
            socket.close();
        } catch (IOException e) {
            eventListener.onException(this, e);
        }
    }

    @Override
    public String toString() {
        return "Соединение: " + socket.getInetAddress() + ": " + socket.getPort();
    }

    public String getLogin() {
        return login;
    }
}
