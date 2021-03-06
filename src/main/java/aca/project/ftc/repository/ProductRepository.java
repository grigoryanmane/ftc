package aca.project.ftc.repository;

import aca.project.ftc.model.entity.ProductModel;
import aca.project.ftc.model.entity.UserProductModel;
import org.springframework.data.repository.CrudRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends CrudRepository<ProductModel, Long> {
    Optional<ProductModel> findById(Long id);

    List<ProductModel> findAllByUserProduct(UserProductModel userProduct);

    Optional<ProductModel> findAllByNameContaining(String name);

}
