package org.beni.gescartebanque.interfaces;

import org.beni.gescartebanque.entities.Client;

public interface Iclient {
    Boolean CreateClient(String txt_addresse_client,String txt_email_client,
                         String txt_nom_client,String txt_prenom_client,
                         String txt_telephone_client,
                         String txt_username_client);
    Client getClientByLogin(String txt_login);
    Client connexionClient(String txt_login, String txt_password);
}
