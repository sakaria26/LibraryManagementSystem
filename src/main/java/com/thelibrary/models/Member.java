package com.thelibrary.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Member extends User {

    private StringProperty PhoneNumber;
    private IntegerProperty numberOfBooksIssued;
    private StringProperty membershipStatus;

    public int getNumberOfBooksIssued() {
        return numberOfBooksIssuedProperty().get();
    }
    public void setNumberOfBooksIssued(int numberOfBooksIssued) {
        this.numberOfBooksIssuedProperty().set(numberOfBooksIssued);
    }
    public IntegerProperty numberOfBooksIssuedProperty() {
        if (numberOfBooksIssued == null)
            numberOfBooksIssued = new SimpleIntegerProperty(this, "number of books issued");
        return numberOfBooksIssued;
    }

    public String getMembershipStatus() {
        return membershipStatusProperty().get();
    }
    public void setMembershipStatus(String membershipStatus) {
        this.membershipStatusProperty().set(membershipStatus);
    }
    public StringProperty membershipStatusProperty() {
        if (membershipStatus == null)
            membershipStatus = new SimpleStringProperty(this, "membership status");
        return membershipStatus;
    }

    public String getPhoneNumber() {
        return phoneNumberProperty().get();
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumberProperty().set(phoneNumber);
    }
    public StringProperty phoneNumberProperty() {
        if (PhoneNumber == null)
            PhoneNumber = new SimpleStringProperty(this, "phone number");
        return PhoneNumber;
    }

}
