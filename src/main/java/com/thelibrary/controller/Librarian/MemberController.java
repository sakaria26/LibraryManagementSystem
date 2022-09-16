package com.thelibrary.controller.Librarian;

import com.thelibrary.database.MemberDAOImpl;
import com.thelibrary.models.Member;
import com.thelibrary.util.ViewUtil;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class MemberController {
    Member Member = new Member();
    @FXML
    private Button returnBtn;
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
    private TableColumn<Member, StringProperty> memberSurname;
    @FXML
    private TableColumn<Member, StringProperty> memberEmail;
    @FXML
    private TableColumn<Member, StringProperty> memberPhone;
    @FXML
    private TableColumn<Member, IntegerProperty> memberIssues;
    @FXML
    private AnchorPane mediaPane = new AnchorPane();

    ViewUtil viewUtil = new ViewUtil();

    public void initialize(){
        loadMembers();
    }
    public void loadMembers(){
        try{
            MemberDAOImpl memberDAO = new MemberDAOImpl();
            memberDAO.getPendingMemberList();
            memberObservableList.setAll(memberDAO.getMemberList());

            memberID.setCellValueFactory(new PropertyValueFactory<>("Username"));
            memberName.setCellValueFactory(new PropertyValueFactory<>("Name"));
            memberSurname.setCellValueFactory(new PropertyValueFactory<>("membershipStatus"));
            memberEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
            memberPhone.setCellValueFactory(new PropertyValueFactory<>("PhoneNumber"));
            memberIssues.setCellValueFactory(new PropertyValueFactory<>("numberOfBooksIssued"));

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


}
