package main.java.ufrn.br.core;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.java.ufrn.br.BookLog;
import org.json.JSONObject;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ServerRequestHandler implements HttpHandler {
    final String route;

    private BookLog bookLog = new BookLog();

    private JsonMarshaller marshaller = new JsonMarshaller();

    private Invoker invoker = new Invoker();

    public ServerRequestHandler(String route) {
        this.route = route;
    }


    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println(exchange.getRequestURI());
        Set<String> allowedMethods = new HashSet<>(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        try{
            if (allowedMethods.contains(exchange.getRequestMethod())){
                JSONObject resourceRequest = marshaller.unmarshall(exchange.getRequestMethod(), exchange.getRequestURI(), exchange.getRequestBody());
                Object result = invoker.invoke(bookLog, resourceRequest);
                String response = result.toString();
                byte[] responseBytes = response.getBytes();
                exchange.sendResponseHeaders(200, responseBytes.length);
                OutputStream os = exchange.getResponseBody();
                os.write(responseBytes);
                os.close();
            } else {
                exchange.sendResponseHeaders(405, -1); return;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
