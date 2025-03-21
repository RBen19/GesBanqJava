package org.beni.gescartebanque.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

import org.beni.gescartebanque.HelloApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class DashbordAdminController {
    Logger logger = LoggerFactory.getLogger(DashbordAdminController.class);

    @FXML
   void btn_deconnexion(ActionEvent event) {
        try {
            //HelloApplication.change(new Stage(),"dashbordAdmin");
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
            HelloApplication.change(new Stage(),"login");
        } catch (IOException e) {
           logger.error("erreur du changement de fenetre lors de la connexion {}",e.getMessage());
        }
    }
}
