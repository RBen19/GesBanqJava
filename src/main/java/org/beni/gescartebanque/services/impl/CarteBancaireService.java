package org.beni.gescartebanque.services.impl;

import ch.qos.logback.classic.Logger;
import jakarta.persistence.EntityManager;
import javafx.scene.control.Alert;
import org.beni.gescartebanque.entities.CarteBancaire;
import org.beni.gescartebanque.entities.Client;
import org.beni.gescartebanque.interfaces.ICarteBancaire;
import org.beni.gescartebanque.services.JpaUtils;
import org.beni.gescartebanque.services.UtilsFonction;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class CarteBancaireService implements ICarteBancaire {
    static Logger logger = (Logger) LoggerFactory.getLogger(ClientService.class);
    @Override
    public Boolean geberCarteBancaire(Long idClient,String pin) {
        boolean isCreated = false;

        EntityManager entityManager = JpaUtils.getEm();
        Client c =  entityManager.find(Client.class, idClient);
        Date currentDate = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.YEAR, 1);
        Date oneYearLater = new Date(calendar.getTimeInMillis());
        String salt = UtilsFonction.generateSalt();

        CarteBancaire cb = new CarteBancaire();


        try{
            entityManager.getTransaction().begin();
            cb.setNumeroCarte(UtilsFonction.generateCardNumber("190411"));
            cb.setSoldeCarte(1000);
            cb.setStatutCarte(1);
            cb.setCashback(0);
            cb.setCvv(String.valueOf(UtilsFonction.generateCVV()));
            cb.setClient(c);
            cb.setPin(UtilsFonction.hashPassword(pin,salt));
            cb.setLimite_journaliere(500000);
            cb.setSalt(salt);
            cb.setDateExpiration(oneYearLater);
            entityManager.persist(cb);
            entityManager.flush();
            entityManager.getTransaction().commit();

            isCreated = true;
            logger.info("creation de la carte bancaire pour le client "+idClient+" a bien été creer ");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Carte bancaire");
            alert.setHeaderText(null);
            alert.setContentText("carte bien crée");
            alert.showAndWait();
        } catch (Exception e) {
            logger.error("erreur lors de la creation de la carte bancaire pour le client " + idClient+" "+e.getMessage());
        }


        return isCreated;
    }
}
