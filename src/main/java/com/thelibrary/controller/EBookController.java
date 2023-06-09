package com.thelibrary.controller;

import com.thelibrary.database.EBookDAOImpl;
import com.thelibrary.models.EBook;
import com.thelibrary.models.Librarian;
import com.thelibrary.models.Member;
import com.thelibrary.util.EmailUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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


public class EBookController {

    public Button selectDocumentBtn;
    public AnchorPane mediaPane;
    EBook eBook = new EBook();
    Member Member = new Member();
    Librarian Librarian = new Librarian();
    EBookDAOImpl eBookDAO = new EBookDAOImpl();
    @FXML
    private ImageView ebookCover;
    @FXML
    private Label ebookTitle;
    @FXML
    private Label ebookAuthor;
     @FXML
    private Label ebookDescription;
     @FXML
    private Label ebookStatus;
     @FXML
    private Label ebookFormat;
     @FXML
    private Label ebookPageCount;
     @FXML
    private Label ebookYear;
     @FXML
    private Label ebookGenre;
    @FXML
    private TextField nameTxt;
    @FXML
    private TextField authorTxt;
    @FXML
    private TextField publicationYearTxt;
    @FXML
    private TextField genreTxt;
    @FXML
    private TextField pageCountTxt;
    @FXML
    private TextField formatTxt;
    @FXML
    private TextArea descriptionTxtArea;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ImageView coverView;
    @FXML
    private Button saveBtn;
    @FXML
    private Button selectImageBtn;
    @FXML
    private Label documentTXT;
    FileChooser filePicker = new FileChooser();
    FileChooser documentPicker = new FileChooser();
    FileChooser fileSaver = new FileChooser();
    public File bookcover;
    public File document;



    public void loadDetails(EBook eBook){
        this.eBook = eBook;
        ebookCover.setImage(eBook.getCover());
        ebookTitle.setText(eBook.getName());
        ebookAuthor.setText(eBook.getAuthor());
        ebookDescription.setText(eBook.getDescription());
        ebookStatus.setText(eBook.getStatus());
        ebookFormat.setText(eBook.getFormat());
        ebookPageCount.setText(String.valueOf(eBook.getPagecount()));
        ebookYear.setText( String.valueOf(eBook.getPublicationyear()));
        ebookGenre.setText(eBook.getGenre());
    }
    @FXML
    protected void selectImage(ActionEvent event){
        filePicker.setInitialDirectory(new File("C:\\Users\\Public\\Pictures"));
        Window stage = anchorPane.getScene().getWindow();
        filePicker.setTitle("Select Image");
        filePicker.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Media cover", "*.png", "*.jpg"));
        bookcover = new File(String.valueOf(filePicker.showOpenDialog(stage)));
        Image image = new Image(String.valueOf(bookcover));
        coverView.setImage(image);
    }

    @FXML
    protected void addEbook(ActionEvent event){
        EBookDAOImpl eBookDAO = new EBookDAOImpl();
        String name = nameTxt.getText();
        String author = authorTxt.getText();
        int publicationyear = Integer.parseInt(publicationYearTxt.getText());
        String genre = genreTxt.getText();
        String description = descriptionTxtArea.getText();
        File cover = bookcover;
        int pageCount = Integer.parseInt(pageCountTxt.getText());
        String format = formatTxt.getText();
        String success = eBookDAO.sp_AddEbook(name, author, publicationyear, genre, description, cover, this.document, pageCount, format);
        Notifications.create().text(success).show();
    }

    public void deleteEBook(ActionEvent event) {
        try {
            EBookDAOImpl eBookDAO = new EBookDAOImpl();
            eBookDAO.deleteEbook(this.eBook);
            goBack(event);

        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    public void setMember(Member member) {
        this.Member = member;
    }

    public void downloadEbook(ActionEvent event) {
        System.out.println(this.Member.getEmail());
        EmailUtil emailUtil = new EmailUtil();
        emailUtil.setRecipientEmail(this.Member.getEmail());
        emailUtil.setRecipientName(this.Member.getName());
        emailUtil.setMediaName(this.eBook.getName());
        emailUtil.sendMediaEmail(this.eBook.getDocument());
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
    protected void goBackToMedia(ActionEvent event){
        try {
            Node node;
            node = (Node) FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/thelibrary/views/assistant/media.fxml")));
            mediaPane.getChildren().setAll(node);
        }catch (IOException ioException){
            ioException.printStackTrace();
        }
    }

    @FXML
    protected void goBackToEBooks(ActionEvent event){
        try {
            Node node;
            node = (Node) FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/thelibrary/views/member/viewEbooks.fxml")));
            mediaPane.getChildren().setAll(node);
        }catch (IOException ioException){
            ioException.printStackTrace();
        }
    }

    @FXML
    protected void selectDocument(ActionEvent event) {
        documentPicker.setInitialDirectory(new File("C:\\Users\\Public\\Documents"));
        Window stage = anchorPane.getScene().getWindow();
        documentPicker.setTitle("Select Document");
        documentPicker.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Media cover", "*.pdf", "*.*"));
        this.document = new File(String.valueOf(documentPicker.showOpenDialog(stage)));
        int lastSlash = String.valueOf(document).lastIndexOf('\\');
        documentTXT.setText(String.valueOf(document).substring(lastSlash+1));
        documentTXT.setVisible(true);
    }

}
