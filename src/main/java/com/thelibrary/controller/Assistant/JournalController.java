package com.thelibrary.controller.Assistant;

import com.thelibrary.database.JournalDAOImpl;
import com.thelibrary.models.Journal;
import com.thelibrary.models.Member;
import com.thelibrary.util.EmailUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.controlsfx.control.Notifications;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class JournalController {
    Member Member = new Member();
    @FXML
    public AnchorPane mediaPane;
    @FXML
    private TextField volumeTXT;
    @FXML
    private TextField issueTXT;
    @FXML
    private Label documentLbl;
    Journal journal = new Journal();
    @FXML
    private Label titleLbl;
    @FXML
    private Label publisherLbl;
    @FXML
    private Label publicationYearlbl;
    @FXML
    private Label areaofstudyLbl;
    @FXML
    private Label volumeLbl;
    @FXML
    private Label issuesLbl;
    @FXML
    private Label citationsLbl;
    @FXML
    private Label descriptionLbl;
    JournalDAOImpl journalDAO = new JournalDAOImpl();
    @FXML
    private TextField nameTxt;
    @FXML
    private TextField authorTxt;
    @FXML
    private TextField publicationYearTxt;
    @FXML
    private TextField areaofstudyTxt;
    @FXML
    private TextField citationsTxt;
    @FXML
    private ImageView journalCover;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button saveBtn;
    FileChooser filePicker = new FileChooser();
    FileChooser imagePicker = new FileChooser();
    public File document;
    File cover;

    public void setMember(Member member) {
        this.Member = member;
    }

    @FXML
    protected void addJournal(ActionEvent event){
        JournalDAOImpl journalDAO = new JournalDAOImpl();
        String name = nameTxt.getText();
        String author = authorTxt.getText();
        int publicationyear = Integer.parseInt(publicationYearTxt.getText());
        String areaofstudy = areaofstudyTxt.getText();
        int citations = Integer.parseInt(citationsTxt.getText());
        int volume = Integer.parseInt(volumeTXT.getText());
        int issue = Integer.parseInt(issueTXT.getText());
        File document = this.document;
        File cover = this.cover;
        String success = journalDAO.sp_AddJournal(name, author, publicationyear, areaofstudy, citations, volume, issue, cover, document);
        Notifications.create().text(success).show();
    }

    public void loadDetails(Journal journal){
        this.journal = journal;
        titleLbl.setText("Title : "+journal.getName());
        publisherLbl.setText("Author : "+journal.getAuthor());
        publicationYearlbl.setText("Publication Year : "+String.valueOf(journal.getPublicationyear()));
        areaofstudyLbl.setText("Area of Study : "+journal.getAreaOfStudy());
        volumeLbl.setText("Volume : "+String.valueOf( journal.getVolume()));
        issuesLbl.setText( "Issue : "+String.valueOf(journal.getIssue()));
        citationsLbl.setText("Citations : "+String.valueOf(journal.getCitations()));
        journalCover.setImage(journal.getCover());
    }

    public void updateJournal(ActionEvent event) {
    }

    public void downloadJournal(ActionEvent event) {
        EmailUtil emailUtil = new EmailUtil();
        emailUtil.setRecipientEmail(this.Member.getEmail());
        emailUtil.setRecipientName(this.Member.getName());
        emailUtil.setMediaName(this.journal.getName());
        emailUtil.sendMediaEmail(this.journal.getDocument());
    }

    public void deleteJournal(ActionEvent event) {
        try {
            JournalDAOImpl journalDAO = new JournalDAOImpl();
            journalDAO.deleteJournal(this.journal);
            goBack(event);
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    @FXML
    protected void goBack(ActionEvent event){
        try {
            Node node;
            node = (Node) FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/thelibrary/views/librarian/media.fxml")));
            mediaPane.getChildren().setAll(node);
        }catch (IOException ioException){
            ioException.printStackTrace();
        }
    }

    @FXML
    protected void goBackToJournals(ActionEvent event){
        try {
            Node node;
            node = (Node) FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/thelibrary/views/member/viewJournals.fxml")));
            mediaPane.getChildren().setAll(node);
        }catch (IOException ioException){
            ioException.printStackTrace();
        }
    }

    @FXML
    public void selectDocument(ActionEvent event) {
        filePicker.setInitialDirectory(new File("C:\\Users\\Public\\Documents"));
        Window stage = anchorPane.getScene().getWindow();
        filePicker.setTitle("Select Document");
        filePicker.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Journal", "*.pdf"));
        this.document = new File(String.valueOf(filePicker.showOpenDialog(stage)));
        int lastSlash = String.valueOf(document).lastIndexOf('\\');
        documentLbl.setText(String.valueOf(document).substring(lastSlash+1));
        documentLbl.setVisible(true);
    }

    @FXML
    public void selectImage(ActionEvent event) {
        imagePicker.setInitialDirectory(new File("C:\\Users\\Public\\Pictures"));
        Window stage = anchorPane.getScene().getWindow();
        imagePicker.setTitle("Select Cover");
        imagePicker.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Media cover", "*.png", "*.jpg"));
        this.cover = new File(String.valueOf(imagePicker.showOpenDialog(stage)));
        Image image = new Image(String.valueOf(cover));
        journalCover.setImage(image);
    }
}
