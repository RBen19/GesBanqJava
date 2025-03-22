package org.beni.gescartebanque.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_abonnement")
public class Abonnement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAbonnement;
    @Column
    private double prix;
    @Column()
    private int statut;
    @ManyToOne
    @JoinColumn(name = "idCarteBancaire",foreignKey = @ForeignKey(name = "fk_Abonnement_Carte"))
    private CarteBancaire carteBancaire;
}
