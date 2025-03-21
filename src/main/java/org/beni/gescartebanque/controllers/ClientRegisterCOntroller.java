package org.beni.gescartebanque.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.beni.gescartebanque.HelloApplication;
import org.beni.gescartebanque.RessourceDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientRegisterCOntroller {
    static Logger logger = LoggerFactory.getLogger(ClientRegisterCOntroller.class);
    @FXML
    private TextField txt_addresse_client;

    @FXML
    private TextField txt_email_client;

    @FXML
    private TextField txt_nom_client;

    @FXML
    private TextField txt_prenom_client;

    @FXML
    private TextField txt_telephone_client;

    @FXML
    private TextField txt_username_client;

    @FXML
    void ajouterClient(ActionEvent event) {
        logger.info("click click lick");

        if(txt_addresse_client.getText().isEmpty() || txt_email_client.getText().isEmpty() || txt_nom_client.getText().isEmpty() || txt_prenom_client.getText().isEmpty() || txt_telephone_client.getText().isEmpty() || txt_username_client.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs");
            alert.showAndWait();
            return;
        }else {
//            if(!txt_email_client.getText().endsWith("@gmail.com") || !txt_email_client.getText().endsWith("@groupeisi.com")) {
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("Erreur");
//                alert.setHeaderText(null);
//                alert.setContentText("Vous devez avoir une addresse email @gmail.com ou @groupeisi.com");
//                alert.showAndWait();
//                return;
//            }
            if ( RessourceDAO.ClientDao().CreateClient(txt_addresse_client.getText(),txt_email_client.getText(),
                    txt_nom_client.getText(),txt_prenom_client.getText(),
                    txt_telephone_client.getText(),
                    txt_username_client.getText())){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Bienvenue");
                alert.setHeaderText(null);
                alert.setContentText("votre inscription à réussit");
                alert.showAndWait();
            }else{

            }
        }




    }

    @FXML
    void viderChamp(ActionEvent event) {
        txt_addresse_client.setText("");
        txt_email_client.setText("");
        txt_nom_client.setText("");
        txt_prenom_client.setText("");
        txt_telephone_client.setText("");
        txt_username_client.setText("");

    }
}
