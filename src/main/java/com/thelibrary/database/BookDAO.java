package com.thelibrary.database;

import com.thelibrary.models.Book;
import javafx.collections.ObservableList;

import java.io.File;

public interface BookDAO {
    String addBook(String name, String author, int publicationyear, String genre, double price, String description, File cover, int pagecount);
    void deleteBook(Book book);
    ObservableList<Book> getAllBooks();
    ObservableList<Book> getAvailableBooks();
}
