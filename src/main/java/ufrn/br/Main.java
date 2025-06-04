package ufrn.br;

import com.sun.net.httpserver.HttpServer;
import main.java.ufrn.br.BookLog;
import main.java.ufrn.br.annotations.*;
import main.java.ufrn.br.core.ServerRequestHandler;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

// TODO: Serializar e deserializar as requisições com o Marshaller
// TODO: Padronizar as rotas para encontrar objeto e metodos.
// TODO: 

public class Main {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException, IOException {

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/", new ServerRequestHandler("/"));

        System.out.println("Sprong in up in 8080");

        server.setExecutor(null);
        server.start();
    }
}