package main.java.ufrn.br.core;

import main.java.ufrn.br.annotations.*;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Invoker {
    public Object invoke(Object instance, JSONObject resourceRequest) throws InvocationTargetException, IllegalAccessException {
        String httpMethod = resourceRequest.getString("httpMethod");
        String route = resourceRequest.getString("route");

        if (httpMethod.equals("GET")){
            for (Method method : instance.getClass().getDeclaredMethods()){
                Get get = method.getAnnotation(Get.class);
                if (get != null){
                    String annotatedRoute = get.route(); // book/{id}
                    String regex = annotatedRoute.replaceAll("\\{\\w+}", "(\\\\d+)");
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(route);

                    if (matcher.matches()){
                        Parameter[] params = method.getParameters();
                        Object[] args = new Object[params.length];
                        for (int i = 0; i < params.length; i++) {
                            Parameter param = params[i]; // Param do metodo
                            if (param.isAnnotationPresent(Param.class)) { // Checa de param é anotado
                                // Extrai o valor correspondente da rota usando o grupo regex
                                String value = matcher.group(i+1);
                                args[i] = Integer.parseInt(value); // trata como int por simplicidade
                            }
                        }
                        return method.invoke(instance, args);
                    }
                }
            }
        } else if (httpMethod.equals("POST")){
            for (Method method : instance.getClass().getDeclaredMethods()){
                Post post = method.getAnnotation(Post.class);
                if (post != null){
                    String annotatedRoute = post.route(); // book/{id}
                    String regex = annotatedRoute.replaceAll("\\{\\w+}", "(\\\\d+)");
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(route);

                    if (matcher.matches()){
                        Parameter[] params = method.getParameters();
                        Object[] args = new Object[params.length];
                        for (int i = 0; i < params.length; i++) {
                            Parameter param = params[i];
                            if (param.isAnnotationPresent(Param.class)) {
                                String value = matcher.group(i+1);
                                args[i] = Integer.parseInt(value);
                            }
                            if (param.isAnnotationPresent(RequestBody.class)){
                                JSONObject requestBody = resourceRequest.getJSONObject("requestBody");
                                args[i] = requestBody;
                            }
                        }
                        return method.invoke(instance, args);
                    }
                }
            }
        } else if (httpMethod.equals("PUT")){
            for (Method method : instance.getClass().getDeclaredMethods()){
                Put put = method.getAnnotation(Put.class);
                if (put != null){
                    String annotatedRoute = put.route(); // book/{id}
                    String regex = annotatedRoute.replaceAll("\\{\\w+}", "(\\\\d+)");
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(route);

                    if (matcher.matches()){
                        Parameter[] params = method.getParameters();
                        Object[] args = new Object[params.length];
                        for (int i = 0; i < params.length; i++) {
                            Parameter param = params[i];
                            if (param.isAnnotationPresent(Param.class)) {
                                String value = matcher.group(i+1);
                                args[i] = Integer.parseInt(value);
                            }
                            if (param.isAnnotationPresent(RequestBody.class)){
                                JSONObject requestBody = resourceRequest.getJSONObject("requestBody");
                                args[i] = requestBody;
                            }
                        }
                        return method.invoke(instance, args);
                    }
                }
            }
        } else if (httpMethod.equals("DELETE")){
            for (Method method : instance.getClass().getDeclaredMethods()) {
                Delete delete = method.getAnnotation(Delete.class);
                if (delete != null) {
                    String annotatedRoute = delete.route(); // book/{id}
                    String regex = annotatedRoute.replaceAll("\\{\\w+}", "(\\\\d+)");
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(route);

                    if (matcher.matches()) {
                        Parameter[] params = method.getParameters();
                        Object[] args = new Object[params.length];
                        for (int i = 0; i < params.length; i++) {
                            Parameter param = params[i];
                            if (param.isAnnotationPresent(Param.class)) {
                                String value = matcher.group(i + 1);
                                args[i] = Integer.parseInt(value);
                            }
                        }
                        return method.invoke(instance, args);
                    }
                }
            }
        }
        return "Method not found";
    }
}
