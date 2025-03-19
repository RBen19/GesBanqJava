package org.beni.gescartebanque.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "utilisateurs")
@NamedQueries({
        @NamedQuery(
                name = "Utilisateur.findByLogin",
                query = "SELECT u FROM Utilisateur u WHERE u.login = :login"
        )
})
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idAdmin;
    @Column(length = 50)
    private String login;
    @Column(length = 255)
    private String password;
    @Column(length = 255)
    private String SaltHash;
    @Column()
    private int Statut;

}
