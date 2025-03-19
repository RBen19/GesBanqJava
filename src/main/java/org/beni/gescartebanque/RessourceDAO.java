package org.beni.gescartebanque;

import org.beni.gescartebanque.entities.Utilisateur;
import org.beni.gescartebanque.interfaces.IUtilisateur;
import org.beni.gescartebanque.services.UtilisateurServices;

public class  RessourceDAO {

    public static IUtilisateur  UserDao() {

        return new UtilisateurServices();
    }
}
