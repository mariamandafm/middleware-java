package main.java.ufrn.br.core;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.java.ufrn.br.BookLog;
import main.java.ufrn.br.annotations.Get;
import main.java.ufrn.br.annotations.Param;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class ServerRequestHandler implements HttpHandler {
    final String route;

    private BookLog bookLog = new BookLog();

    public ServerRequestHandler(String route) {
        this.route = route;
    }

    private Object invoke(Object instance, String methodName) throws InvocationTargetException, IllegalAccessException {
        for (Method method : instance.getClass().getDeclaredMethods()){
            Get get = method.getAnnotation(Get.class);
            if (get != null && get.route().equals(methodName)){
                Parameter[] params = method.getParameters();
                if (params.length > 0){
                    for (Parameter param : params){
                        if (param.isAnnotationPresent(Param.class)){
                            Param annotation = param.getAnnotation(Param.class);
                            System.out.println("Notação: " + annotation.name());
                        }
                    }
                }
                return method.invoke(bookLog);
            }
        }
        return "Method not found";
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println(exchange.getRequestURI());
        try{
            if ("POST".equals(exchange.getRequestMethod())){
                System.out.println("POST Request");
            } else if ("GET".equals(exchange.getRequestMethod())) {
                System.out.println("GET Request");
                Object result = invoke(bookLog, exchange.getRequestURI().toString());
                String response = "GET methos called";
                exchange.sendResponseHeaders(200, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else {
                exchange.sendResponseHeaders(405, -1); return;
            }
        } catch (Exception e){
            e.printStackTrace();
        }



    }
}
