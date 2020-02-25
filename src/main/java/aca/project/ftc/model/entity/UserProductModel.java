package aca.project.ftc.model.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "user_product")
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

    private Boolean isActive = true;

    public void addUserProductNotification(NotificationModel newNotification) {
        userProductNotification.add(newNotification);
        newNotification.setUserProduct(this);
    }

    public void removeUserProductNotification(NotificationModel oldNotification) {
        userProductNotification.remove(oldNotification);
        oldNotification.setUserProduct(null);
    }


}
