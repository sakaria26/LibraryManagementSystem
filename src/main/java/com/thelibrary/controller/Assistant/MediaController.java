package com.thelibrary.controller.Assistant;

import com.thelibrary.controller.BookController;
import com.thelibrary.controller.EBookController;
import com.thelibrary.controller.JournalController;
import com.thelibrary.database.BookDAOImpl;
import com.thelibrary.database.EBookDAOImpl;
import com.thelibrary.database.JournalDAOImpl;
import com.thelibrary.models.Book;
import com.thelibrary.models.EBook;
import com.thelibrary.models.Journal;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Objects;

public class MediaController  {

    JournalDAOImpl journalDAO = new JournalDAOImpl();
    @FXML
    private AnchorPane mediaPane;

    @FXML
    private TableView<EBook> ebookTableView;
    ObservableList<EBook> eBookObservableList = FXCollections.observableArrayList();
    @FXML
    private TableColumn<EBook, StringProperty> ebookIDCOL;
    @FXML
    private TableColumn<EBook, StringProperty> eBookTitleCOL;
    @FXML
    private TableColumn<EBook, StringProperty> eBookAuthorCOL;
    @FXML
    private TableColumn<EBook, IntegerProperty> ebookPublicationYearCOL;
    @FXML
    private TextField eBookSearchTXT;

    @FXML
    private TableView<Journal> journalsTableView;
    @FXML
    private TableColumn<Journal, StringProperty> journalIDCOL;
    @FXML
    private TableColumn<Journal, StringProperty>  journalTitleCOL;
    @FXML
    private TableColumn<Journal, StringProperty>  journalPublisherCOL;
    @FXML
    private TableColumn<Journal, IntegerProperty>  journalPublicationYearCOL;
    ObservableList<Journal> journalObservableList = FXCollections.observableArrayList();
    @FXML
    private TextField journalSearchTXT;
    @FXML
    private AnchorPane choicePane;
    @FXML
    private AnchorPane ebookchoicePane;
    @FXML
    private AnchorPane journalchoicePane;
    @FXML
    private Button addBtn;
    @FXML
    private Button addEBookBtn;
    @FXML
    private Button deleteEBookBtn;


    @FXML
    private Button addJournalBtn;
    @FXML
    private Button deleteJournalBtn;

    @FXML
    public ImageView bookCover;
    @FXML
    private TextField bookSearchTXT;
    @FXML
    public TableView<Book> bookTableView = new TableView<>();
    @FXML
    private TableColumn<Book, StringProperty> bookIDCOL;
    @FXML
    private TableColumn<Book, StringProperty> bookTitleCOL;
    @FXML
    private TableColumn<Book, StringProperty> bookAuthorCOL;
    @FXML
    private TableColumn<Book, IntegerProperty> bookPublicationYearCOL;
    ObservableList<Book> bookObservableList = FXCollections.observableArrayList();




    public void initialize(){
        loadBooks();
        loadEBooks();
        loadJournals();
        handleRowSelect();

    }

