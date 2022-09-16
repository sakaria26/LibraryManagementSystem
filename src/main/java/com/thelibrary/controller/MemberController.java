package com.thelibrary.controller;

import com.thelibrary.controller.Member.EBookController;
import com.thelibrary.controller.Member.JournalController;
import com.thelibrary.database.MemberDAOImpl;
import com.thelibrary.models.Issue;
import com.thelibrary.models.Member;
import com.thelibrary.util.ViewUtil;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.sql.Date;
import java.util.Objects;

public class MemberController {

    @FXML
    private Label nameLbl;
    @FXML
    private Label surnameLbl;
    @FXML
    private Label emailLbl;
    @FXML
    private Label noofIssuesLbl;
    @FXML
    private Label memberStatusLbl;
    @FXML
    private TextField passwordTXT;
    @FXML
    private TextField usernameTXT;
    @FXML
    private Label usernameLbl;
    Member Member = new Member();
    @FXML
    private TextField nameTXT;
    @FXML
    private TextField surnameTXT;
    @FXML
    private TextField emailTXT;
    @FXML
    private TextField confirmEmailTXT;
    @FXML
    private TextField phoneNumber;
    @FXML
    private Button returnBtn;
    @FXML
    private TableView<Issue> issuesTableView;
    ObservableList<Issue> issueObservableList = FXCollections.observableArrayList();
    @FXML
    private TableColumn<Issue, StringProperty> issueIDCOL;
    @FXML
    private TableColumn<Issue, StringProperty> mediaNameCOL;
    @FXML
    private TableColumn<Issue, IntegerProperty> periodCOL;
    @FXML
    private TableColumn<Issue, Date> returnDateCOL;
    @FXML
    private AnchorPane mediaPane = new AnchorPane();

    ViewUtil viewUtil = new ViewUtil();

    public void initialize(){
        loadIssues();
    }

    public void setUsernameLbl(Member member){
        this.Member = member;
        usernameLbl.setText(member.getUsername());
    }

    public void loadIssues(){
        try {
            MemberDAOImpl memberDAO = new MemberDAOImpl();
            Member member = this.Member;
            issueObservableList.setAll(memberDAO.getMemberIssues(member.getUsername()));
            System.out.println(member.getUsername());
            issueIDCOL.setCellValueFactory(new PropertyValueFactory<>("IssueID"));
            mediaNameCOL.setCellValueFactory(new PropertyValueFactory<>("MediaName"));
            periodCOL.setCellValueFactory(new PropertyValueFactory<>("Period"));
            returnDateCOL.setCellValueFactory(new PropertyValueFactory<>("ReturnDate"));
            issuesTableView.setItems(issueObservableList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void booksBtnEvent(ActionEvent event) throws IOException{
        Node node;
        node = (Node) FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/thelibrary/views/member/viewBooks.fxml")));
        mediaPane.getChildren().setAll(node);
    }

    @FXML
    protected void ebooksBtnEvent(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/com/thelibrary/views/member/viewEbooks.fxml")));
        Node node =  (Node) fxmlLoader.load();
        mediaPane.getChildren().setAll(node);

        EBookController eBookController = fxmlLoader.getController();
        eBookController.setMember(this.Member);
    }
    @FXML
    protected void journalsBtnEvent(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/com/thelibrary/views/member/viewJournals.fxml")));
        Node node = (Node) fxmlLoader.load();
        mediaPane.getChildren().setAll(node);

        JournalController journalController = fxmlLoader.getController();
        journalController.setMember(this.Member);
    }

    public void closeBtnEvent(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void issueBtnEvent(ActionEvent event) {
        mediaPane.getChildren().setAll(issuesTableView, returnBtn);
    }


    @FXML
    protected void signUpEvent(ActionEvent event) {
        MemberDAOImpl memberDAO = new MemberDAOImpl();
        memberDAO.signUp(nameTXT.getText(), surnameTXT.getText(), emailTXT.getText(), phoneNumber.getText());
        Notifications.create().text("Your request to sign up has been made. \nKeep an eye on your emails to know if you have been accepted or rejected").show();
    }

    public void goBackToLogin(ActionEvent event) throws IOException {
        viewUtil.loadLogin(event);
    }

    public void updateMember(ActionEvent event) throws IOException {
        MemberDAOImpl memberDAO = new MemberDAOImpl();
        memberDAO.sp_ChangeMemberPassword(usernameTXT.getText(), passwordTXT.getText());
        Notifications.create().text("Password Changed!").show();
        viewUtil.loadLogin(event);
    }

    @FXML
    protected void loadAccount(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/com/thelibrary/views/member/account.fxml")));
        Node node = (Node)loader.load();
        mediaPane.getChildren().setAll(node);
        MemberController memberController = loader.getController();
        memberController.nameLbl.setText("Name : "+ Member.getName());
        memberController.surnameLbl.setText("Surname : "+ Member.getSurname());
        memberController.emailLbl.setText("Email : "+Member.getEmail());
        memberController.noofIssuesLbl.setText("Number of Books Issued : "+String.valueOf(Member.getNumberOfBooksIssued()));
        memberController.memberStatusLbl.setText("Membership Status"+Member.getMembershipStatus());
    }


}
