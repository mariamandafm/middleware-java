package main.java.ufrn.br.core;

import org.json.JSONObject;

import java.io.InputStream;
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
    public JSONObject unmarshall(String httpMethod, URI requestUri, InputStream requestBody){
        String fullRoute = requestUri.toString().substring(1);
        int firstSlash = fullRoute.indexOf('/');

        String remoteObject = fullRoute.substring(0, firstSlash);
        String route = fullRoute.substring(firstSlash+1);

        JSONObject jsonObject = new JSONObject();
        StringTokenizer tokenizer = new StringTokenizer(requestUri.toString());
        jsonObject.put("httpMethod", httpMethod);
        jsonObject.put("remoteObject", remoteObject);
        jsonObject.put("route", route);
        return jsonObject;
    }
}
