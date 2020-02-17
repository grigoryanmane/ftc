package aca.project.ftc.repository;

import aca.project.ftc.model.Product;
import aca.project.ftc.model.UserProduct;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, Long> {

    List<Product> findAllByUserProduct(UserProduct userProduct);

    Optional<Product> findAllByNameContaining(String name);

}
