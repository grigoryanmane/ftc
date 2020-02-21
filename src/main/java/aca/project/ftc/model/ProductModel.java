package aca.project.ftc.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
public class ProductModel extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "product", orphanRemoval = true)
    private List<UserProductModel> userProduct = new ArrayList<>();


    public void addUserProduct(UserProductModel newUserProduct) {
        userProduct.add(newUserProduct);
        newUserProduct.setProduct(this);
    }

    public void removeUserProduct(UserProductModel oldUserProduct) {
        userProduct.remove(oldUserProduct);
        oldUserProduct.setProduct(null);
    }

}
