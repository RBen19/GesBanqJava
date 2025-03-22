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
@NamedQueries({
        @NamedQuery(name = "CarteBancaire.findByClientId",
                query = "SELECT c FROM CarteBancaire c WHERE c.client.idCLient = :idClient"),
        @NamedQuery(name = "CarteBancaire.findByNumeroCarte",
                query = "SELECT c FROM CarteBancaire c WHERE c.numeroCarte = :numeroCarte")
})
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
    @Column
    private String pin;
    @Column
    private String salt;
}
