package com.thelibrary.database;

import com.thelibrary.models.Librarian;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import static com.thelibrary.util.DatabaseUtil.connect;

public class LibrarianDAOImpl implements LibrarianDAO {
    @Override
    public ObservableList<Librarian> getAllStaff() {
        ObservableList<Librarian> librarianObservableList = FXCollections.observableArrayList();
        try (PreparedStatement queryLibrarian = Objects.requireNonNull(connect()).prepareStatement("select * from librarian")){
            ResultSet resultSet = queryLibrarian.executeQuery();
            while (resultSet.next()) {
                Librarian librarian = new Librarian();
                librarian.setUsername(resultSet.getString("librarianid"));
                librarian.setName(resultSet.getString("librarianname"));
                librarian.setSurname(resultSet.getString("librariansurname"));
                librarian.setPassword(resultSet.getString("librarianpassword"));
                librarian.setEmail(resultSet.getString("librarianemail"));
                librarian.setRole(resultSet.getString("role"));

                librarianObservableList.add(librarian);
            }
        }catch (SQLException sqlException){
            sqlException.getLocalizedMessage();
        }
        return librarianObservableList;
    }

    @Override
    public Librarian getStaff(String librarianid, String password) {
        Librarian librarian = new Librarian();
        try(PreparedStatement retrieveLibrarian = Objects.requireNonNull(connect()).prepareStatement("select * from librarian where librarianid = ? and  librarianpassword = ?")){
            retrieveLibrarian.setString(1, librarianid);
            retrieveLibrarian.setString(2, password);
            retrieveLibrarian.executeQuery();
            ResultSet resultSet = retrieveLibrarian.executeQuery();
            while (resultSet.next()){
                librarian.setUsername(resultSet.getString("librarianid"));
                librarian.setName(resultSet.getString("librarianname"));
                librarian.setSurname(resultSet.getString("librariansurname"));
                librarian.setEmail(resultSet.getString("librarianemail"));
                librarian.setRole(resultSet.getString("role"));
            }
        }catch (SQLException sqlException){
            sqlException.getLocalizedMessage();
        }
        return librarian;
    }

    @Override
    public void addStaff(Librarian librarian) {
        try(CallableStatement addStaff = Objects.requireNonNull(connect()).prepareCall("{call sp_AddLibrarian(?,?,?,?,?,?)}")){
            addStaff.setString(1, librarian.getUsername());
            addStaff.setString(2, librarian.getName());
            addStaff.setString(3, librarian.getSurname());
            addStaff.setString(4, librarian.getEmail());
            addStaff.setString(5, librarian.getPassword());
            addStaff.setString(6, librarian.getRole());
           addStaff.executeUpdate();
        }catch (SQLException sqlException){
            sqlException.getLocalizedMessage();
        }
    }

    @Override
    public void deleteStaff(Librarian librarian) {
        try(CallableStatement deleteLibrarian = Objects.requireNonNull(connect()).prepareCall("{call sp_DeleteLibrarian(?)}")) {
            deleteLibrarian.setString(1, librarian.getUsername());
            deleteLibrarian.executeUpdate();
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    @Override
    public void updateLibrarian(String userID, String password) {
        try (CallableStatement callableStatement = Objects.requireNonNull(connect()).prepareCall("{call sp_UpdateLibrarian(?, ?)}")){
            callableStatement.setString(1, userID);
            callableStatement.setString(2, password);
            callableStatement.executeUpdate();
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }


}
