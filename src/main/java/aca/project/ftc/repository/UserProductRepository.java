package aca.project.ftc.repository;

import aca.project.ftc.model.UserProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserProductRepository extends CrudRepository<UserProduct, Long> {

    List<UserProduct> findByUserId(Long id);

    List<UserProduct> findByProductId(Long id);

    Optional<UserProduct> findByAmountIsGreaterThanEqual(Double amount);

    Optional<UserProduct> findByAmountIsLessThanEqual(Double amount);

    Optional<UserProduct> findByQuantityIsGreaterThan(Double quantity);

    Optional<UserProduct> findByQuantityIsLessThanEqual(Double quantity);


}
