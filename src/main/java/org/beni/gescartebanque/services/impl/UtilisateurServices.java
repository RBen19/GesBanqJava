package org.beni.gescartebanque.services.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import javafx.scene.control.Alert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.beni.gescartebanque.entities.Utilisateur;
import org.beni.gescartebanque.interfaces.IUtilisateur;
import org.beni.gescartebanque.services.JpaUtils;
import org.beni.gescartebanque.services.UtilsFonction;

import java.util.List;

public class UtilisateurServices implements IUtilisateur {
    static final Logger logger = LogManager.getLogger(IUtilisateur.class);


    @Override
    public boolean CreateUtilisateur(String password,String login) {

        EntityManager entityManger = JpaUtils.getEm();
        EntityTransaction et = entityManger.getTransaction();
        boolean  isCreated = false;
       List<Utilisateur> verifUser = null;
        String salt,hasedPassword;
       try{
           verifUser =entityManger.createNamedQuery("Utilisateur.findByLogin",Utilisateur.class)
                   .setParameter("login",login)
                   .getResultList();
       }catch (Exception e){
           logger.error("une erreur est survenue lors de la recupération de l'utilisateur{}",e.getMessage());
           e.printStackTrace();
       }

      if(!verifUser.isEmpty()) {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("Erreur");
          alert.setHeaderText(null);
          alert.setContentText("le nom d'utilisateur "+login+" existe déjà");
          alert.showAndWait();
          return isCreated;

      }else {
          Utilisateur utilisateur =  new Utilisateur();
          salt= UtilsFonction.generateSalt();
          utilisateur.setSaltHash(salt);
          utilisateur.setPassword(UtilsFonction.hashPassword(password,salt));
          utilisateur.setStatut(1);
          utilisateur.setLogin(login);

          try{
              et.begin();
              entityManger.persist(utilisateur);
              entityManger.flush();
              et.commit();
              isCreated = true;
              Alert alert = new Alert(Alert.AlertType.INFORMATION);
              alert.setTitle("Information");
              alert.setHeaderText(null);
              alert.setContentText("Utilisateur créer");
              alert.showAndWait();

          } catch (Exception e) {
              logger.error("une erreur est survenue lors de la creation de l'utilisateur {}",e.getMessage());
          }
      }
      return isCreated;
    }

    @Override
    public boolean UpdateUtilisateur(Utilisateur utilisateur) {
        return false;
    }

    @Override
    public boolean DeleteUtilisateur(Utilisateur utilisateur) {
        return false;
    }

    @Override
    public Utilisateur GetUtilisateur(String login,String password) {
        EntityManager entityManger = JpaUtils.getEm();
        EntityTransaction et = entityManger.getTransaction();
        Utilisateur utilisateur = null;
        String verifPassword = null;
        try
        {
            utilisateur = entityManger.createNamedQuery("Utilisateur.findByLogin",Utilisateur.class)
                    .setParameter("login",login)
                    .getSingleResult();

            if(utilisateur==null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("ce nom d'utilisateur n existe pas ");
                alert.showAndWait();
                return utilisateur;
            }else{
                verifPassword =   UtilsFonction.hashPassword(password,utilisateur.getSaltHash());
                if(verifPassword.equals(utilisateur.getPassword())) {
                    logger.info("utilisateur "+utilisateur.getLogin()+" vient de se connecter");

                }else {
                    utilisateur = null;
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText(null);
                    alert.setContentText("mot de passe incorrect");
                    alert.showAndWait();
                    return utilisateur;
                }
            }

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("ce nom d'utilisateur n existe pas ");
            alert.show();
            logger.error("erreur lors de la connexion {}",e.getMessage());
        }
        return  utilisateur;
    }
}
