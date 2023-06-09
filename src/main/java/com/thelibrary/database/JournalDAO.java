package com.thelibrary.database;

import com.thelibrary.models.Journal;
import javafx.collections.ObservableList;

import java.io.File;

public interface JournalDAO {
    String sp_AddJournal(String name, String author, int publicationyear, String areaofstudy, int citations, int volume, int issue, File cover, File document);
    void deleteJournal(Journal journal);
    ObservableList<Journal> getAllJournals();
}
