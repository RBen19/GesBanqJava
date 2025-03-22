package org.beni.gescartebanque.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.beni.gescartebanque.HelloApplication;
import org.beni.gescartebanque.Ressource;
import org.beni.gescartebanque.RessourceDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class DashboardClientController implements Initializable {
   static Logger logger = LoggerFactory.getLogger(HelloApplication.class);


    public DashboardClientController() {
    }

    public DashboardClientController(Label lbl_nom_client) {
        this.lbl_nom_client = lbl_nom_client;
    }

    @FXML
    private AnchorPane ancor_parent;

    @FXML
    private Label lbl_nom_client;

    @FXML
    void btn_deconnexion(ActionEvent event) {

    }

    @FXML
    void btn_demande_carte(ActionEvent event) throws IOException {
        try
        {
            HelloApplication.change(new Stage(),"carteBancaire");
        } catch (Exception e) {
            logger.error("erreur lors du changement de fenetre entre dashboardclient et demander une carte {}",e.getMessage());

        }
    }

    @FXML
    void btn_historique(ActionEvent event) {

    }

    @FXML
    void btn_gerer_ma_carte(ActionEvent event) {
       try{
           HelloApplication.change(new Stage(),"gererMaCarte");
       } catch (Exception e) {
           logger.error("erreur lors du changement de screen entre dashboardclient et GererMaCarte {}",e.getMessage());
       }
    }

    @FXML
    void btn_retrait_argent(ActionEvent event) {

        try
        {
            HelloApplication.change(new Stage(),"retrait");
        } catch (Exception e) {
            logger.error("erreur lors du changement de fenetre entre dashboardclient et retrait argent{}",e.getMessage());

        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        if(!Ressource.nomClient.isEmpty()) {
//            lbl_nom_client.setText(Ressource.nomClient);
//        }else {
//            System.out.println("nulllllllll");
//        }
        lbl_nom_client.setText("Salut");
    }
}
