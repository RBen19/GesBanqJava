package org.beni.gescartebanque.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_authentification_transaction")
@NamedQueries({
        @NamedQuery(
                name = "AuthTransaction.findByTransactionId",
                query = "SELECT a FROM AuthTransaction a WHERE a.transaction.idTransaction = :idTransaction"
        ),
        @NamedQuery(
                name = "AuthTransaction.findByOTPCode",
                query = "SELECT a FROM AuthTransaction a WHERE a.code_OTP = :codeOTP"
        )
})
public class AuthTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idAuthTransaction;
    @Column(length = 50)
    private String Statut;
    @Column
    private Date date_generarion;
    @Column(length = 240)
    private String code_OTP;
    @ManyToOne
    @JoinColumn(name = "idTransaction",foreignKey = @ForeignKey(name = "fk_transaction_aithentifiactionTransaction"))
    private Transaction transaction;
}
