package ru.sazonov.htmlbuilder.thymeleaf;

import org.thymeleaf.templateresource.ITemplateResource;
import org.thymeleaf.util.Validate;

import java.io.*;
import java.util.Objects;

public class TemplateResource<T> implements ITemplateResource {

    private final String path;
    private final T context;

    public TemplateResource(T context, String path) {
        Validate.notEmpty(path, "Resource Path cannot be null or empty");
        this.context = context;
        this.path = path.charAt(0) != '/' ? "/" + path : path;
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public String getBaseName() {
        return "";
    }

    @Override
    public boolean exists() {
        return Objects.nonNull(context.getClass().getResource(this.path));
    }

    @Override
    public Reader reader() {
        InputStream inputStream = context.getClass().getResourceAsStream(this.path);
        return new BufferedReader(new InputStreamReader(new BufferedInputStream(inputStream)));
    }

    @Override
    public ITemplateResource relative(String s) {
        return null;
    }
}