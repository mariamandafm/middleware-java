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

public class Main {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException, IOException {

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/", new ServerRequestHandler("/"));

        System.out.println("Sprong in up in 8080");

//        if (bookLog.getClass().isAnnotationPresent(RemoteComponent.class)){
//            System.out.println("É uma classe remota");
//        } else {
//            System.out.println("Não é classe remota");
//        }
//
//        // Registrar os metodos com as devidas notações
//        List<Class> allowMethods = List.of(Get.class, Post.class, Put.class, Delete.class);
//
//        for (Method method : bookLog.getClass().getDeclaredMethods()){
//
//            if (method.isAnnotationPresent(Get.class)){
//                Get get = method.getAnnotation(Get.class);
//                server.createContext("/" + get.route(), new ServerRequestHandler("/"+get.route()));
//                System.out.println(get.route());
//                method.invoke(bookLog);
//            }
//        }
        server.setExecutor(null);
        server.start();
    }
}