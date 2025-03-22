package org.beni.gescartebanque.interfaces;

import org.beni.gescartebanque.entities.CarteBancaire;

import java.io.IOException;

public interface ICarteBancaire {
    Boolean geberCarteBancaire(Long idClient,String pin);
    Boolean bloquerDebloquerCarteBancaire(Long idClient,String pin);
    Boolean retraitArgent(Long idClient,String pin,double montant);
}
