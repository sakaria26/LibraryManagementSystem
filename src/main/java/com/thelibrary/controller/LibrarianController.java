package com.thelibrary.controller;

import com.thelibrary.database.LibrarianDAOImpl;
import com.thelibrary.models.Librarian;
import com.thelibrary.util.ViewUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.util.Objects;

import static com.thelibrary.util.ViewUtil.close;

public class LibrarianController {
    Librarian Librarian = new Librarian();

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
    public static AnchorPane viewBookDetailsFull = new AnchorPane();
    @FXML
    private Label usernameLbl;
    @FXML
    private Label headerLbl;
    @FXML
    public AnchorPane mediaPane;
    @FXML
    private Button mediaBtn;

    @FXML
    private Button memberBtn;

    @FXML
    private Button issueBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private Button closeBtn;
    @FXML
    public TilePane tilePane;
    @FXML
    public VBox vBox;
    @FXML
    public ImageView imageView;
    @FXML
    public Label label;

    ViewUtil viewUtil = new ViewUtil();
    public void setLibrarian(Librarian librarian){
        this.Librarian = librarian;
    }

    public void setUsernameLbl(Librarian librarian) {
        this.Librarian = librarian;
        usernameLbl.setText(librarian.getUsername());
    }

    @FXML
    protected void mediaBtnEvent(ActionEvent event) throws IOException {
        Node node;
        node = (Node) FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/thelibrary/views/librarian/media.fxml")));
        mediaPane.getChildren().setAll(node);
    }

    @FXML
    protected void closeBtnEvent(ActionEvent event) throws IOException {
        close(event);
    }

    @FXML
    protected void memberBtnEvent(ActionEvent event) throws IOException {
        Node node;
        node = (Node) FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/thelibrary/views/librarian/members.fxml")));
        mediaPane.getChildren().setAll(node);
    }

    @FXML
    protected void issueBtnEvent(ActionEvent event) throws IOException{
        Node node;
        node = (Node) FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/thelibrary/views/librarian/issues.fxml")));
        mediaPane.getChildren().setAll(node);
    }

    @FXML
    protected void loadAccount(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/com/thelibrary/views/librarian/account.fxml")));
        Node node = (Node)loader.load();
        mediaPane.getChildren().setAll(node);
        LibrarianController librarianController = loader.getController();
        librarianController.nameLbl.setText("Name : "+ Librarian.getName());
        librarianController.surnameLbl.setText("Surname : "+ Librarian.getSurname());
        librarianController.emailLbl.setText("Email : "+Librarian.getEmail());
        librarianController.roleLbl.setText("Role : "+Librarian.getRole());
    }

    @FXML
    protected void updateLibrarian(ActionEvent event) throws IOException {
        LibrarianDAOImpl staffDAO = new LibrarianDAOImpl();
        staffDAO.updateLibrarian(usernameTXT.getText(), passwordTXT.getText());
        Notifications.create().text("Password Changed!").show();
        viewUtil.loadLogin(event);
    }
}