package org.beni.gescartebanque.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import org.beni.gescartebanque.Ressource;
import org.beni.gescartebanque.RessourceDAO;
import org.beni.gescartebanque.entities.Transaction;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TransacClientCOntroller implements Initializable {

    @FXML
    private ListView<Transaction> listTransaction;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTransaction();
    }

    public void loadTransaction(){
        List<Transaction> list = RessourceDAO.TransactionDao().getTransationForClient(Ressource.idClient);
        ObservableList<Transaction> observableList = FXCollections.observableList(list);
        listTransaction.setItems(observableList);
    }

}
