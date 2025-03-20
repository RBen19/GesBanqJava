package org.beni.gescartebanque.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="tb_client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCLient;

    @Column(length = 100)
    private String nom;

    @Column(length = 100)
    private String prenom;

    @Column(length = 100)
    private String email;

    @Column(length = 30)
    private String telephone;

    @Column(length = 30)
    private String adresse;

    @Column
    private Date dateInscription;

    @Column(length = 200)
    private String Salt;

    @Column(length = 200)
    private String password;

    @Column
    private String username;
    @Column(length = 200)
    private String tmpPassword;
    @Column
    private int premiere_connexion;


}
