package main.java.ufrn.br.core;

import main.java.ufrn.br.annotations.*;
import org.json.JSONObject;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Invoker {

    private static final Map<String, Class<? extends Annotation>> annotationMap = Map.of(
            "GET", Get.class,
            "POST", Post.class,
            "PUT", Put.class,
            "DELETE", Delete.class
    );

    public Object invoke(Object instance, JSONObject resourceRequest)
            throws InvocationTargetException, IllegalAccessException {

        String httpMethod = resourceRequest.getString("httpMethod");
        String route = resourceRequest.getString("route");
        JSONObject requestBody = resourceRequest.optJSONObject("requestBody");

        Class<? extends Annotation> annotationClass = annotationMap.get(httpMethod);
        if (annotationClass == null) return "Unsupported HTTP method";

        for (Method method : instance.getClass().getDeclaredMethods()) {
            Annotation annotation = method.getAnnotation(annotationClass);
            if (annotation == null) continue; // tem anotação

            String annotatedRoute = extractRouteFromAnnotation(annotation);
            String regex = annotatedRoute.replaceAll("\\{\\w+}", "(\\\\d+)");
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(route);

            if (!matcher.matches()) continue; // tem rota

            Parameter[] params = method.getParameters();
            Object[] args = new Object[params.length];

            for (int i = 0; i < params.length; i++) {
                Parameter param = params[i];

                if (param.isAnnotationPresent(Param.class)) {
                    String value = matcher.group(i + 1);
                    args[i] = Integer.parseInt(value);
                } else if (param.isAnnotationPresent(RequestBody.class)) {
                    args[i] = requestBody;
                }
            }

            return method.invoke(instance, args);
        }

        return "Method not found";
    }

    private String extractRouteFromAnnotation(Annotation annotation) {
        try {
            return (String) annotation.annotationType().getMethod("route").invoke(annotation);
        } catch (Exception e) {
            throw new RuntimeException("Failed to extract route from annotation", e);
        }
    }
}

