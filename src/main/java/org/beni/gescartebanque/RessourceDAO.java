package org.beni.gescartebanque;

import org.beni.gescartebanque.interfaces.*;
import org.beni.gescartebanque.services.impl.*;

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

    public static ITransaction TransactionDao() {
        return new TransactionService();
    }
    public static ILitige LitigDao() {
        return new LitigeService();
    }
}
