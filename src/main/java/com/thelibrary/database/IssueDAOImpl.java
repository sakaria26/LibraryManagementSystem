package com.thelibrary.database;

import com.thelibrary.models.Issue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.Objects;

import static com.thelibrary.util.DatabaseUtil.connect;

public class IssueDAOImpl implements IssueDAO{
   //Creating Issue
    @Override
    public  void createIssue(String mediaid, String memberid, int period){
        try (Connection connection = connect()){
            assert connection != null;
            CallableStatement createissue = connection.prepareCall("{call sp_CreateIssue(?,?,?)}");
            createissue.setString(1, mediaid);
            createissue.setString(2, memberid);
            createissue.setInt(3, period);
            createissue.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //Retrieve All Issues
    @Override
    public ObservableList<Issue> getAllIssues() {
        ObservableList<Issue> issueObservableList = FXCollections.observableArrayList();
        try (PreparedStatement queryBooks = Objects.requireNonNull(connect()).prepareStatement("select * from issue where status = 'pending' or status= 'issued'")){
            ResultSet resultSet = queryBooks.executeQuery();
            while (resultSet.next()) {
                Issue issue = new Issue();
                issue.setIssueID(resultSet.getString("issueid"));
                issue.setMemberID(resultSet.getString("memberid"));
                issue.setMediaID(resultSet.getString("mediaid"));
                issue.setIssueDate(resultSet.getDate("issuedate"));
                issue.setPeriod(resultSet.getInt("periodindays"));
                issue.setReturnDate(resultSet.getDate("returndate"));

                issueObservableList.add(issue);
            }
        }catch (SQLException sqlException){
            sqlException.getLocalizedMessage();
        }
        return issueObservableList;
    }

    @Override
    public Issue getIssue(String issueid) {
        Issue issue = new Issue();
        try(PreparedStatement retrieveIssue = Objects.requireNonNull(connect()).prepareStatement("select * from issue where issueid like "+issueid)){
            ResultSet resultSet = retrieveIssue.executeQuery();
            while (resultSet.next()){
                issue.setIssueID(resultSet.getString("issueid"));
                issue.setMemberID(resultSet.getString("memberid"));
                issue.setMediaID(resultSet.getString("mediaid"));
                issue.setIssueDate(resultSet.getDate("issuedate"));
                issue.setPeriod(resultSet.getInt("periodindays"));
                issue.setReturnDate(resultSet.getDate("returndate"));
            }
        }catch (SQLException sqlException){
            sqlException.getLocalizedMessage();
        }
        return issue;
    }

    @Override
    public void issueMedia(Issue issue) {
        try (CallableStatement issueMedia = Objects.requireNonNull(connect()).prepareCall("{call sp_IssueMedia(?)}")){
            issueMedia.setString(1, issue.getIssueID());
            issueMedia.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void payFine(String issueid) {
        try(CallableStatement payFine = Objects.requireNonNull(connect()).prepareCall("{call sp_PayFine(?)}")){
            payFine.setString(1, issueid);
            payFine.executeUpdate();
        }
        catch (ArithmeticException arithmeticException){
            arithmeticException.printStackTrace();
        }catch (SQLException sqlException){
            sqlException.getLocalizedMessage();
        }
    }

    @Override
    public int returnMedia(Issue issue) {
        int fine = 0;
        try (CallableStatement returnMedia = Objects.requireNonNull(connect()).prepareCall("{call sp_ReturnMedia(?,?)}")){
            returnMedia.setString(1, issue.getIssueID());
            returnMedia.registerOutParameter(2, Types.INTEGER);
            returnMedia.executeUpdate();
            fine = returnMedia.getInt(2);
        }catch (SQLException sqlException){
            sqlException.getLocalizedMessage();
        }
        return fine;
    }
}
