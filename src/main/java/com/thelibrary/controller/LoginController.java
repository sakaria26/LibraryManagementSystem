package com.thelibrary.controller;

import com.thelibrary.controller.Assistant.AssistantController;
import com.thelibrary.database.MemberDAOImpl;
import com.thelibrary.database.LibrarianDAOImpl;
import com.thelibrary.models.Librarian;
import com.thelibrary.models.Member;
import com.thelibrary.util.ViewUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static com.thelibrary.util.DatabaseUtil.spLogin;

public class LoginController {

    Member Member = new Member();
    @FXML
    private Label headerLbl;

    @FXML
    private TextField usernameTxt;
    @FXML
    private TextField passwordTxt;
    @FXML
    private Button cancelBtn;
    @FXML
    private Button loginBtn;

    //@FXML
    //private Label usernameLbl;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    protected void cancelEvent(ActionEvent event){
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

public ViewUtil viewUtil = new ViewUtil();
    @FXML
    protected void loginEvent(ActionEvent event) throws IOException {
        if (Objects.equals(spLogin(usernameTxt.getText(), passwordTxt.getText()), "member")){
            MemberDAOImpl memberDAO = new MemberDAOImpl();
            Member member = memberDAO.getMember(usernameTxt.getText(), passwordTxt.getText());
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/com/thelibrary/views/member/member.fxml")));
            Parent root = (Parent) loader.load();
            MemberController memberController = loader.getController();
            memberController.setUsernameLbl(member);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 900, 600);
            stage.setScene(scene);
            stage.show();

            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
        }else if (Objects.equals(spLogin(usernameTxt.getText(), passwordTxt.getText()), "librarian")){
            LibrarianDAOImpl staffDAO = new LibrarianDAOImpl();
            Librarian librarian = staffDAO.getStaff(usernameTxt.getText(), passwordTxt.getText());
            System.out.println(librarian.getUsername());
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/com/thelibrary/views/librarian/librarian.fxml")));
            Parent root = (Parent) loader.load();
            LibrarianController librarianController = loader.getController();
            librarianController.setUsernameLbl(librarian);


            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 900, 600);
            stage.setScene(scene);
            stage.show();
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
        }else if (Objects.equals(spLogin(usernameTxt.getText(), passwordTxt.getText()), "assistant")){
            LibrarianDAOImpl staffDAO = new LibrarianDAOImpl();
            Librarian librarian = staffDAO.getStaff(usernameTxt.getText(), passwordTxt.getText());

            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/com/thelibrary/views/assistant/assistant.fxml")));
            Parent root = (Parent) loader.load();
            AssistantController assistantController = loader.getController();
            assistantController.setUsernameLbl(librarian);

            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 900, 600);
            stage.setScene(scene);
            stage.show();

            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
        }else if (Objects.equals(spLogin(usernameTxt.getText(), passwordTxt.getText()), "chief")){
            LibrarianDAOImpl staffDAO = new LibrarianDAOImpl();
            Librarian librarian = staffDAO.getStaff(usernameTxt.getText(), passwordTxt.getText());

            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/com/thelibrary/views/chief/chief.fxml")));
            Parent root = (Parent) loader.load();
            ChiefController chiefController = loader.getController();
            chiefController.setUsernameLbl(librarian);

            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 900, 600);
            stage.setScene(scene);
            stage.show();
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Login Failed");
            alert.setHeaderText(null);
            alert.setContentText("Incorrect Details. Try Again");
            alert.showAndWait();}
    }

    public void registerEvent(ActionEvent event) throws IOException {
        viewUtil.loadSignUp(event);
    }
}
