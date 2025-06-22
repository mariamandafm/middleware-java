package main.java.ufrn.br.core;

import main.java.ufrn.br.annotations.RemoteComponent;
import main.java.ufrn.br.core.server.HttpMessageHandler;
import main.java.ufrn.br.core.server.HttpServer;

import java.io.IOException;

// TODO: Implementar Remoting Error
// TODO: Unmarshall
public class Broker {
    private Lookup lookup = Lookup.getInstance();

    public void registerObject(Object instance){
        String objectId = instance.getClass().getAnnotation(RemoteComponent.class).name();
        lookup.registerObject(objectId, instance);
    }

    public void startServer(int port) throws IOException {
        HttpServer server = new HttpServer(port, new HttpMessageHandler());

        System.out.println("Server in up in " + port);
        server.start();
    };
}
