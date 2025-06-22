package main.java.ufrn.br;
import main.java.ufrn.br.core.Broker;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException, IOException {

        BookLog bookLog = new BookLog();
        Broker server = new Broker();
        server.registerObject(bookLog);
        server.startServer(8080);
    }
}