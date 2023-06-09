package com.thelibrary.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

import java.io.File;

public class EBook extends EMedia{
    private StringProperty format;
    private IntegerProperty pagecount;
    private File document;



    public String getFormat() {
        return formatProperty().get();
    }
    public void setFormat(String format) {
        this.formatProperty().set(format);
    }
    public StringProperty formatProperty() {
        if (format == null)
            format = new SimpleStringProperty(this, "format");
        return format;
    }

    public int getPagecount() {
        return pagecountProperty().get();
    }
    public void setPagecount(int pagecount) {
        this.pagecountProperty().set(pagecount);
    }
    public IntegerProperty pagecountProperty() {
        if (pagecount == null)
            pagecount = new SimpleIntegerProperty(this, "page count");
        return pagecount;
    }

    public File getDocument() {
        return this.document;
    }

    public void setDocument(File document) {
        this.document = document;
    }

    public EBook(StringProperty Mediaid, StringProperty Name, StringProperty Author, IntegerProperty Publicationyear, StringProperty Genre, StringProperty Format, StringProperty Status, Image Cover, File Document, StringProperty Description, IntegerProperty Price) {
        super(Mediaid, Name, Author, Publicationyear, Genre, Status, Cover, Description);
        this.format = Format;
        this.pagecount = Price;
        this.document = Document;
    }
    public EBook() {
        super();
        this.format = null;
        this.pagecount = null;
        this.document = null;
    }
}
