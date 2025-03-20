package org.beni.gescartebanque.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.beni.gescartebanque.HelloApplication;

import java.io.IOException;

public class DashbordAdminController {
    static final Logger logger = LogManager.getLogger(DashbordAdminController.class);

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
