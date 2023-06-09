package com.thelibrary.controller.Member;

import com.thelibrary.database.EBookDAOImpl;
import com.thelibrary.models.EBook;
import com.thelibrary.models.Member;
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

public class EBookController {
    EBookDAOImpl eBookDAO = new EBookDAOImpl();
    Member Member = new Member();
    @FXML
    private TableColumn<EBook, StringProperty> eBookTitle;
    @FXML
    private TableColumn<EBook, StringProperty> eBookAuthor;
    @FXML
    private TableColumn<EBook, IntegerProperty> eBookPublicationYear;
    @FXML
    private TableColumn<EBook, StringProperty> eBookGenre;
    ObservableList<EBook> eBookObservableList = FXCollections.observableArrayList();
    @FXML
    private TableView<EBook> eBookTableView;
    @FXML
    private TextField eBookSearchTXT;
    @FXML
    private AnchorPane mediaPane;
    public void initialize(){
        loadEBooks();
        handleSelection();
    }

    public void setMember(Member member) {
        this.Member = member;
    }

    private void handleSelection(){
        eBookTableView.setRowFactory( eBookTableView  -> {
                    TableRow<EBook> eBookTableRow = new TableRow<>();
                    eBookTableRow.setOnMouseClicked(event1 ->{
                                if (event1.getClickCount() == 2 && (!eBookTableRow.isEmpty())){
                                    EBook rowData = eBookTableRow.getItem();
                                    try {
                                        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/com/thelibrary/views/member/ebookDetails.fxml"));
                                        Node node = (Node) fxmlLoader.load();
                                        mediaPane.getChildren().setAll(node);

                                        com.thelibrary.controller.EBookController ebookController = fxmlLoader.getController();
                                        ebookController.setMember(this.Member);
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
    }
    private void loadEBooks(){
        try {
            eBookObservableList.setAll(eBookDAO.getAllEbooks());
            eBookTitle.setCellValueFactory(new PropertyValueFactory<>("name"));
            eBookAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
            eBookPublicationYear.setCellValueFactory(new PropertyValueFactory<>("publicationyear"));
            eBookGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));

            eBookTableView.setItems(eBookObservableList);

            FilteredList<EBook> ebookFilteredList = new FilteredList<>(eBookObservableList, ebook -> true);
            eBookSearchTXT.textProperty().addListener((observable, oldValue, newValue) -> {
                ebookFilteredList.setPredicate(ebook -> {
                    if (newValue.isEmpty() || newValue.isBlank()) {
                        return true;
                    }

                    String ebookSearchKeyword = newValue.toLowerCase();

                    if (ebook.getName().toLowerCase().contains(ebookSearchKeyword)) {
                        return true;
                    } else if (ebook.getAuthor().toLowerCase().contains(ebookSearchKeyword)) {
                        return true;
                    } else if (String.valueOf(ebook.getPublicationyear()).contains(ebookSearchKeyword)) {
                        return true;
                    } else return ebook.getGenre().contains(ebookSearchKeyword);
                });
            });

            SortedList<EBook> eBookSortedList = new SortedList<>(ebookFilteredList);
            eBookSortedList.comparatorProperty().bind(eBookTableView.comparatorProperty());
            eBookTableView.setItems(eBookSortedList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
