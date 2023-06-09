package com.thelibrary.util;

import java.sql.*;
import java.util.Objects;

public class DatabaseUtil {

    public static Connection connect(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3308/thelibrary?allowPublicKeyRetrieval=true&useSSL=false";
            return DriverManager.getConnection(url, "root", "pn?$a3sNt7ybQGjz4bbY");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String spLogin(String username, String password){
        try {
            Connection connection = connect();
            assert connection != null;
            CallableStatement login = connection.prepareCall("{call sp_Login(?,?,?)}");

            //passing username and password
            login.setString(1, username);
            login.setString(2, password);

            //registering output
            login.registerOutParameter(3, Types.VARCHAR);

            login.executeQuery();

            //get values from output
            String usertype = login.getString(3);

            return usertype;
        }catch (Exception exception){
            exception.printStackTrace();
        }

        return null;
    }













    public static void sp_deleteAudiobook(String audiobookid){
        try {
            Connection connection = connect();
            assert connection != null;
            CallableStatement deleteAudiobook = connection.prepareCall("{call sp_DeleteAudiobook(?)}");
            deleteAudiobook.setString(1, audiobookid);
            deleteAudiobook.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void sp_deleteEBook(String ebookid){
        try {
            Connection connection = connect();
            assert connection != null;
            CallableStatement deleteEBook = connection.prepareCall("{call sp_DeleteEBook(?)}");
            deleteEBook.setString(1, ebookid);
            deleteEBook.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void sp_deleteJournal(String journalid){
        try {
            Connection connection = connect();
            assert connection != null;
            CallableStatement deleteJournal = connection.prepareCall("{call sp_DeleteJournal(?)}");
            deleteJournal.setString(1, journalid);
            deleteJournal.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void sp_CreateIssue(String mediaid, String memberid, int period){
        try {
            Connection connection = connect();
            assert connection != null;
            CallableStatement createIssue = connection.prepareCall("{call sp_CreateIssue(?,?,?)}");
            createIssue.setString(1, mediaid);
            createIssue.setString(2, memberid);
            createIssue.setInt(3, period);
            createIssue.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void sp_IssueMedia(String issueid){
        try {
            Connection connection = connect();
            assert connection != null;
            CallableStatement issueMedia = Objects.requireNonNull(connect()).prepareCall("{call sp_IssueMedia(?)}");
            issueMedia.setString(1, issueid);
            issueMedia.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}


