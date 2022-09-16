package com.thelibrary.controller.Assistant;

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

public class AssistantController {

    @FXML
    public static AnchorPane viewBookDetailsFull = new AnchorPane();
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
    private Label usernameLbl;
    Librarian Librarian = new Librarian();
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

    ViewUtil viewUtil= new ViewUtil();
    public void setLibrarian(Librarian librarian){
        this.Librarian = librarian;
    }
    @FXML
    public void setUsernameLbl(Librarian librarian){
        this.Librarian = librarian;
        usernameLbl.setText(librarian.getUsername());
    }

    @FXML
    protected void mediaBtnEvent(ActionEvent event) throws IOException {
        Node node;
        node = (Node) FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/thelibrary/views/assistant/media.fxml")));
        mediaPane.getChildren().setAll(node);
    }

    @FXML
    protected void closeBtnEvent(ActionEvent event) throws IOException {
        close(event);
    }

    @FXML
    protected void memberBtnEvent(ActionEvent event) throws IOException {
        Node node;
        node = (Node) FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/thelibrary/views/assistant/members.fxml")));
        mediaPane.getChildren().setAll(node);
    }

    @FXML
    protected void issueBtnEvent(ActionEvent event) throws IOException{
        Node node;
        node = (Node) FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/thelibrary/views/assistant/issues.fxml")));
        mediaPane.getChildren().setAll(node);
    }

    public void fineBtnEvent(ActionEvent event) throws IOException {
        Node node;
        node = (Node) FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/thelibrary/views/assistant/fines.fxml")));
        mediaPane.getChildren().setAll(node);
    }

    @FXML
    protected void loadAccount(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/com/thelibrary/views/assistant/account.fxml")));
        Node node = (Node)loader.load();
        mediaPane.getChildren().setAll(node);
        AssistantController assistantController = loader.getController();
        assistantController.nameLbl.setText("Name : "+ Librarian.getName());
        assistantController.surnameLbl.setText("Surname : "+ Librarian.getSurname());
        assistantController.emailLbl.setText("Email : "+Librarian.getEmail());
        assistantController.roleLbl.setText("Role : "+Librarian.getRole());
    }

    @FXML
    protected void updateLibrarian(ActionEvent event) throws IOException {
        LibrarianDAOImpl staffDAO = new LibrarianDAOImpl();
        staffDAO.updateLibrarian(usernameTXT.getText(), passwordTXT.getText());
        Notifications.create().text("Password Changed!").show();
        viewUtil.loadLogin(event);
    }
}