package com.thelibrary.database;

import com.thelibrary.models.Librarian;
import javafx.collections.ObservableList;

public interface LibrarianDAO {
    public ObservableList<Librarian> getAllStaff();
    public Librarian getStaff(String librarianid, String password);
    public void addStaff(Librarian librarian);
    public void deleteStaff(Librarian librarian);
    void updateLibrarian(String userID, String password);

}
