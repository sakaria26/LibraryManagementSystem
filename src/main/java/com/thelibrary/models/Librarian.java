package com.thelibrary.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Librarian extends User {

    private StringProperty role;

    public String getRole() {
        return roleProperty().get();
    }
    public void setRole(String role) {
        this.roleProperty().set(role);
    }
    public StringProperty roleProperty() {
        if (role == null)
            role = new SimpleStringProperty(this, "role");
        return role;
    }

    public Librarian(){
        super();
        this.role = null;
    }

    public Librarian(StringProperty Username, StringProperty Password, StringProperty Name, StringProperty Surname, StringProperty Email, StringProperty Role){
        super(Username, Password, Name, Surname, Email);
        this.role = Role;
    }

    public void addLibrarian(){}
    public void deleteLibrarian(){}
    public void updateLibrarian(){}
    public void addMembers(){}
    public void deleteMembers(){}
    public void changeMembershipStatus(){}
    public void addMedia(){}
    public void deleteMedia(){}
    public void issueMedia(){}
    public void viewRequests(){}
    public void markMediaReturn(){}
    public void orderMedia(){}
    public void markFinePaid(){}

}
