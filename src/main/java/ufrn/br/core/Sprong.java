package main.java.ufrn.br.core;

import com.sun.net.httpserver.HttpServer;
import main.java.ufrn.br.annotations.RemoteComponent;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Sprong {
    private Lookup lookup = Lookup.getInstance();

    public void registerObject(Object instance){
        String objectId = instance.getClass().getAnnotation(RemoteComponent.class).name();
        lookup.registerObject(objectId, instance);
    }

    public void startServer(int port) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/", new ServerRequestHandler("/"));

        System.out.println("Sprong in up in 8080");

        server.setExecutor(null);
        server.start();
    };
}
