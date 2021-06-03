package ru.sazonov.htmlbuilder;

import lombok.NonNull;

import java.io.InputStream;

public interface HTMLDocumentBuilder<C> {

    /**
     * Convert HTML raw text (from html file f.e.) to InputStream
     *
     * @return InputStream
     */
    @NonNull InputStream getInputStream();

    /**
     * Method for manipulating html document nodes
     *
     * @return HTMLDocument
     */
    HTMLDocument manipulateHTMLDocument();

    /**
     * Context must contains variables you need to inject to html template
     *
     * @return IContext
     */
    C getContext();

}
