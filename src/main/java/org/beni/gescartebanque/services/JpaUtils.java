package org.beni.gescartebanque.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtils {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("GestionCarteBancairePU");
    public static final EntityManager em = emf.createEntityManager();


    public static EntityManager getEm() {
        if (em == null) {
            EntityManager em = emf.createEntityManager();
        }
        return em;
    }
    public static void closeEntityManagerFactory() {
        emf.close();
    }
}
