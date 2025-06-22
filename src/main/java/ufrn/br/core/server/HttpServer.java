package main.java.ufrn.br.core.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
    private int port;
    private ServerSocket socket;
    private boolean running = true;
    private HttpMessageHandler handler;

    public HttpServer(int port, HttpMessageHandler handler) {
        this.port = port;
        this.handler = handler;
        try {
            this.socket = new ServerSocket(port, 600); // Servidor espera conexões na porta indicada
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public void start() {
        new Thread(() -> {
            while(running) {
                try {
                    Socket connection = socket.accept();

                    PrintWriter output = new PrintWriter(connection.getOutputStream(), true);
                    String response = handler.handle(connection.getInputStream());

                    if (response != null && !response.trim().isEmpty()) {
                        output.println(response);
                        connection.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
