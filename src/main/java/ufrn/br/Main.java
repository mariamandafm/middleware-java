package ufrn.br;

import com.sun.net.httpserver.HttpServer;
import main.java.ufrn.br.BookLog;
import main.java.ufrn.br.core.ServerRequestHandler;
import main.java.ufrn.br.core.Sprong;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException, IOException {

        BookLog bookLog = new BookLog();
        Sprong server = new Sprong();
        server.registerObject(bookLog);
        server.startServer(8080);
    }
}