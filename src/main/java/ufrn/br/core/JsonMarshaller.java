package main.java.ufrn.br.core;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.StringTokenizer;

public class JsonMarshaller {

    // Chamado antes de enviar resposta
    public void marshall(URI requestUri, InputStream requestBody){
        JSONObject jsonObject = new JSONObject();
        System.out.println(requestUri);
        System.out.println(requestBody);
    }

    // Chamado para ler requisição
    public JSONObject unmarshall(String httpMethod, URI requestUri, InputStream requestBody) throws IOException {
        String fullRoute = requestUri.toString().substring(1);
        int firstSlash = fullRoute.indexOf('/');

        String remoteObject = fullRoute.substring(0, firstSlash);
        String route = fullRoute.substring(firstSlash+1);

        JSONObject jsonObject = new JSONObject();
        StringTokenizer tokenizer = new StringTokenizer(requestUri.toString());
        jsonObject.put("httpMethod", httpMethod);
        jsonObject.put("remoteObject", remoteObject);
        jsonObject.put("route", route);

        // Ler corpo
        BufferedReader reader = new BufferedReader(new InputStreamReader(requestBody));
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
