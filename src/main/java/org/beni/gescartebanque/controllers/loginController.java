package org.beni.gescartebanque.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.beni.gescartebanque.RessourceDAO;

public class loginController {
    @FXML
    private PasswordField txt_password;

    @FXML
    private TextField txt_username;

    @FXML
    void btn_connexion_clik(ActionEvent event) {


    }

    @FXML
    void btn_quitter_click(ActionEvent event) {

    }

    @FXML
    void btnValider(ActionEvent event) {
        if( (txt_password.getText().isBlank()|| txt_username.getText().isBlank()) || (txt_password.getLength()<8) || (txt_username.getLength()<3)) {
            System.out.println("Erreur de validation");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir tous les champs  un mot de passe  d'au moins 8 caractères  et un username d'au moins 3 caractères");
            alert.showAndWait();
            return;
        }else {
            if(RessourceDAO.UserDao().GetUtilisateur(txt_username.getText(),txt_password.getText())!=null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Bienvenue");
                alert.setHeaderText(null);
                alert.setContentText("Bienvenue "+txt_username.getText());
                alert.show();
            }
            System.out.println("helloooooooooooo");
        }
    }
}
