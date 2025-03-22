package org.beni.gescartebanque.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.beni.gescartebanque.Ressource;
import org.beni.gescartebanque.RessourceDAO;
import org.beni.gescartebanque.services.UtilsFonction;

public class GererMaCarteController {
    @FXML
    private TextField txt_code_pin1;

    @FXML
    void bloquerDeloquerAction(ActionEvent event) {
        if(txt_code_pin1.getText().isEmpty()){
            UtilsFonction.messageError("veuillez donner votre code pin","champ obligatoire");
            return;
        }
        RessourceDAO.CarteBancaireDao().bloquerDebloquerCarteBancaire(Ressource.idClient, txt_code_pin1.getText());
    }
}
