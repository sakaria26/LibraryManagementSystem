package com.thelibrary.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

abstract class Media {
    private StringProperty mediaid;
    private StringProperty name;
    private StringProperty author;
    private IntegerProperty publicationyear;
    private StringProperty status;

    public void setMediaid(String mediaid) {
        this.mediaidProperty().set(mediaid);
    }
    public String getMediaid() {
        return mediaidProperty().get();
    }
    public StringProperty mediaidProperty(){
        if (mediaid == null)
            mediaid = new SimpleStringProperty(this, "media id");
        return mediaid;
    }

    public void setName(String name) {
        this.nameProperty().set(name);
    }
    public String getName() {
        return nameProperty().get();
    }
    public StringProperty nameProperty(){
        if (name == null)
            name = new SimpleStringProperty(this, "name");
        return name;
    }

    public void setAuthor(String author) {
        this.authorProperty().set(author);
    }
    public String getAuthor() {
        return authorProperty().get();
    }
    public StringProperty authorProperty(){
        if (author == null)
            author = new SimpleStringProperty(this, "author");
        return author;
    }


    public void setPublicationyear(int publicationyear) {
        this.publicationYearProperty().set(publicationyear);
    }
    public int getPublicationyear() {
        return publicationYearProperty().get();
    }
    public IntegerProperty publicationYearProperty(){
        if (publicationyear == null)
            publicationyear= new SimpleIntegerProperty(this, "publication year");
        return publicationyear;
    }

    public void setStatus(String status) {
        this.statusProperty().set(status);
    }
    public String getStatus() {
        return statusProperty().get();
    }
    public StringProperty statusProperty(){
        if (status == null)
            status = new SimpleStringProperty(this, "status");
        return status;
    }

    public Media(StringProperty Mediaid, StringProperty Name, StringProperty Author, IntegerProperty Publicationyear, StringProperty Status) {
        this.mediaid = Mediaid;
        this.name = Name;
        this.author = Author;
        this.publicationyear = Publicationyear;
        this.status = Status;
    }

    public Media() {
        this.mediaid = null;
        this.name = null;
        this.author = null;
        this.publicationyear = null;
        this.status = null;
    }

    @Override
    public String toString() {
        return "Media{" +
                "mediaid=" + getMediaid() +
                ", name=" + getName() +
                ", author=" + getAuthor() +
                ", publicationyear=" + getPublicationyear() +
                ", status=" + getStatus() +
                '}';
    }

    public abstract void displayDetails();
}