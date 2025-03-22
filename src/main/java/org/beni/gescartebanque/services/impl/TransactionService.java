package org.beni.gescartebanque.services.impl;

import jakarta.persistence.EntityManager;
import org.beni.gescartebanque.entities.CarteBancaire;
import org.beni.gescartebanque.entities.Client;
import org.beni.gescartebanque.entities.Transaction;
import org.beni.gescartebanque.interfaces.ITransaction;
import org.beni.gescartebanque.services.JpaUtils;

import java.util.ArrayList;
import java.util.List;

public class TransactionService implements ITransaction {
    @Override
    public List<Transaction> getAllTransaction() {
        EntityManager em = JpaUtils.getEm();
        return  em.createQuery("from Transaction", Transaction.class).getResultList();

    }

    @Override
    public List<Transaction> getTransationForClient(Long id) {
        EntityManager em = JpaUtils.getEm();
        List<Transaction> transactionList = new ArrayList<>();

        // recupérer le client
        Client c =  em.createQuery("select c From Client  c where c.idCLient=:idCLient",Client.class)
                .setParameter("idCLient",id)
                .getSingleResultOrNull();
        // la carte
        CarteBancaire carte = em.createNamedQuery("CarteBancaire.findByClientId", CarteBancaire.class)
                .setParameter("idClient",id)
                .getSingleResultOrNull();
        // transaction

        transactionList = em.createNamedQuery("Transaction.findByCarteBancaireId",Transaction.class)
                .setParameter("idCarteBancaire",carte.getIdCarteBancaire())
                .getResultList();



        return  transactionList;
    }
}
