package org.beni.gescartebanque.interfaces;

import org.beni.gescartebanque.entities.Transaction;

import java.util.List;

public interface ITransaction {
    List<Transaction> getAllTransaction();
}
