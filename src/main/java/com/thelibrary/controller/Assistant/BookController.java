package com.thelibrary.controller.Assistant;

import com.thelibrary.database.BookDAOImpl;
import com.thelibrary.database.IssueDAOImpl;
import com.thelibrary.models.Book;
import com.thelibrary.models.Member;
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
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.controlsfx.control.Notifications;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class BookController{



    BookDAOImpl bookDAO = new BookDAOImpl();
    Book book = new Book();
    Member member = new Member();
    @FXML
    private AnchorPane mediaPane;
    @FXML
    private TextField nameTxt;
    @FXML
    private TextField authorTxt;
    @FXML
    private TextField publicationYearTxt;
    @FXML
    private TextField genreTxt;
    @FXML
    private TextField priceTxt;
    @FXML
    private TextField pageCountTxt;
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
    public Button backBTN;
    @FXML
    public Label bookDescription;
    @FXML
    public Label bookStatus;
    @FXML
    public Label bookPageCount;
    @FXML
    public Label bookYear;
    @FXML
    public Label bookGenre;
    @FXML
    public Label bookAuthor;
    @FXML
    public Label bookName;

    @FXML
    public ImageView bookCover;


    FileChooser filePicker = new FileChooser();
    public File bookcover;

    @FXML
    private VBox borrowVBox;
    @FXML
    private TextField periodTXT;
    @FXML
    private Button borrowBTN;


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
    protected void addBook(ActionEvent event){
        String name = nameTxt.getText();
        String author = authorTxt.getText();
        int publicationyear = Integer.parseInt(publicationYearTxt.getText());
        String genre = genreTxt.getText();
        double price = Double.parseDouble(priceTxt.getText());
        String description = descriptionTxtArea.getText();
        File cover = bookcover;
        int pagecount = Integer.parseInt(pageCountTxt.getText());
        String success = bookDAO.addBook(name, author, publicationyear, genre, price, description, cover, pagecount);
        Notifications.create().text(success).show();
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
    protected void goBackToBooks(ActionEvent event){
        try {
            Node node;
            node = (Node) FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/thelibrary/views/member/viewBooks.fxml")));
            mediaPane.getChildren().setAll(node);
        }catch (IOException ioException){
            ioException.printStackTrace();
        }
    }





    public void loadDetails(Book book){
        this.book = book;
        bookDescription.setText(book.getDescription());
        bookStatus.setText(book.getStatus());
        bookPageCount.setText(String.valueOf(book.getPagecount()) );
        bookYear.setText(String.valueOf(book.getPublicationyear()));
        bookGenre.setText(book.getGenre());
        bookAuthor.setText(book.getAuthor());
        bookName.setText(book.getName());
        bookCover.setImage(book.getCover());

    }
    @FXML
    protected void deleteBook(ActionEvent event){
        try {
            BookDAOImpl bookDAOImpl = new BookDAOImpl();
            bookDAOImpl.deleteBook(this.book);
            goBack(event);

        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    @FXML
    protected void borrowBook(ActionEvent event) {
        if (book.getStatus().equalsIgnoreCase("inhouse")) {
            borrowVBox.setVisible(true);
            borrowBTN.setVisible(false);
        }else {
            Notifications.create().text("This book is unavailable. Please select one that is available").show();
        }
    }

    @FXML
    protected void confirmBorrow(ActionEvent event) {
        IssueDAOImpl issueDAO = new IssueDAOImpl();
        issueDAO.createIssue(this.book.getMediaid(), member.getUsername(), Integer.parseInt(periodTXT.getText()));
        Notifications.create().text("Your reservation has been made. Please a librarian to complete the process").show();
    }

    @FXML
    protected void cancelBorrow(ActionEvent event) {
        borrowBTN.setVisible(true);
        borrowVBox.setVisible(false);
    }
}
