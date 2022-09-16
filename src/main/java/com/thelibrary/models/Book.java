package com.thelibrary.models;

import javafx.beans.property.*;
import javafx.scene.image.Image;

public class Book extends Media{
    private StringProperty genre;
    private DoubleProperty price;
    private StringProperty description;
    private IntegerProperty pagecount;
    private Image cover;

    public String getGenre() {
        return genreProperty().get();
    }
    public void setGenre(String genre) {
        this.genreProperty().set(genre);
    }
    public StringProperty genreProperty() {
        if (genre == null)
            genre = new SimpleStringProperty(this, "genre");
        return genre;
    }
    public String getDescription() {
        return description.get();
    }
    public void setDescription(String description) {
        this.descriptionProperty().set(description);
    }
    public StringProperty descriptionProperty() {
        if (description == null)
           description = new SimpleStringProperty(this, "description");
        return description;
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

    public double getPrice() {
        return priceProperty().get();
    }
    public void setPrice(double price) {
        this.priceProperty().set(price);
    }
    public DoubleProperty priceProperty() {
        if (price == null)
            price = new SimpleDoubleProperty(this, "price");
        return price;
    }


    public Image getCover() {
        return cover;
    }
    public void setCover(Image cover) {
        this.cover = cover;
    }


    public Book(StringProperty Mediaid, StringProperty Name, StringProperty Author, IntegerProperty Publicationyear, StringProperty Genre, DoubleProperty Price, Image Cover, StringProperty Status, StringProperty description, IntegerProperty pagecount) {
        super(Mediaid, Name, Author, Publicationyear, Status);
        this.genre = Genre;
        this.price = Price;
        this.cover = Cover;
        this.pagecount = pagecount;
        this.description = description;
    }

    public Book(){
        super();
        this.genre = null;
        this.price = null;
        this.cover = null;
        this.description = null;
        this.pagecount = null;
    }
    @Override
    public void displayDetails() {

    }

    @Override
    public String toString() {
        return "Book{" +
                super.toString()+
                "genre=" + getGenre() +
                ", price=" + getPrice() +
                ", description=" + getDescription() +
                ", pagecount=" + getPagecount()+
                '}';
    }
}
