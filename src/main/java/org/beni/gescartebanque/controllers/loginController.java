package org.beni.gescartebanque.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import org.beni.gescartebanque.HelloApplication;
import org.beni.gescartebanque.Ressource;
import org.beni.gescartebanque.RessourceDAO;
import org.beni.gescartebanque.entities.Client;
import org.beni.gescartebanque.interfaces.IUtilisateur;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class loginController {

    static Logger logger = LoggerFactory.getLogger(HelloApplication.class);

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
    void gotoRegister(ActionEvent event) {
        try {
            HelloApplication.change(new Stage(),"clientRegister");
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            logger.error("l'erreur {} est survenue lors du changement de fenetre de login vers register ",e.getLocalizedMessage());
        }
    }

    @FXML
    void btnValider(ActionEvent event) {
        Client client;
        if( (txt_password.getText().isBlank()|| txt_username.getText().isBlank()) || (txt_password.getLength()<8) || (txt_username.getLength()<3)) {
            System.out.println("Erreur de validation");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir tous les champs  un mot de passe  d'au moins 8 caractères  et un username d'au moins 3 caractères");
            alert.showAndWait();
            return;
        }else {
            if(txt_username.getText().startsWith("@ebk") && txt_username.getText().endsWith("@dk")) {
                // utilisateur gestionnaire admin etc...
//                Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                alert.setTitle("Erreur");
//                alert.setHeaderText(null);
//                alert.setContentText("admin");
//                alert.showAndWait();

                if(RessourceDAO.UserDao().GetUtilisateur(txt_username.getText(),txt_password.getText())!=null) {

                    try {
                        // dashbordAdmin
                        HelloApplication.change(new Stage(),"dashbordAdmin");
                        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        currentStage.close();  //

                    } catch (IOException e) {
                        logger.error("erreur lors du changement de fenetre de login vers dashbordAdmin {}",e.getMessage());
                    }
                }

            }else{

              client =   RessourceDAO.ClientDao().connexionClient(txt_username.getText(),txt_password.getText());
              if(client!=null) {
                  Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
//                  alert2.setTitle("Erreur");
//                  alert2.setHeaderText(null);
//                  alert2.setContentText("client");
//                  alert2.showAndWait();

                  try {
                      HelloApplication.change(new Stage(),"dashboardClient");
                      Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                      Ressource.nomClient = client.getUsername();
                      Ressource.idClient = client.getIdCLient();
                      System.out.println(Ressource.nomClient);
                      currentStage.close();
                  } catch (Exception e) {
                      logger.error("erreur lors du changement de fenetre de login vers dashbordClient {}",e.getMessage());
                  }
              }else{
                  logger.error("erreur lors de la connexion car le client avec le login "+txt_username+" n'a pas été trouvé");
                  Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                  alert1.setTitle("Erreur");
                  alert1.setHeaderText(null);
                  alert1.setContentText("erreur lors de la connexion");
                  alert1.showAndWait();


              }


            }


            System.out.println("helloooooooooooo");
        }
    }
}
