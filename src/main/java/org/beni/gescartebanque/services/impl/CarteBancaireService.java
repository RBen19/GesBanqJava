package org.beni.gescartebanque.services.impl;

import ch.qos.logback.classic.Logger;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import javafx.scene.control.Alert;
import org.beni.gescartebanque.entities.AuthTransaction;
import org.beni.gescartebanque.entities.CarteBancaire;
import org.beni.gescartebanque.entities.Client;
import org.beni.gescartebanque.entities.Transaction;
import org.beni.gescartebanque.interfaces.ICarteBancaire;
import org.beni.gescartebanque.services.JpaUtils;
import org.beni.gescartebanque.services.UtilsFonction;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CarteBancaireService implements ICarteBancaire {
    static Logger logger = (Logger) LoggerFactory.getLogger(ClientService.class);
    //TODO : AJout la demande de pin pour bloquer ou débloquer
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
            UtilsFonction.sendMessageBYMail("Votre carte a bien été créer votre numéro de carte est le "+cb.getNumeroCarte()+" et votre cvv est  : "+cb.getCvv()+" votre carte expire le "+cb.getDateExpiration()+" votre limite journalière est de "+cb.getLimite_journaliere(),c.getEmail());
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

    @Override
    public Boolean bloquerDebloquerCarteBancaire(Long idClient, String pin) {
        boolean isDone = false;
        String message;
        EntityManager entityManager = JpaUtils.getEm();

        Client c =  entityManager.find(Client.class, idClient);
        CarteBancaire cb;

       try
       {
           cb = entityManager.createNamedQuery("CarteBancaire.findByClientId",CarteBancaire.class)
                   .setParameter("idClient",idClient)
                   .getSingleResult();

           if(cb.getStatutCarte()==0){
               message = "débloquer";
            cb.setStatutCarte(1);
           }else {
               cb.setStatutCarte(0);
               message="bloquer";
           }

           EntityTransaction transaction = entityManager.getTransaction();
           transaction.begin();
           entityManager.merge(cb);
           entityManager.flush();
           transaction.commit();
           isDone = true;
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Carte bancaire");
           alert.setHeaderText(null);
           alert.setContentText("votre carte a bien été "+message);
           alert.showAndWait();

       } catch (Exception e) {
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Erreur");
           alert.setHeaderText(null);
           alert.setContentText("nous ne parvenons pas à trouvez votre carte bancaire");
           alert.showAndWait();
           logger.error("erreur lors de la recherche de la carte par rapport à l'id du client {}",e.getMessage());

       }
       return isDone;
    }

    @Override
    public Boolean retraitArgent(Long idClient, String pin, double montant) {
        boolean isDone = false;
        CarteBancaire cb;
        EntityManager entityManager = JpaUtils.getEm();
        Transaction transaction = new Transaction() ;
        AuthTransaction authTransaction = new AuthTransaction();
        String salt,codeTransaction,codeOTP;



        Date currentUtilDate = new Date();
        java.sql.Date currentSqlDate = new java.sql.Date(currentUtilDate.getTime());


        try{
            cb = entityManager.createNamedQuery("CarteBancaire.findByClientId",CarteBancaire.class)
                    .setParameter("idClient",idClient)
                    .getSingleResult();

            if(cb==null){
                UtilsFonction.messageError("nous n'avons pas pu trouvez votre carte","carte introuvable");
                return false;
            }
            if(cb.getStatutCarte()==0){
                UtilsFonction.messageError("vous ne pouvez pas faire de retrait ","carte bloquée");
                return isDone;
            }
            if(cb.getSoldeCarte()<montant){
                UtilsFonction.messageError("solde insuffisant","impossible de faire le retrait");
                return isDone;
            }

            if(!UtilsFonction.hashPassword(pin,cb.getSalt()).equals(cb.getPin())){
                UtilsFonction.messageError("le mot de passe que vous avez saisie est incorrect","mot de passe incorrect");
                return isDone;
            }

           try{
               entityManager.getTransaction().begin();

               transaction.setCarteBancaire(cb);
               transaction.setMontant(montant);
               transaction.setDate_transaction(currentSqlDate);
               transaction.setStatus("en attente de confirmation");
               transaction.setOperation("retrait");
               codeTransaction = UtilsFonction.generateSomeCode(10);
               transaction.setCodeTransaction(codeTransaction);

               entityManager.persist(transaction);
               entityManager.flush();
               
               List<Transaction> tr = entityManager.createNamedQuery("Transaction.findByCodeTransaction",Transaction.class)
                       .setParameter("codeTransaction",codeTransaction)
                       .getResultList();
               
               
               if(tr.isEmpty()){
                   logger.error("erreur lors de la recuperation de la transaction CartBancaireServiceRetrait");
                   UtilsFonction.messageError("impossible de trouver la transaction","erreur");
                   return isDone;
                   
               }else {
                   authTransaction.setTransaction(tr.get(0));
                   authTransaction.setStatut("en attente");
                   codeOTP = UtilsFonction.generateSomeCode(6);
                   authTransaction.setCode_OTP(codeOTP);
                   authTransaction.setDate_generarion(currentSqlDate);
                   entityManager.persist(authTransaction);
                   entityManager.flush();
                   Client c = entityManager.find(Client.class, idClient);


                   UtilsFonction.sendMessageBYMail("le code otp de la transaction "+codeOTP,c.getEmail());

                   entityManager.getTransaction().commit();


               }
           } catch (Exception e) {
               logger.error("erreur lors de la retrait {}",e.getMessage());
           }
            
            
            
            















        } catch (Exception e) {
            logger.error("erreur lors du retrait pour le client avec l'id "+idClient+" "+e.getMessage());
        }
        return isDone;
    }

}
