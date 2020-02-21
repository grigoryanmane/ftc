package aca.project.ftc.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user_product")
@Getter
@Setter
public class UserProductModel extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductModel product;


    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "userProduct", orphanRemoval = true)
    private List<NotificationModel> userProductNotification = new ArrayList<>();

    private String description;

    @NotNull
    private Double amount = 0.0;

    @NotNull
    private Double quantity = 0.0;


    public void addUserProductNotification(NotificationModel newNotification){
        userProductNotification.add(newNotification);
        newNotification.setUserProduct(this);
    }

    public void  removeUserProductNotification(NotificationModel oldNotification){
        userProductNotification.remove(oldNotification);
        oldNotification.setUserProduct(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserProductModel)) return false;
        UserProductModel that = (UserProductModel) o;
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
