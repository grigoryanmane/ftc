package aca.project.ftc.repository;

import aca.project.ftc.model.UserProductModel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserProductRepository extends CrudRepository<UserProductModel, Long> {

    List<UserProductModel> findByUserId(Long id);

    List<UserProductModel> findByProductId(Long id);

    Optional<UserProductModel> findByAmountIsGreaterThanEqual(Double amount);

    Optional<UserProductModel> findByAmountIsLessThanEqual(Double amount);

    Optional<UserProductModel> findByQuantityIsGreaterThan(Double quantity);

    Optional<UserProductModel> findByQuantityIsLessThanEqual(Double quantity);

    void deleteAllByUserId(Long id);
}
