package org.beni.gescartebanque.services.impl;

import ch.qos.logback.classic.Logger;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import javafx.scene.control.Alert;
import org.beni.gescartebanque.entities.Client;
import org.beni.gescartebanque.interfaces.Iclient;
import org.beni.gescartebanque.services.JpaUtils;
import org.beni.gescartebanque.services.UtilsFonction;
import org.hibernate.Hibernate;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

public class ClientService implements Iclient {
    static Logger logger = (Logger) LoggerFactory.getLogger(ClientService.class);

    @Override
    public Boolean CreateClient(String txt_addresse_client,String txt_email_client,
                                String txt_nom_client,String txt_prenom_client,
                                String txt_telephone_client,
                                String txt_username_client) {
        boolean isCreated = false;
        List<Client> verifClient = null;
        Client client = new Client();
        EntityManager entityManager = JpaUtils.getEm();
        String salt,hasPassword;


        try{
            verifClient = entityManager.createNamedQuery("Client.findByEmail",Client.class)
                    .setParameter("email",txt_email_client)
                    .getResultList();
           if(verifClient.isEmpty()){
               logger.info("nouvel email");

               verifClient = entityManager.createNamedQuery("Client.findByUsername",Client.class)
                       .setParameter("username",txt_username_client)
                       .getResultList();
               if(verifClient.isEmpty()){
                   logger.info("nouvel username");

                    salt = UtilsFonction.generateSalt();
                    client.setAdresse(txt_addresse_client);
                    client.setEmail(txt_email_client);
                    client.setNom(txt_nom_client);
                    client.setPrenom(txt_prenom_client);
                    client.setTelephone(txt_telephone_client);
                    client.setUsername(txt_username_client);
                    client.setDateInscription(new Date());
                    client.setSalt(salt);
                    client.setPassword(UtilsFonction.hashPassword("Passer12345",salt));
                    client.setTmpPassword(UtilsFonction.hashPassword("Passer12345",salt));
                    client.setPremiere_connexion(0);

                    try {
                        entityManager.getTransaction().begin();
                        entityManager.persist(client);
                        entityManager.getTransaction().commit();
                        UtilsFonction.sendUsernameAndPasswordByMail(client.getUsername(),"Passer12345",client.getEmail());
                        isCreated = true;
                    } catch (Exception e) {
                        logger.error("erreur lors de l'envoie de l'ajout du client {}",e.getMessage());
                    }

               }else {
                   logger.info("username existant");
                   Alert alert = new Alert(Alert.AlertType.ERROR);
                   alert.setTitle("Erreur");
                   alert.setHeaderText(null);
                   alert.setContentText("ce nom d'utilisateur est déjà pris");
                   alert.showAndWait();

               }

           }else {
               logger.info("mail exisant");
               Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setTitle("Erreur");
               alert.setHeaderText(null);
               alert.setContentText("addresse mail déjà répertoriée");
               alert.showAndWait();
           }
        } catch (Exception e) {
            logger.error("l'erreur {}, est survenue lors  de la creation du client ",e.getMessage());

        }
        return isCreated;
    }
}
