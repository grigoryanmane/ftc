package aca.project.ftc.model;


import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "notifications")
public class Notification extends Audit {
    private enum Status {
        PENDING, ACCEPTED, REJECTED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;


    @ManyToOne
    @MapsId("id")
    @JoinColumns({@JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            @JoinColumn(name = "product_id",  referencedColumnName = "product_id"),
            @JoinColumn(name = "user_product_id",  referencedColumnName = "id")  })
    private UserProduct userProduct;

    @Size(max = 150)
    private String message;

    private Boolean active = true;

    private Status status = Status.PENDING;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public UserProduct getUserProduct() {
        return userProduct;
    }

    public void setUserProduct(UserProduct userProduct) {
        this.userProduct = userProduct;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Notification)) return false;
        Notification that = (Notification) o;
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

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", receiver=" + receiver +
                ", sender=" + sender +
                ", userProduct=" + userProduct +
                ", message='" + message + '\'' +
                ", active=" + active +
                ", status=" + status +
                '}';
    }
}
