package org.beni.gescartebanque.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.beni.gescartebanque.Ressource;
import org.beni.gescartebanque.RessourceDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CarteBancaireCOntroller {
    Logger logger = LoggerFactory.getLogger(CarteBancaireCOntroller.class);

    @FXML
    private AnchorPane carte_ancor_pane;

    @FXML
    private TextField txt_code_pin;

    @FXML
    void btn_valider_demande_carte(ActionEvent event) {
        if(txt_code_pin.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("veuillez renseigner un code pin pour la carte");
            alert.showAndWait();
            return;
        }

       if(txt_code_pin.getText().equals("1234")){
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Erreur");
           alert.setHeaderText(null);
           alert.setContentText("votre code pin ne peut pas être 1234");
           alert.showAndWait();
           return;
       }
       Long idClient = Ressource.idClient;

       if(RessourceDAO.CarteBancaireDao().geberCarteBancaire(idClient, txt_code_pin.getText())){
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("info");
           alert.setHeaderText(null);
           alert.setContentText("votre carte a bien été créer");
           alert.showAndWait();
       }





    }

    @FXML
    void gotoDashboard(ActionEvent event) {

    }
}
