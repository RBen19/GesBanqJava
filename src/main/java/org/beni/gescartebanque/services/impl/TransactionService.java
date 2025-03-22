package org.beni.gescartebanque.services.impl;

import jakarta.persistence.EntityManager;
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
}
