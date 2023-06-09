package com.thelibrary.database;

import com.thelibrary.models.Journal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

import java.io.*;
import java.sql.*;
import java.util.Objects;

import static com.thelibrary.util.DatabaseUtil.connect;
import static com.thelibrary.util.GeneralUtil.mediaID;

public class JournalDAOImpl implements JournalDAO{
    @Override
    public String sp_AddJournal(String name, String author, int publicationyear, String areaofstudy, int citations, int volume, int issue, File cover, File document) {
        try {
            Connection connection = connect();
            assert connection != null;
            CallableStatement addJournal = Objects.requireNonNull(connect()).prepareCall("{call sp_AddJournal(?,?,?,?,?,?,?,?,?,?)}");

            FileInputStream input = new FileInputStream(document);
            FileInputStream coverInput = new FileInputStream(cover);
            String journalid = mediaID("journal", name);
            addJournal.setString(1, journalid);
            addJournal.setString(2, name);
            addJournal.setString(3, author);
            addJournal.setInt(4, publicationyear);
            addJournal.setString(5, areaofstudy);
            addJournal.setInt(6, citations);
            addJournal.setInt(7, volume);
            addJournal.setInt(8, issue);
            addJournal.setBinaryStream(9, (InputStream)input, (int)document.length());
            addJournal.setBinaryStream(10, (InputStream)coverInput, (int)cover.length());
            addJournal.registerOutParameter(11, Types.BOOLEAN);

            addJournal.executeUpdate();

            boolean success = addJournal.getBoolean(11);
            if (success) {
                return name+" has been saved with ID: "+ journalid;
            }else {
                return "Error Occurred. Please try again";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteJournal(Journal journal) {
        try (CallableStatement deleteJournal = Objects.requireNonNull(connect()).prepareCall("{call sp_DeleteJournal(?)}")){
            deleteJournal.setString(1, journal.getMediaid());
            deleteJournal.executeUpdate();
        }catch (SQLException sqlException){
            sqlException.getLocalizedMessage();
        }
    }

    @Override
    public ObservableList<Journal> getAllJournals() {
        ObservableList<Journal> journalObservableList = FXCollections.observableArrayList();
        try (PreparedStatement getAllJournals = Objects.requireNonNull(connect()).prepareStatement("select * from journal")){
            ResultSet resultSet = getAllJournals.executeQuery();

            while (resultSet.next()){
                Journal journal = new Journal();
                journal.setMediaid(resultSet.getString("journalid"));
                journal.setName(resultSet.getString("title"));
                journal.setAuthor(resultSet.getString("author"));
                journal.setPublicationyear(resultSet.getInt("publicationyear"));
                journal.setAreaOfStudy(resultSet.getString("areaofstudy"));
                journal.setCitations(resultSet.getInt("citations"));
                journal.setStatus(resultSet.getString("status"));
                journal.setVolume(resultSet.getInt("volume"));
                journal.setIssue(resultSet.getInt("issue"));
                InputStream inputStream = resultSet.getBinaryStream("document");
                InputStream coverInputStream = resultSet.getBinaryStream("cover");
                OutputStream outputStream = new FileOutputStream(new File("./assets/document/"+resultSet.getString("title")+".pdf"));
                OutputStream coverOutputStream = new FileOutputStream(new File("./assets/images/"+resultSet.getString("title")+".png"));
                byte[] content = new byte[1024];
                byte[] imagecontent = new byte[1024];
                int documentsize, imagesize = 0;
                while ((documentsize = inputStream.read(content)) != -1) {
                    outputStream.write(content, 0, documentsize);
                }
                outputStream.close();
                inputStream.close();

                while ((imagesize = coverInputStream.read(imagecontent)) != -1){
                    coverOutputStream.write(imagecontent, 0, imagesize);
                }
                coverOutputStream.close();
                coverInputStream.close();
                File document = new File("./assets/document/" + resultSet.getString("title")+".pdf");
                Image image = new Image("file:./assets/images/" + resultSet.getString("title"), 200, 342, true, true);
                journal.setDocument(document);
                journal.setCover(image);
                journalObservableList.add(journal);
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }

        return journalObservableList;
    }
}
