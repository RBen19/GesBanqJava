package org.beni.gescartebanque;

import org.beni.gescartebanque.interfaces.IUtilisateur;
import org.beni.gescartebanque.services.impl.UtilisateurServices;

public class  RessourceDAO {

    public static IUtilisateur  UserDao() {

        return new UtilisateurServices();
    }
}
