package com.thelibrary.database;

import com.thelibrary.models.EBook;
import javafx.collections.ObservableList;

import java.io.File;

public interface EBookDAO {

    String sp_AddEbook(String name, String author, int publicationyear, String genre, String description, File cover, File document, int pagecount, String format);
    void deleteEbook(EBook eBook);
    ObservableList<EBook> getAllEbooks();
}
