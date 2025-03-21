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
@Table(name = "tb_carte_bancaire")
public class CarteBancaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idCarteBancaire;
    @Column(length = 16)
    private String numeroCarte;
    @Column
    private int statutCarte;
    @Column
    private double soldeCarte;
    @Column
    private String cvv;
    @Column
    private double limite_journaliere;
    @Column
    private Date dateExpiration;
    @ManyToOne
    @JoinColumn(name = "idCLient",foreignKey = @ForeignKey(name = "fk_carte_client"))
    private Client client;
    @Column
    private double cashback;
}
