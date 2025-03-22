package org.beni.gescartebanque.services.impl;

import ch.qos.logback.classic.Logger;
import jakarta.persistence.EntityManager;
import org.beni.gescartebanque.entities.Client;
import org.beni.gescartebanque.interfaces.ILitige;
import org.beni.gescartebanque.services.JpaUtils;
import org.beni.gescartebanque.services.UtilsFonction;
import org.slf4j.LoggerFactory;

public class LitigeService implements ILitige {
    static Logger logger = (Logger) LoggerFactory.getLogger(ILitige.class);

    @Override
    public boolean sendLitige(String message, String codeTransaction,Long idClient) {
      boolean  isSend = false;
      String ToSend ;
        EntityManager em = JpaUtils.getEm();
      try{
          Client c = em.find(Client.class, idClient);
          ToSend = "le client avec l'email :  \n"+c.getEmail()+" signal le litige \n"+ message+"\n"+" pour la transaction \n"+codeTransaction;
         if( UtilsFonction.sendMessageBYMail(ToSend,"benirosinard19@gmail.com")){
             isSend = true;
             UtilsFonction.InfoMessage("plainte déposée","merci de votre retour");
         }
      } catch (Exception e) {
          logger.error("l'erreur {}",e.getMessage()+" est survenue lors de l'envoie du litige");
      }
      return isSend;
    }
}
