package org.beni.gescartebanque.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.beni.gescartebanque.Ressource;
import org.beni.gescartebanque.RessourceDAO;
import org.beni.gescartebanque.services.UtilsFonction;

public class PaiementController {
    @FXML
    private TextField txt_code_marchand;

    @FXML
    private TextField txt_code_otp;

    @FXML
    private TextField txt_code_pin;

    @FXML
    private TextField txt_montant;

    @FXML
    void valider_paiement(ActionEvent event) {
        if(txt_code_pin.getText().isEmpty() ||txt_code_marchand.getText().isEmpty() || txt_code_pin.getText().isEmpty()){
            UtilsFonction.messageError("veuillez remplir tous les champs","erreur");
            return;
        }
        double montant_retirer = Double.parseDouble(txt_montant.getText());
        RessourceDAO.CarteBancaireDao().paiement(Ressource.idClient,txt_code_pin.getText(),montant_retirer,txt_code_marchand.getText());
    }
    /*
    *
    * if(txt_code_otp.getText().isEmpty()){
            UtilsFonction.messageError("le code de otp est vide","erreur");
            return;
        }
        RessourceDAO.CarteBancaireDao().confirmerPaiement(Ressource.idClient,txt_code_otp.getText());
    *
    * */

    @FXML
    void confimerOTP(ActionEvent event) {

         if(txt_code_otp.getText().isEmpty()){
            UtilsFonction.messageError("le code de otp est vide","erreur");
            return;
        }
        RessourceDAO.CarteBancaireDao().confirmerPaiement(Ressource.idClient,txt_code_otp.getText());

    }

}
