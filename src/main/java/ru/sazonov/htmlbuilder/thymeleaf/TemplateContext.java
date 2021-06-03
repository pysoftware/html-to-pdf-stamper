package ru.sazonov.htmlbuilder.thymeleaf;

import org.thymeleaf.context.AbstractContext;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class TemplateContext<T> extends AbstractContext {

    public TemplateContext(T obj) {
        List<Method> methods = Arrays.asList(obj.getClass().getDeclaredMethods());

        methods.stream()
                .filter(method -> method.getName().startsWith("get"))
                .forEach(method -> {
                    try {
                        String methodName = method.getName().replace("get", "");
                        String fieldName = methodName.substring(0, 1).toLowerCase() + methodName.substring(1);
                        this.setVariable(fieldName, method.invoke(obj));
                    } catch (Exception ignored) {
                    }
                });
    }

}