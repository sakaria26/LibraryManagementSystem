package com.thelibrary.models;

import java.util.Date;

public class Request {
        private String RequestID;
        private Book Book;
        private Date RequestDate;
        private String Status;

    public String getRequestID() {
        return RequestID;
    }

    public void setRequestID(String requestID) {
        RequestID = requestID;
    }

    public Book getBook() {
        return this.Book;
    }

    public void setBook(Book book) {
        this.Book = book;
    }

    public Date getRequestDate() {
        return RequestDate;
    }

    public void setRequestDate(Date requestDate) {
        RequestDate = requestDate;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public Request(String requestID, Book book, Date requestDate, String status) {
        RequestID = requestID;
        Book = book;
        RequestDate = requestDate;
        Status = status;
    }

    public Request() {
        RequestID = "";
        Book = new Book();
        RequestDate = new Date();
        Status = "pending";
    }
}
