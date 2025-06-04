package main.java.ufrn.br.core;

import main.java.ufrn.br.annotations.Get;
import main.java.ufrn.br.annotations.Param;
import main.java.ufrn.br.annotations.Post;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
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
                if (post != null && post.route().equals(route)){
                    return method.invoke(instance);
                }
            }
        }


//        for (Method method : instance.getClass().getDeclaredMethods()){
//            Get get = method.getAnnotation(Get.class);
//            if (get != null && get.route().equals(methodName)){
//                Parameter[] params = method.getParameters();
//                if (params.length > 0){
//                    for (Parameter param : params){
//                        if (param.isAnnotationPresent(Param.class)){
//                            Param annotation = param.getAnnotation(Param.class);
//                            System.out.println("Notação: " + annotation.name());
//                        }
//                    }
//                }
//                return method.invoke(instance);
//            }
//        }
        return "Method not found";
    }

}