    @FXML
    protected void handleRowSelect() {
        bookTableView.setRowFactory( bookTableView1 -> {
            TableRow<Book> bookTableRow = new TableRow<>();
            bookTableRow.setOnMouseClicked(event1 ->{
                if (event1.getClickCount() == 2 && (!bookTableRow.isEmpty())){
                    Book rowData = bookTableRow.getItem();
                    System.out.println(rowData);
                    try {
                        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/com/thelibrary/views/assistant/bookDetails.fxml"));
                        Node node = (Node) fxmlLoader.load();
                        mediaPane.getChildren().setAll(node);

                        BookController bookController = fxmlLoader.getController();
                        bookController.loadDetails(rowData);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                    }
                    );
            return bookTableRow;
                }

        );

        ebookTableView.setRowFactory( eBookTableView  -> {
                    TableRow<EBook> eBookTableRow = new TableRow<>();
                    eBookTableRow.setOnMouseClicked(event1 ->{
                                if (event1.getClickCount() == 2 && (!eBookTableRow.isEmpty())){
                                    EBook rowData = eBookTableRow.getItem();
                                    try {
                                        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/com/thelibrary/views/assistant/ebookDetails.fxml"));
                                        Node node = (Node) fxmlLoader.load();
                                        mediaPane.getChildren().setAll(node);

                                        EBookController ebookController = fxmlLoader.getController();
                                        ebookController.loadDetails(rowData);

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                    );
                    return eBookTableRow;
                }

        );

        journalsTableView.setRowFactory( journalTableView  -> {
                    TableRow<Journal> journalTableRow = new TableRow<>();
                    journalTableRow.setOnMouseClicked(event1 ->{
                                if (event1.getClickCount() == 2 && (!journalTableRow.isEmpty())){
                                    Journal rowData = journalTableRow.getItem();
                                    try {
                                        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/com/thelibrary/views/assistant/journalDetails.fxml"));
                                        Node node = (Node) fxmlLoader.load();
                                        mediaPane.getChildren().setAll(node);

                                        JournalController journalController = fxmlLoader.getController();
                                        journalController.loadDetails(rowData);

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                    );
                    return journalTableRow;
                }

        );
    }


    private void loadEBooks() {

        try {
            EBookDAOImpl eBookDAO = new EBookDAOImpl();
            eBookObservableList.setAll(eBookDAO.getAllEbooks());
            ebookIDCOL.setCellValueFactory(new PropertyValueFactory<>("mediaid"));
            eBookTitleCOL.setCellValueFactory(new PropertyValueFactory<>("name"));
            eBookAuthorCOL.setCellValueFactory(new PropertyValueFactory<>("author"));
            ebookPublicationYearCOL.setCellValueFactory(new PropertyValueFactory<>("publicationyear"));

            ebookTableView.setItems(eBookObservableList);

            FilteredList<EBook> eBookFilteredList = new FilteredList<>(eBookObservableList, ebook -> true);
            eBookSearchTXT.textProperty().addListener((observable, oldValue, newValue) -> {
                eBookFilteredList.setPredicate(ebook -> {
                    if (newValue.isEmpty() || newValue.isBlank()) {
                        return true;
                    }

                    String ebookSearchKeyword = newValue.toLowerCase();

                    if (ebook.getMediaid().toLowerCase().contains(ebookSearchKeyword)) {
                        return true;
                    } else if (ebook.getName().toLowerCase().contains(ebookSearchKeyword)) {
                        return true;
                    } else if (ebook.getAuthor().toLowerCase().contains(ebookSearchKeyword)) {
                        return true;
                    } else return String.valueOf(ebook.getPublicationyear()).contains(ebookSearchKeyword);
                });
            });

            SortedList<EBook> eBookSortedList = new SortedList<>(eBookFilteredList);
            eBookSortedList.comparatorProperty().bind(ebookTableView.comparatorProperty());
            ebookTableView.setItems(eBookSortedList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void loadBooks() {

        try {
            BookDAOImpl bookDAO = new BookDAOImpl();
            bookObservableList.setAll(bookDAO.getAllBooks());
            bookIDCOL.setCellValueFactory(new PropertyValueFactory<>("mediaid"));
            bookTitleCOL.setCellValueFactory(new PropertyValueFactory<>("name"));
            bookAuthorCOL.setCellValueFactory(new PropertyValueFactory<>("author"));
            bookPublicationYearCOL.setCellValueFactory(new PropertyValueFactory<>("publicationyear"));

            bookTableView.setItems(bookObservableList);

            FilteredList<Book> bookFilteredList = new FilteredList<>(bookObservableList, book -> true);
            bookSearchTXT.textProperty().addListener((observable, oldValue, newValue) -> {
                bookFilteredList.setPredicate(book -> {
                    if (newValue.isEmpty() || newValue.isBlank()) {
                        return true;
                    }

                    String bookSearchKeyword = newValue.toLowerCase();

                    if (book.getMediaid().toLowerCase().contains(bookSearchKeyword)) {
                        return true;
                    } else if (book.getName().toLowerCase().contains(bookSearchKeyword)) {
                        return true;
                    } else if (book.getAuthor().toLowerCase().contains(bookSearchKeyword)) {
                        return true;
                    } else return String.valueOf(book.getPublicationyear()).contains(bookSearchKeyword);
                });
            });

            SortedList<Book> bookSortedList = new SortedList<>(bookFilteredList);
            bookSortedList.comparatorProperty().bind(bookTableView.comparatorProperty());
            bookTableView.setItems(bookSortedList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    private void loadJournals(){
        try {

            journalObservableList.setAll(journalDAO.getAllJournals());
            journalIDCOL.setCellValueFactory(new PropertyValueFactory<>("mediaid"));
            journalTitleCOL.setCellValueFactory(new PropertyValueFactory<>("name"));
            journalPublisherCOL.setCellValueFactory(new PropertyValueFactory<>("author"));
            journalPublicationYearCOL.setCellValueFactory(new PropertyValueFactory<>("publicationyear"));

            journalsTableView.setItems(journalObservableList);

            FilteredList<Journal> journalFilteredList = new FilteredList<>(journalObservableList, journal -> true);
            journalSearchTXT.textProperty().addListener((observable, oldValue, newValue) -> {
                journalFilteredList.setPredicate(journal -> {
                    if (newValue.isEmpty() || newValue.isBlank()) {
                        return true;
                    }

                    String journalSearchKeyword = newValue.toLowerCase();
                    if (journal.getMediaid().toLowerCase().contains(journalSearchKeyword)) {
                        return true;
                    } else if (journal.getName().toLowerCase().contains(journalSearchKeyword)) {
                        return true;
                    } else if (journal.getAuthor().toLowerCase().contains(journalSearchKeyword)) {
                        return true;
                    } else return String.valueOf(journal.getPublicationyear()).contains(journalSearchKeyword);
                });
            });

            SortedList<Journal> journalSortedList = new SortedList<>(journalFilteredList);
            journalSortedList.comparatorProperty().bind(journalsTableView.comparatorProperty());
            journalsTableView.setItems(journalSortedList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void addBtnEvent(ActionEvent event) throws IOException {
        Node node;
        node = (Node) (Node) FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/thelibrary/views/librarian/addBook.fxml")));
        choicePane.getChildren().setAll(node);
    }

    @FXML
    protected void addEBookBtnEvent(ActionEvent event) throws IOException {
        Node node;
        node = (Node) (Node) FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/thelibrary/views/librarian/addEbook.fxml")));
        ebookchoicePane.getChildren().setAll(node);
    }


    @FXML
    protected void addJournalBtnEvent(ActionEvent event) throws IOException {
        Node node;
        node = (Node) (Node) FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/thelibrary/views/librarian/addJournal.fxml")));
        journalchoicePane.getChildren().setAll(node);
    }


}
