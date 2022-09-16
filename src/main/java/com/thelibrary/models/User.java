package com.thelibrary.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

abstract class User {
    private StringProperty Username;
    private StringProperty Password;
    private StringProperty Name;
    private StringProperty Surname;
    private StringProperty Email;

    public String getUsername() {
        return usernameProperty().get();
    }
    public void setUsername(String username) {
        this.usernameProperty().set(username);
    }
    public StringProperty usernameProperty() {
        if (Username == null)
            Username = new SimpleStringProperty(this, "username");
        return Username;
    }



    public String getPassword() {
        return passwordProperty().get();
    }
    public void setPassword(String password) {
        this.passwordProperty().set(password);
    }
    public StringProperty passwordProperty() {
        if (Password == null)
            Password = new SimpleStringProperty(this, "password");
        return Password;
    }



    public String getName() {
        return nameProperty().get();
    }
    public void setName(String name) {
        this.nameProperty().set(name);
    }
    public StringProperty nameProperty() {
        if (Name == null)
            Name = new SimpleStringProperty(this, "name");
        return Name;
    }



    public String getSurname() {
        return surnameProperty().get();
    }
    public void setSurname(String surname) {
        this.surnameProperty().set(surname);
    }
    public StringProperty surnameProperty() {
        if (Surname == null)
            Surname = new SimpleStringProperty(this, "surname");
        return Surname;
    }



    public String getEmail() {
        return emailProperty().get();
    }
    public void setEmail(String email) {
        this.emailProperty().set(email);
    }
    public StringProperty emailProperty() {
        if (Email == null)
            Email = new SimpleStringProperty(this, "email");
        return Email;
    }



    public User(StringProperty Username, StringProperty Password, StringProperty Name, StringProperty Surname, StringProperty Email) {
        this.Username = Username;
        this.Password = Password;
        this.Name = Name;
        this.Surname = Surname;
        this.Email = Email;
    }

    public User() {
        this.Username = new SimpleStringProperty();
        this.Password = new SimpleStringProperty();
        this.Name = new SimpleStringProperty();
        this.Surname = new SimpleStringProperty();
        this.Email = new SimpleStringProperty();
    }
}
