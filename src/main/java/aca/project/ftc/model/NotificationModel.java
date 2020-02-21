package aca.project.ftc.model;


import aca.project.ftc.model.constants.NotificationStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Objects;


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
    @JoinColumns(@JoinColumn(name = "user_product_id",  referencedColumnName = "id"))
    private UserProductModel userProduct;

    @Size(max = 150)
    private String message;

    private Boolean active = true;

    private NotificationStatus status = NotificationStatus.PENDING;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NotificationModel)) return false;
        NotificationModel that = (NotificationModel) o;
        return getId().equals(that.getId()) &&
                getReceiver().equals(that.getReceiver()) &&
                getSender().equals(that.getSender()) &&
                getUserProduct().equals(that.getUserProduct()) &&
                Objects.equals(getMessage(), that.getMessage()) &&
                getActive().equals(that.getActive()) &&
                getStatus() == that.getStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getReceiver(), getSender(), getUserProduct(), getMessage(), getActive(), getStatus());
    }

}
