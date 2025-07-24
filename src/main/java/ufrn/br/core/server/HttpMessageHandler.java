package main.java.ufrn.br.core.server;

import main.java.ufrn.br.core.Invoker;
import main.java.ufrn.br.core.JsonMarshaller;
import main.java.ufrn.br.core.Lookup;
import org.json.JSONObject;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class HttpMessageHandler{
    public HttpMessageHandler() {
    }

    private JsonMarshaller marshaller = new JsonMarshaller();
    private Invoker invoker = new Invoker();
    private Lookup lookup = Lookup.getInstance();

    public String handle(InputStream input) throws IOException {
        BufferedReader message = new BufferedReader(new InputStreamReader(input));
        String requestURI = message.readLine();
        String requestMethod = "";
        String requestPath = "";
        if (requestURI != null){
            String[] URIParts = requestURI.split(" ");
            requestMethod = URIParts[0];
            requestPath = URIParts[1];

            int contentLenght = 0;
            String line;

            while ((line = message.readLine()) != null){
                if (line.trim().isEmpty()) break;
                if (line.toLowerCase().startsWith("content-length")) {
                    contentLenght = Integer.parseInt(line.split(":")[1].trim());
                }
            }
            char[] requestBody = new char[contentLenght];
            if (contentLenght > 0) {
                message.read(requestBody, 0, contentLenght);
            }
            //System.out.println(input);

            JSONObject resourceRequest = marshaller.unmarshall(requestMethod, requestPath, requestBody);
            //System.out.println(resourceRequest);
            Object instance = lookup.getObject(resourceRequest.getString("remoteObject"));
            try {
                Object result = invoker.invoke(instance, resourceRequest);
                return createHttpResponse(200, result.toString());
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        return createHttpResponse(500, "Não foi possivel processar a requisição");
    }

    private String createHttpResponse(int statusCode, String content){
        HttpStatus status = HttpStatus.fromCode(statusCode);
        StringBuilder response = new StringBuilder();
        response.append("HTTP/1.0 " + status.toString() + "\r\n");
        response.append("Server: WebServer\r\n");
        response.append("Content-Type: application/json\r\n");
        byte[] contentBytes = content.getBytes(StandardCharsets.UTF_8);
        if (content != null){
            response.append("Content-Length: ").append(contentBytes.length).append("\r\n");
            response.append("\r\n");
            response.append(content);
        }
        return response.toString();
    }
}
