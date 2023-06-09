package com.thelibrary.controller;

import com.thelibrary.database.IssueDAOImpl;
import com.thelibrary.models.Issue;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.sql.Date;

public class IssuesController {

    @FXML
    private TextField issueReturnID;
    @FXML
    private Label fineLbl;
    @FXML
    private TableView<Issue> issuesTBL = new TableView<>();
    @FXML
    private TableColumn<Issue, StringProperty> issueIDCOL;
    @FXML
    private TableColumn<Issue, StringProperty> memberIDCOL;
    @FXML
    private TableColumn<Issue, StringProperty> mediaIDCOL;
    @FXML
    private TableColumn<Issue, Date> issueDateCOL;
    @FXML
    private TableColumn<Issue, IntegerProperty> periodCOL;
    @FXML
    private TableColumn<Issue, Date> returnDateCOL;
    @FXML
    private TextField issueSearchTXT;
    @FXML
    private TextField issueIDConfirmTXT;
    ObservableList<Issue> issueObservableList = FXCollections.observableArrayList();

    public void initialize(){
        loadIssues();
    }

    public void loadIssues() {
        try {
            IssueDAOImpl issueDAO = new IssueDAOImpl();
            issueDAO.getAllIssues();
            issueObservableList.setAll(issueDAO.getAllIssues());
            issueIDCOL.setCellValueFactory(new PropertyValueFactory<>("IssueID"));
            memberIDCOL.setCellValueFactory(new PropertyValueFactory<>("MemberID"));
            mediaIDCOL.setCellValueFactory(new PropertyValueFactory<>("MediaID"));
            issueDateCOL.setCellValueFactory(new PropertyValueFactory<>("IssueDate"));
            periodCOL.setCellValueFactory(new PropertyValueFactory<>("Period"));
            returnDateCOL.setCellValueFactory(new PropertyValueFactory<>("ReturnDate"));
            issuesTBL.setItems(issueObservableList);

            FilteredList<Issue> issueFilteredList = new FilteredList<>(issueObservableList, issue -> true);
            issueSearchTXT.textProperty().addListener((observable, oldValue, newValue) -> {
                issueFilteredList.setPredicate(issue -> {
                    if (newValue.isEmpty() || newValue.isBlank()) {
                        return true;
                    }

                    String issueSearchKeyword = newValue.toLowerCase();

                    if (issue.getIssueID().toLowerCase().contains(issueSearchKeyword)){
                        return true;
                    } else if (issue.getMemberID().toLowerCase().contains(issueSearchKeyword)){
                        return true;
                    } else if (issue.getMediaID().toLowerCase().contains(issueSearchKeyword)){
                        return true;
                    } else if (String.valueOf(issue.getIssueDate()).toLowerCase().contains(issueSearchKeyword)){
                        return true;
                    } else if (String.valueOf(issue.getPeriod()).toLowerCase().contains(issueSearchKeyword)){
                        return true;
                    } else return String.valueOf(issue.getReturnDate()).toLowerCase().contains(issueSearchKeyword);
                });
            });

            SortedList<Issue> bookSortedList = new SortedList<>(issueFilteredList);
            bookSortedList.comparatorProperty().bind(issuesTBL.comparatorProperty());
            issuesTBL.setItems(bookSortedList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void returnBTNEVENT(ActionEvent event) throws IOException {
        try {
            Issue issue = issuesTBL.getSelectionModel().getSelectedItem();
            IssueDAOImpl issueDAO = new IssueDAOImpl();
            Notifications.create().text("Fine : "+String.valueOf(issueDAO.returnMedia(issue))).show();
        }catch (Exception exception){
            exception.printStackTrace();
        }



    }
    @FXML
    protected void closeReturn(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void confirmEvent(ActionEvent event) throws IOException {
        Issue issue = issuesTBL.getSelectionModel().getSelectedItem();
        IssueDAOImpl issueDAO = new IssueDAOImpl();
        issueDAO.issueMedia(issue);
        Notifications.create().text("Book has been issued").show();
    }

    @FXML
    protected void confirmIssue(ActionEvent event){
            Issue issue = new Issue();
            issue.setIssueID(issueIDConfirmTXT.getText());
            IssueDAOImpl issueDAO = new IssueDAOImpl();
            issueDAO.issueMedia(issue);
            issuesTBL.refresh();

    }

}
