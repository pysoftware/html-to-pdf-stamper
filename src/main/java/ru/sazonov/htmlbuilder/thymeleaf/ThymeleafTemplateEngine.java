package ru.sazonov.htmlbuilder.thymeleaf;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;

public class ThymeleafTemplateEngine<T> {

    private final TemplateEngine templateEngine;
    private final TemplateContext<T> templateContext;

    public ThymeleafTemplateEngine(T context) {
        this.templateEngine = new TemplateEngine();
        this.templateContext = new TemplateContext<>(context);
        TemplateResolver<T> templateResolver = new TemplateResolver<>(context);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCacheTTLMs(3600000L);
    }

    public String process(String html) {
        return this.templateEngine.process(html, templateContext);
    }
}
