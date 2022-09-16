package com.thelibrary.controller;

import com.thelibrary.database.MemberDAOImpl;
import com.thelibrary.database.LibrarianDAOImpl;
import com.thelibrary.models.Librarian;
import com.thelibrary.models.Member;
import com.thelibrary.util.EmailUtil;
import com.thelibrary.util.ViewUtil;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.util.Objects;

import static com.thelibrary.util.GeneralUtil.librarianusername;

public class ChiefController {
    Member Member = new Member();
    @FXML
    private AnchorPane mediaPane;

    Librarian Librarian = new Librarian();
    @FXML
    private Button addMemeberBtn;
    @FXML
    private Label headerLbl;
    @FXML
    private Label usernameLbl;

    @FXML
    private Label nameLbl;
    @FXML
    private Label surnameLbl;
    @FXML
    private Label emailLbl;
    @FXML
    private Label roleLbl;
    @FXML
    private TextField passwordTXT;
    @FXML
    private TextField usernameTXT;
    @FXML
    private TextField memberSearchTXT;
    @FXML
    private TableView<Member> memberTableView;
    ObservableList<Member> memberObservableList = FXCollections.observableArrayList();
    @FXML
    private TableColumn<Member, StringProperty> memberID;
    @FXML
    private TableColumn<Member, StringProperty> memberName;
    @FXML
    private TableColumn<Member, StringProperty> memberStatus;
    @FXML
    private TableColumn<Member, StringProperty> memberEmail;
    @FXML
    private TableColumn<Member, StringProperty> memberPhone;
    @FXML
    private TextField librarianNameTXT;
    @FXML
    private TextField librarianSurnameTXT;
    @FXML
    private TextField librarianEmailTXT;
    @FXML
    private TextField librarianPasswordTXT;
    @FXML
    private TextField librarianRoleTXT ;
    @FXML
    private TextField staffSearchTXT ;

    @FXML
    private TableView<Librarian> librarianTableView = new TableView<>();
    ObservableList<Librarian> librarianObservableList = FXCollections.observableArrayList();
    @FXML
    private TableColumn<Librarian, StringProperty> staffIDCOL;
    @FXML
    private TableColumn<Librarian, StringProperty> staffNameCOL;
    @FXML
    private TableColumn<Librarian, StringProperty> staffSurnameCOL;
    @FXML
    private TableColumn<Librarian, StringProperty> staffEmailCOL;
    @FXML
    private TableColumn<Librarian, StringProperty> staffRoleCOL;
    @FXML
    private AnchorPane sideBarPane;
    @FXML
    private Label testLBL;
    @FXML
    private AnchorPane choicePane;

    ViewUtil viewUtil= new ViewUtil();
    public void setLibrarian(Librarian librarian){
        this.Librarian = librarian;
    }

    public void setUsernameLbl(Librarian librarian) {
        this.Librarian = librarian;
        this.usernameLbl.setText(librarian.getUsername());
    }

    public void initialize(){
        loadStaff();
        loadMembers();
    }

    @FXML
    protected void loadStaffCRUD(ActionEvent event) throws IOException {
        Node node;
        node = (Node) FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/thelibrary/views/chief/staffCRUD.fxml")));
        choicePane.getChildren().setAll(node);
    }
    public void loadStaff(){
        try{
            LibrarianDAOImpl staffDAO = new LibrarianDAOImpl();
            staffDAO.getAllStaff();
            librarianObservableList.setAll(staffDAO.getAllStaff());

            staffIDCOL.setCellValueFactory(new PropertyValueFactory<>("Username"));
            staffNameCOL.setCellValueFactory(new PropertyValueFactory<>("Name"));
            staffSurnameCOL.setCellValueFactory(new PropertyValueFactory<>("Surname"));
            staffRoleCOL.setCellValueFactory(new PropertyValueFactory<>("Role"));
            staffEmailCOL.setCellValueFactory(new PropertyValueFactory<>("Email"));
            
            librarianTableView.setItems(librarianObservableList);

            FilteredList<Librarian> librarianFilteredList = new FilteredList<>(librarianObservableList, librarian -> true);
            staffSearchTXT.textProperty().addListener((observable, oldValue, newValue) -> {
                librarianFilteredList.setPredicate(librarian -> {
                    if (newValue.isEmpty() || newValue.isBlank()) {
                        return true;
                    }

                    String staffSearchKeyword = newValue.toLowerCase();

                    if (librarian.getUsername().toLowerCase().contains(staffSearchKeyword)){
                        return true;
                    } else if (librarian.getName().toLowerCase().contains(staffSearchKeyword)){
                        return true;
                    } else if (librarian.getSurname().toLowerCase().contains(staffSearchKeyword)){
                        return true;
                    } else if (librarian.getEmail().toLowerCase().contains(staffSearchKeyword)){
                        return true;
                    } else return librarian.getRole().toLowerCase().contains(staffSearchKeyword);
                });
            });

            SortedList<Librarian> librarianSortedList = new SortedList<>(librarianFilteredList);
            librarianSortedList.comparatorProperty().bind(librarianTableView.comparatorProperty());
            librarianTableView.setItems(librarianSortedList);


        }catch (Exception exception){
            exception.printStackTrace();
        }
    }


