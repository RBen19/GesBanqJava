package org.beni.gescartebanque.interfaces;

import org.beni.gescartebanque.entities.CarteBancaire;

import java.io.IOException;

public interface ICarteBancaire {
    Boolean geberCarteBancaire(Long idClient,String pin);
}
