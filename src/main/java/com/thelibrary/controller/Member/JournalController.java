package com.thelibrary.controller.Member;

import com.thelibrary.database.JournalDAOImpl;
import com.thelibrary.models.Journal;
import com.thelibrary.models.Member;
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

public class JournalController {

    Member Member = new Member();
    @FXML
    public AnchorPane mediaPane;
    JournalDAOImpl journalDAO = new JournalDAOImpl();
    @FXML
    private TableView<Journal> journalTableView;
    ObservableList<Journal> journalObservableList = FXCollections.observableArrayList();
    @FXML
    private TableColumn<Journal, StringProperty> journalTitle;
    @FXML
    private TableColumn<Journal, StringProperty> journalAuthor;
    @FXML
    private TableColumn<Journal, StringProperty> areaOfStudy;
    @FXML
    private TableColumn<Journal, StringProperty> journalVolume;
    @FXML
    private TextField journalSearchTXT;

    public void initialize(){
        loadJournals();
        handleSelection();
    }

    public void setMember(Member member) {
        this.Member = member;
    }

    private void loadJournals(){
        try {
            journalObservableList.setAll(journalDAO.getAllJournals());
            journalTitle.setCellValueFactory(new PropertyValueFactory<>("name"));
            journalAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
            areaOfStudy.setCellValueFactory(new PropertyValueFactory<>("areaofstudy"));
            journalVolume.setCellValueFactory(new PropertyValueFactory<>("publicationyear"));


            journalTableView.setItems(journalObservableList);

            FilteredList<Journal> journalFilteredList = new FilteredList<>(journalObservableList, journal -> true);
            journalSearchTXT.textProperty().addListener((observable, oldValue, newValue) -> {
                journalFilteredList.setPredicate(journal -> {
                    if (newValue.isEmpty() || newValue.isBlank()) {
                        return true;
                    }
                    String journalSearchKeyword = newValue.toLowerCase();

                    if (journal.getName().toLowerCase().contains(journalSearchKeyword)) {
                        return true;
                    } else if (journal.getName().toLowerCase().contains(journalSearchKeyword)) {
                        return true;
                    } else if (journal.getAreaOfStudy().toLowerCase().contains(journalSearchKeyword)) {
                        return true;
                    } else return String.valueOf(journal.getPublicationyear()).contains(journalSearchKeyword);
                });
            });

            SortedList<Journal> journalSortedList = new SortedList<>(journalFilteredList);
            journalSortedList.comparatorProperty().bind(journalTableView.comparatorProperty());
            journalTableView.setItems(journalSortedList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleSelection(){
        journalTableView.setRowFactory( journalTableView  -> {
                    TableRow<Journal> journalTableRow = new TableRow<>();
                    journalTableRow.setOnMouseClicked(event1 ->{
                                if (event1.getClickCount() == 2 && (!journalTableRow.isEmpty())){
                                    Journal rowData = journalTableRow.getItem();
                                    try {
                                        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/com/thelibrary/views/member/journalDetails.fxml"));
                                        Node node = (Node) fxmlLoader.load();
                                        mediaPane.getChildren().setAll(node);

                                        com.thelibrary.controller.JournalController journalController = fxmlLoader.getController();
                                        journalController.setMember(this.Member);
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
}
