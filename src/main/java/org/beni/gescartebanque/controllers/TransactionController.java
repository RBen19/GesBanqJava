package org.beni.gescartebanque.controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.beni.gescartebanque.RessourceDAO;
import org.beni.gescartebanque.entities.Transaction;

import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

public class TransactionController implements Initializable {

    @FXML
    private ListView<Transaction> listViewTRansaction;
    @FXML
    private TableView<Transaction> tblTransaction;

    @FXML
    private TableColumn<Transaction, String> beneficiareTrasaction;

    @FXML
    private TableColumn<Transaction, Date> dateTransaction;

    @FXML
    private TableColumn<Transaction, Double> montant_transaction;

    @FXML
    private TableColumn<Transaction, String> refTransaction;

    @FXML
    private TableColumn<Transaction, String> statut_transaction;

    @FXML
    private TableColumn<Transaction, String> typeTransaction;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       // beneficiareTrasaction.setCellValueFactory(new PropertyValueFactory<>("codeBeneficiare"));
//        dateTransaction.setCellValueFactory(new PropertyValueFactory<>("date_transaction"));
//        montant_transaction.setCellValueFactory(new PropertyValueFactory<>("montant"));
//        refTransaction.setCellValueFactory(new PropertyValueFactory<>("codeTransaction"));
//        statut_transaction.setCellValueFactory(new PropertyValueFactory<>("status"));
//        typeTransaction.setCellValueFactory(new PropertyValueFactory<>("operation"));
        loadTransaction();

    }
    public void loadTransaction(){
        List<Transaction> list = RessourceDAO.TransactionDao().getAllTransaction();
        ObservableList<Transaction> observableList = FXCollections.observableList(list);
        listViewTRansaction.setItems(observableList);
   }
}