    public void loadMembers(){
        try{
            MemberDAOImpl memberDAO = new MemberDAOImpl();
            memberDAO.getPendingMemberList();
            memberObservableList.setAll(memberDAO.getMemberList());

            memberID.setCellValueFactory(new PropertyValueFactory<>("Username"));
            memberName.setCellValueFactory(new PropertyValueFactory<>("Name"));
            memberStatus.setCellValueFactory(new PropertyValueFactory<>("membershipStatus"));
            memberEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
            memberPhone.setCellValueFactory(new PropertyValueFactory<>("PhoneNumber"));

            memberTableView.setItems(memberObservableList);

            FilteredList<Member> memberFilteredList = new FilteredList<>(memberObservableList, member -> true);
            memberSearchTXT.textProperty().addListener((observable, oldValue, newValue) -> {
                memberFilteredList.setPredicate(member -> {
                    if (newValue.isEmpty() || newValue.isBlank()) {
                        return true;
                    }

                    String memberSearchKeyword = newValue.toLowerCase();

                    if (member.getUsername().toLowerCase().contains(memberSearchKeyword)){
                        return true;
                    } else if (member.getName().toLowerCase().contains(memberSearchKeyword)){
                        return true;
                    } else if (member.getSurname().toLowerCase().contains(memberSearchKeyword)){
                        return true;
                    } else if (member.getEmail().toLowerCase().contains(memberSearchKeyword)){
                        return true;
                    } else return member.getPhoneNumber().toLowerCase().contains(memberSearchKeyword);
                });
            });

            SortedList<Member> memberSortedList = new SortedList<>(memberFilteredList);
            memberSortedList.comparatorProperty().bind(memberTableView.comparatorProperty());
            memberTableView.setItems(memberSortedList);



        }catch (Exception exception){
            exception.printStackTrace();
        }
    }
    public void closeBtnEvent(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }


    @FXML
    protected void addLibrarianEvent(ActionEvent event) throws IOException {
        Librarian librarian = new Librarian();
        librarian.setUsername(librarianusername(librarianNameTXT.getText(), librarianSurnameTXT.getText()));
        librarian.setName(librarianNameTXT.getText());
        librarian.setSurname(librarianSurnameTXT.getText());
        librarian.setEmail(librarianEmailTXT.getText());
        librarian.setPassword("password");
        librarian.setRole(librarianRoleTXT.getText());
        LibrarianDAOImpl staffDAO = new LibrarianDAOImpl();
        staffDAO.addStaff(librarian);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/thelibrary/views/chief/chief.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
        loadStaff();
    }

    @FXML
    protected void cancelAddLibrarianEvent(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/thelibrary/views/chief/chief.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
        loadStaff();
    }

    @FXML
    public void deleteLibrarianEvent(ActionEvent event) {
        LibrarianDAOImpl staffDAO = new LibrarianDAOImpl();
        Librarian librarian = librarianTableView.getSelectionModel().getSelectedItem();
        staffDAO.deleteStaff(librarian);
        loadStaff();
    }

    public void addMemberEvent(ActionEvent event) {
        Member member = memberTableView.getSelectionModel().getSelectedItem();
        MemberDAOImpl memberDAO = new MemberDAOImpl();
        memberDAO.addMember(member);
        loadMembers();
        EmailUtil emailUtil = new EmailUtil();
        emailUtil.setRecipientEmail(member.getEmail());
        emailUtil.setRecipientName(member.getName()+" "+member.getSurname());
        emailUtil.setRecipientUserID(member.getUsername());
        emailUtil.setRecipientPassword(member.getPassword());
        emailUtil.sendAccountCreationEmail();
    }

    public void deleteMemberEvent(ActionEvent event) {
        Member member = memberTableView.getSelectionModel().getSelectedItem();
        MemberDAOImpl memberDAO = new MemberDAOImpl();
        memberDAO.deleteMember(member);
        loadMembers();
        EmailUtil emailUtil = new EmailUtil();
        emailUtil.setRecipientEmail(member.getEmail());
        emailUtil.setRecipientName(member.getName()+" "+member.getSurname());
        emailUtil.sendAccountRejectionEmail();
    }

    @FXML
    protected void loadAccount(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/com/thelibrary/views/chief/account.fxml")));
        Node node = (Node)loader.load();
        mediaPane.getChildren().setAll(node);
        ChiefController chiefController = loader.getController();
        chiefController.nameLbl.setText("Name : "+ Librarian.getName());
        chiefController.surnameLbl.setText("Surname : "+ Librarian.getSurname());
        chiefController.emailLbl.setText("Email : "+Librarian.getEmail());
        chiefController.roleLbl.setText("Role : "+Librarian.getRole());
    }

    @FXML
    protected void updateLibrarian(ActionEvent event) throws IOException {
        LibrarianDAOImpl staffDAO = new LibrarianDAOImpl();
        staffDAO.updateLibrarian(usernameTXT.getText(), passwordTXT.getText());
        Notifications.create().text("Password Changed!").show();
        viewUtil.loadLogin(event);
    }
}
