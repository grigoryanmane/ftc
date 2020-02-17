package aca.project.ftc.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "user_product")
public class UserProduct extends Audit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private String description;

    @NotNull
    private Double amount = 0.0;



    @NotNull
    private Double quantity = 0.0;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "UserProduct{" +
                "id=" + id +
                ", user=" + user +
                ", product=" + product +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", quantity=" + quantity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserProduct)) return false;
        UserProduct that = (UserProduct) o;
        return getId().equals(that.getId()) &&
                getUser().equals(that.getUser()) &&
                getProduct().equals(that.getProduct()) &&
                getDescription().equals(that.getDescription()) &&
                getAmount().equals(that.getAmount()) &&
                getQuantity().equals(that.getQuantity());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUser(), getProduct(), getDescription(), getAmount(), getQuantity());
    }


}
