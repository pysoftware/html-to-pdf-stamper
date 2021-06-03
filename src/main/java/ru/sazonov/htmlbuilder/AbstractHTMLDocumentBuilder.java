package ru.sazonov.htmlbuilder;

import com.itextpdf.html2pdf.attach.Attacher;
import com.itextpdf.kernel.counter.event.IMetaInfo;
import com.itextpdf.kernel.pdf.DocumentProperties;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.styledxmlparser.IXmlParser;
import com.itextpdf.styledxmlparser.node.IDocumentNode;
import com.itextpdf.styledxmlparser.node.impl.jsoup.JsoupHtmlParser;
import lombok.*;
import ru.sazonov.htmlbuilder.thymeleaf.ThymeleafTemplateEngine;

import java.io.*;
import java.util.Objects;

public abstract class AbstractHTMLDocumentBuilder<C> implements HTMLDocumentBuilder<C> {
    @Getter
    private final HTMLDocument htmlDocument;

    protected AbstractHTMLDocumentBuilder() {
        htmlDocument = manipulateHTMLDocument();
    }

    @Override
    public HTMLDocument manipulateHTMLDocument() {
        return HTMLDocument.of(getInputStream()).build();
    }

    @Override
    public C getContext() {
        return null;
    }

    @SneakyThrows
    public OutputStream convertToPDF(OutputStream outputStream) {
        PdfWriter pdfWriter = new PdfWriter(outputStream);
        DocumentProperties documentProperties = (new DocumentProperties()).setEventCountingMetaInfo(new AbstractHTMLDocumentBuilder.HtmlMetaInfo());

        PdfDocument pdfDocument = new PdfDocument(pdfWriter, documentProperties);

        IXmlParser parser = new JsoupHtmlParser();
        IDocumentNode htmlTemplate = parser.parse(getHTML());
        Document documentLayout = Attacher.attach(htmlTemplate, pdfDocument, null);
        documentLayout.close();

        return outputStream;
    }

    private String getHTML() {
        String currentHTML = htmlDocument.html();

        if (Objects.nonNull(getContext())) {
            ThymeleafTemplateEngine<C> thymeleafTemplateEngine = new ThymeleafTemplateEngine<>(getContext());
            currentHTML = thymeleafTemplateEngine.process(currentHTML);
        }

        currentHTML = currentHTML.replace("${nextLine}", "<br />");

        return currentHTML;
    }

    private static class HtmlMetaInfo implements IMetaInfo {
        private static final long serialVersionUID = -295587336698550627L;

        private HtmlMetaInfo() {
        }
    }
}