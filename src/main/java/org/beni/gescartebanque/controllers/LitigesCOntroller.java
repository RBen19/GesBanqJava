package org.beni.gescartebanque.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.beni.gescartebanque.Ressource;
import org.beni.gescartebanque.RessourceDAO;
import org.beni.gescartebanque.services.UtilsFonction;

public class LitigesCOntroller {

    @FXML
    private TextField txt_codeTransaction;

    @FXML
    private TextArea txt_litige;



    @FXML
    void sendLitige(ActionEvent event) {
        if(txt_codeTransaction.getText().isBlank() || txt_litige.getText().isBlank()){
            UtilsFonction.messageError("vous devez saisir un motif de litige et le code de la transaction ","erreur");
            return;
        }
        RessourceDAO.LitigDao().sendLitige(txt_litige.getText(),txt_litige.getText(), Ressource.idClient);
    }




}
