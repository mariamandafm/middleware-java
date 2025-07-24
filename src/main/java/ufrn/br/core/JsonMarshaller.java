package main.java.ufrn.br.core;

import org.json.JSONObject;

import java.io.*;
import java.net.URI;
import java.util.StringTokenizer;

public class JsonMarshaller {

    // chamado antes de enviar resposta
    public void marshall(URI requestUri, InputStream requestBody){
        JSONObject jsonObject = new JSONObject();
        System.out.println(requestUri);
        System.out.println(requestBody);
    }

    // chamado para ler requisição
    public JSONObject unmarshall(String httpMethod, String requestUri, char[] requestBody) throws IOException {
        String fullRoute = requestUri;
        int firstSlash = fullRoute.indexOf('/', 1);

        String remoteObject = fullRoute.substring(1, firstSlash);
        String route = fullRoute.substring(firstSlash+1);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("httpMethod", httpMethod);
        jsonObject.put("remoteObject", remoteObject);
        jsonObject.put("route", route);

        // Ler corpo
        BufferedReader reader = new BufferedReader(new CharArrayReader(requestBody));
        if (reader.ready()){
            StringBuilder jsonBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null){
                jsonBuilder.append(line);
            }
            JSONObject jsonBody = new JSONObject(jsonBuilder.toString());
            jsonObject.put("requestBody", jsonBody);
        }
        return jsonObject;
    }
}
