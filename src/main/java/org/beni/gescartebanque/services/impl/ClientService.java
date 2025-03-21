package org.beni.gescartebanque.services.impl;

import ch.qos.logback.classic.Logger;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import javafx.scene.control.Alert;
import lombok.extern.slf4j.Slf4j;
import org.beni.gescartebanque.entities.Client;
import org.beni.gescartebanque.interfaces.Iclient;
import org.beni.gescartebanque.services.JpaUtils;
import org.beni.gescartebanque.services.UtilsFonction;
import org.hibernate.Hibernate;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

@Slf4j
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

    @Override
    public Client getClientByLogin(String txt_login) {
        EntityManager entityManager = JpaUtils.getEm();
        List<Client> verifClient = null;
        Client client =null;
       try{
           verifClient = entityManager.createNamedQuery("Client.findByUsername",Client.class)
                   .setParameter("username",txt_login)
                   .getResultList();
           if(verifClient.isEmpty()){
               logger.info("aucun client n'a été trouvé");
              Alert alert = new Alert(Alert.AlertType.ERROR);
              alert.setTitle("Erreur");
              alert.setHeaderText(null);
              alert.setContentText("utilisateur introuvable");
              alert.showAndWait();

               //client = null;
           }else {
               client = verifClient.get(0);
               logger.info("le client avec pour login ");
           }
       } catch (Exception e) {
           logger.error("erreur  {} lors de la récupération du client ligne 118 ClientService ",e.getMessage());

       }

        return  client;
    }

    @Override
    public Client connexionClient(String txt_login, String txt_password) {
        Client client = getClientByLogin(txt_login);
        String salt;
        if (client!=null) {

            if(client.getPremiere_connexion()==0){
                salt = client.getSalt();


                if(!UtilsFonction.hashPassword(txt_password,salt).equals(client.getTmpPassword())){
                  client = null;
                  logger.error("ceci est la première connexion et le mot de passe ne correspond pas à celui qui vous a été envoyé");
                 Alert alert = new Alert(Alert.AlertType.ERROR);
                 alert.setTitle("Erreur");
                 alert.setHeaderText(null);
                 alert.setContentText("le mot de passe saisie ne correspond pas au mot de passe qui vous a été envoyé");
                 alert.showAndWait();
                 return client;
                }else {
                    logger.info("le client avec le login  "+txt_login+" c'est connecté pour la première fois ");
                    client.setPremiere_connexion(1);
                    return client;

                }


            }else{


                salt = client.getSalt();
                if(!UtilsFonction.hashPassword(txt_password,salt).equals(client.getPassword())){
                    logger.error("le mot de passe saisie ne correspond pas ");
                    client = null;
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText(null);
                    alert.setContentText("le mot de passe incorrect");
                    alert.showAndWait();
                    return client;
                }else {

                  logger.info("le client est bien authentifié");
                  return  client;

                }

            }
        }else {
            logger.error("le client n'a pas été trouver lors de la connexion ");
            client = null;
        }
        return client;
    }
}
