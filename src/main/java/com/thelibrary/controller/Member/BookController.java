package com.thelibrary.controller.Member;

import com.thelibrary.database.BookDAOImpl;
import com.thelibrary.models.Book;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class BookController {



    BookDAOImpl bookDAO = new BookDAOImpl();
    @FXML
    private TextField bookSearchTXT;
    @FXML
    private TableView<Book> bookTableView = new TableView<>();
    ObservableList<Book> bookObservableList = FXCollections.observableArrayList();
    @FXML
    private TableColumn<Book, StringProperty> bookTitleCOL;
    @FXML
    private TableColumn<Book, StringProperty> bookAuthorCOL;
    @FXML
    private TableColumn<Book, IntegerProperty> bookPublicationYearCOL;
    @FXML
    private TableColumn<Book, StringProperty> bookGenreCOL;
    @FXML
    private AnchorPane mediaPane;
    public void initialize(){
        loadBooks();
        handleSelection();
    }

    private void handleSelection(){
        bookTableView.setRowFactory( bookTableView1 -> {
                    TableRow<Book> bookTableRow = new TableRow<>();
                    bookTableRow.setOnMouseClicked(event1 ->{
                                if (event1.getClickCount() == 2 && (!bookTableRow.isEmpty())){
                                    Book rowData = bookTableRow.getItem();
                                    System.out.println(rowData);
                                    try {
                                        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/com/thelibrary/views/member/bookDetails.fxml"));
                                        Node node = (Node) fxmlLoader.load();
                                        mediaPane.getChildren().setAll(node);
                                        com.thelibrary.controller.BookController bookController = fxmlLoader.getController();
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

    }
    private void loadBooks(){
        try {
            bookObservableList.setAll(bookDAO.getAvailableBooks());
            bookTitleCOL.setCellValueFactory(new PropertyValueFactory<>("name"));
            bookAuthorCOL.setCellValueFactory(new PropertyValueFactory<>("author"));
            bookPublicationYearCOL.setCellValueFactory(new PropertyValueFactory<>("publicationyear"));
            bookGenreCOL.setCellValueFactory(new PropertyValueFactory<>("genre"));

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
}
