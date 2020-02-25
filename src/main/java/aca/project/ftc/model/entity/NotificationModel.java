package aca.project.ftc.model.entity;


import aca.project.ftc.model.constants.NotificationStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;


@Entity
@Table(name = "notifications")
@Setter
@Getter
public class NotificationModel extends BaseModel {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private UserModel receiver;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private UserModel sender;

    @ManyToOne
    @MapsId("id")
    @JoinColumn(name = "user_product_id",  referencedColumnName = "id")
    private UserProductModel userProduct;

    @Size(max = 150)
    private String message;

    private NotificationStatus status = NotificationStatus.PENDING;


}
