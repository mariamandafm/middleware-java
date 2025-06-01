package main.java.ufrn.br.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// TODO: Colocar nomes mais descritivos como Service, Controller, Repository, ...
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RemoteComponent {
    String name();
    String basePath();
}
