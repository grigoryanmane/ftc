package aca.project.ftc.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "products")
public class Product  extends Audit{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "product", orphanRemoval = true)
    private List<UserProduct> userProduct = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List getUserProduct() {
        return userProduct;
    }
    public void addUserProduct(UserProduct newUserProduct) {
        userProduct.add(newUserProduct);
        newUserProduct.setProduct(this);
    }

    public void setUserProduct(List<UserProduct> userProduct) {
        this.userProduct = userProduct;
    }

    public void removeUserProduct(UserProduct oldUserProduct) {
        userProduct.remove(oldUserProduct);
        oldUserProduct.setProduct(null);
    }
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", userProduct=" + userProduct +
                '}';
    }
}
