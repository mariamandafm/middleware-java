package main.java.ufrn.br.core;

import main.java.ufrn.br.annotations.RemoteComponent;
import main.java.ufrn.br.core.exception.RemoteObjectNotIdentified;
import main.java.ufrn.br.core.server.HttpMessageHandler;
import main.java.ufrn.br.core.server.HttpServer;

import java.io.IOException;

// TODO: Implementar Remoting Error
// TODO: Unmarshall
public class Broker {
    private Lookup lookup = Lookup.getInstance();

    public void registerObject(Object instance) {
        try{
            String objectId = getInstanceName(instance);
            lookup.registerObject(objectId, instance);
        } catch (RemoteObjectNotIdentified e){
            System.err.println(e);
            System.exit(1);
        }
    }

    private String getInstanceName(Object instance) throws RemoteObjectNotIdentified{
        String objectId = instance.getClass().getAnnotation(RemoteComponent.class).name();
        if (objectId.isEmpty()){
            throw new RemoteObjectNotIdentified("Remote object does not have a valid name");
        } else {
            return objectId;
        }
    }

    public void startServer(int port) throws IOException {
        HttpServer server = new HttpServer(port, new HttpMessageHandler());

        System.out.println("Server in up in " + port);
        server.start();
    };
}
