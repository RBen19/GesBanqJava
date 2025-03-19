package org.beni.gescartebanque.interfaces;

import org.beni.gescartebanque.entities.Utilisateur;

public interface IUtilisateur {

    boolean CreateUtilisateur(String password,String login);
    boolean UpdateUtilisateur(Utilisateur utilisateur);
    boolean DeleteUtilisateur(Utilisateur utilisateur);
    Utilisateur GetUtilisateur(String login,String password);

}
