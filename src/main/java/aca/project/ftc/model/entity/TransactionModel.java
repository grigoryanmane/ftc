package aca.project.ftc.model.entity;

import aca.project.ftc.model.constants.TransactionStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "transactions")
@Getter
@Setter
public class TransactionModel extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private ProjectModel project;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user;

    private Double amountSent;

    private String currencySent;

    private Double amountReceived;

    private String currencyReceived;

    private String dataSent;

    private String dataReceived;

    private TransactionStatus status = TransactionStatus.PENDING;

    private String callbackReceived;

    private Long paymentId;

    private String note;

    private String response;

}
