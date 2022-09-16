package com.thelibrary.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

abstract class EMedia extends Media{
    private StringProperty genre;
    private Image cover;
    private StringProperty description;



    public String getGenre() {
        return genreProperty().get();
    }
    public void setGenre(String genre) {
        this.genreProperty().set(genre);
    }
    public StringProperty genreProperty() {
        if (genre ==  null)
            genre = new SimpleStringProperty(this, "genre");
        return genre;
    }

    public Image getCover() {
        return cover;
    }

    public void setCover(Image cover) {
        this.cover = cover;
    }

    public String getDescription() {
        return descriptionProperty().get();
    }
    public void setDescription(String description) {
        this.descriptionProperty().set(description);
    }
    public StringProperty descriptionProperty() {
        if (description == null)
            description = new SimpleStringProperty(this, "description");
        return description;
    }



    public EMedia(StringProperty Mediaid, StringProperty Name, StringProperty Author, IntegerProperty Publicationyear, StringProperty Genre, StringProperty Status, Image Cover, StringProperty description) {
        super(Mediaid, Name, Author, Publicationyear, Status);
        this.genre = Genre;
        this.cover = Cover;
        this.description = description;
    }

    public EMedia(){
        super();
        this.genre = null;
        this.cover = null;
        this.description = null;
    }
    @Override
    public void displayDetails() {

    }
}
