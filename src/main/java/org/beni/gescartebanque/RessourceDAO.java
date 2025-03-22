package org.beni.gescartebanque;

import org.beni.gescartebanque.interfaces.ICarteBancaire;
import org.beni.gescartebanque.interfaces.IUtilisateur;
import org.beni.gescartebanque.interfaces.Iclient;
import org.beni.gescartebanque.services.impl.CarteBancaireService;
import org.beni.gescartebanque.services.impl.ClientService;
import org.beni.gescartebanque.services.impl.UtilisateurServices;

public class  RessourceDAO {

    public static IUtilisateur  UserDao() {

        return new UtilisateurServices();
    }
    public static Iclient ClientDao() {
        return new ClientService();
    }
    public  static  ICarteBancaire CarteBancaireDao() {
        return new CarteBancaireService();
    }
}
