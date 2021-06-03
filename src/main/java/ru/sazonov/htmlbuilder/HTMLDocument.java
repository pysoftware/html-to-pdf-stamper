package ru.sazonov.htmlbuilder;

import lombok.Data;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Data
public final class HTMLDocument {
    private final String baseUri = "";
    private final Charset documentCharset = StandardCharsets.UTF_8;

    private Document htmlDocument;

    public HTMLDocument(Document htmlDocument) {
        this.htmlDocument = htmlDocument;
    }

    @SneakyThrows
    public HTMLDocument(InputStream inputStream) {
        this.htmlDocument = Jsoup.parse(inputStream, documentCharset.toString(), baseUri, Parser.xmlParser());
    }

    @SneakyThrows
    public HTMLDocument(String rawHTML) {
        this.htmlDocument = Jsoup.parse(rawHTML, baseUri, Parser.xmlParser());
    }

    protected String html() {
        return htmlDocument.html();
    }

    protected Element html(String html) {
        return htmlDocument.html(html);
    }

    public static HTMLDocumentBuilder of(InputStream inputStream) {
        return new HTMLDocument(inputStream).new HTMLDocumentBuilder();
    }

    public static HTMLDocumentBuilder of(Document htmlDocument) {
        return new HTMLDocument(htmlDocument).new HTMLDocumentBuilder();
    }

    public static HTMLDocumentBuilder of(String rawHTML) {
        return new HTMLDocument(rawHTML).new HTMLDocumentBuilder();
    }

    public final class HTMLDocumentBuilder {

//        public HTMLDocumentBuilder injectValue(String key, String value) {
//            String rawHtml = htmlDocument.html();
//            rawHtml = rawHtml.replace(String.format("${%s}", key), value);
//            HTMLDocument.this.htmlDocument = htmlDocument.html(rawHtml).ownerDocument();
//            return this;
//        }

        protected Element html(String html) {
            return htmlDocument.html(html);
        }

        public HTMLDocument build() {
            return HTMLDocument.this;
        }

    }
}