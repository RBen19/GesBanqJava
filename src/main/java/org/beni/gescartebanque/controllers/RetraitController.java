package org.beni.gescartebanque.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.beni.gescartebanque.Ressource;
import org.beni.gescartebanque.RessourceDAO;

public class RetraitController {
    @FXML
    private Button btn_valider_retrait;

    @FXML
    private TextField txt_code_pin;

    @FXML
    private TextField txt_montant_retirer;

    @FXML
    void btn_valider_retrait_action(ActionEvent event) {

        RessourceDAO.CarteBancaireDao().retraitArgent(Ressource.idClient,txt_code_pin.getText(),1000);
    }
}
