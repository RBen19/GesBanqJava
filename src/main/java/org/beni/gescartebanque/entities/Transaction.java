package org.beni.gescartebanque.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.text.SimpleDateFormat;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_transaction")
@NamedQueries({
        @NamedQuery(
                name = "Transaction.findByCarteBancaireId",
                query = "SELECT t FROM Transaction t WHERE t.carteBancaire.idCarteBancaire = :idCarteBancaire"
        ),
        @NamedQuery(
                name = "Transaction.findByCodeTransaction",
                query = "SELECT t FROM Transaction t WHERE t.codeTransaction = :codeTransaction"
        )
})
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idTransaction;
    @Column(length = 50)
    private String status;
    @Column
    private double montant;
    @Column(length = 50)
    private String operation;
    @Column
    private Date date_transaction;
    @ManyToOne
    @JoinColumn(name = "idCarteBancaire",foreignKey = @ForeignKey(name = "fk_transaction_carte"))
    private CarteBancaire carteBancaire;
    @Column(length = 200,unique = true)
    private String codeTransaction;
    @Column(length = 200)
    private String codeBeneficiare;

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // Format de date
        return "Transaction{" +
                "date_transaction=" + (date_transaction != null ? sdf.format(date_transaction) : "null") +
                ", status='" + status + '\'' +
                ", codeBeneficiare='" + codeBeneficiare + '\'' +
                ", operation='" + operation + '\'' +
                ", montant=" + montant +
                '}';
    }
}
