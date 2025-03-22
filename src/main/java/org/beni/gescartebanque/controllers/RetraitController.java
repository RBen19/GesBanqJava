package org.beni.gescartebanque.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.beni.gescartebanque.Ressource;
import org.beni.gescartebanque.RessourceDAO;
import org.beni.gescartebanque.services.UtilsFonction;

public class RetraitController {
    @FXML
    private Button btn_valider_retrait;

    @FXML
    private TextField txt_code_pin;

    @FXML
    private TextField txt_montant_retirer;

    @FXML
    void btn_valider_retrait_action(ActionEvent event) {
        double montant_retirer = Double.parseDouble(txt_montant_retirer.getText());
        RessourceDAO.CarteBancaireDao().retraitArgent(Ressource.idClient,txt_code_pin.getText(),montant_retirer);
    }

    @FXML
    private TextField txt_code_otp;

    @FXML
    void confirmer_avec_otp(ActionEvent event) {
        if(txt_code_otp.getText().isEmpty()){
            UtilsFonction.messageError("vous ne pouvez pas saisir un code OTP vide","impossible");
            return;
        }
        RessourceDAO.CarteBancaireDao().confirmerRetrait(Ressource.idClient,txt_code_otp.getText());
    }
}
