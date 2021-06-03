package ru.sazonov.htmlbuilder.thymeleaf;

import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.templateresolver.AbstractConfigurableTemplateResolver;
import org.thymeleaf.templateresource.ITemplateResource;

import java.util.Map;

public class TemplateResolver<T> extends AbstractConfigurableTemplateResolver {

    private final T context;

    public TemplateResolver(T context) {
        this.context = context;
    }

    @Override
    protected ITemplateResource computeTemplateResource(IEngineConfiguration iEngineConfiguration, String ownerTemplate, String template, String resourceName, String characterEncoding, Map<String, Object> map) {
        return new TemplateResource<T>(context, resourceName);
    }
}