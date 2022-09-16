package com.thelibrary.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

import java.io.File;

public class Journal extends Media{
    private StringProperty AreaOfStudy;
    private IntegerProperty Citations;
    private IntegerProperty Volume;
    private IntegerProperty Issue;
    private File Document;
    private Image Cover;

    public String getAreaOfStudy() {
        return areaOfStudyProperty().get();
    }
    public void setAreaOfStudy(String areaOfStudy) {
        this.areaOfStudyProperty().set(areaOfStudy);
    }
    public StringProperty areaOfStudyProperty() {
        if (AreaOfStudy == null)
            AreaOfStudy = new SimpleStringProperty(this, "Area of Study");
        return AreaOfStudy;
    }

    public int getCitations() {
        return citationsProperty().get();
    }
    public void setCitations(int citations) {
        this.citationsProperty().set(citations);
    }
    public IntegerProperty citationsProperty() {
        if (Citations == null)
            Citations = new SimpleIntegerProperty(this, "Citations");
        return Citations;
    }

    public int getVolume() {
        return volumeProperty().get();
    }
    public void setVolume(int volume) {
        this.volumeProperty().set(volume);
    }
    public IntegerProperty volumeProperty() {
        if (Volume == null)
            Volume = new SimpleIntegerProperty(this, "Volume");
        return Volume;
    }



    public int getIssue() {
        return issueProperty().get();
    }
    public void setIssue(int issue) {
        this.issueProperty().set(issue);
    }
    public IntegerProperty issueProperty() {
        if (Issue == null)
            Issue = new SimpleIntegerProperty(this, "Issue");
        return Issue;
    }



    public File getDocument() {
        return this.Document;
    }
    public void setDocument(File document) {
        this.Document = document;
    }

    public Image getCover() {
        return Cover;
    }

    public void setCover(Image cover) {
        Cover = cover;
    }

    public Journal(StringProperty Mediaid, StringProperty Name, StringProperty Author, IntegerProperty Publicationyear, StringProperty AreaofStudy, IntegerProperty Citations, StringProperty Status) {
        super(Mediaid, Name, Author, Publicationyear, Status);
        this.AreaOfStudy = AreaofStudy;
        this.Citations = Citations;
        //this.Document = document;
    }

    public Journal(){
        super();
        this.AreaOfStudy = null;
        this.Citations = null;
    }
    @Override
    public void displayDetails() {

    }
}
