package aca.project.ftc.repository;

import aca.project.ftc.model.entity.UserProductModel;
import org.hibernate.annotations.OrderBy;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface UserProductRepository extends PagingAndSortingRepository<UserProductModel, Long> {

    List<UserProductModel> findByUserIdOrderByUpdatedAtDesc(Long id);
    List<UserProductModel> findAllByIsActiveOrderByUpdatedAtDesc(Boolean isActive);

    @Override
    boolean existsById(Long aLong);

    List<UserProductModel> findByProductId(Long id);

    Optional<UserProductModel> findAllByIsActive(Boolean isActive);

    Optional<UserProductModel> findByAmountIsLessThanEqual(Double amount);

    Optional<UserProductModel> findByQuantityIsGreaterThan(Double quantity);

    Optional<UserProductModel> findByQuantityIsLessThanEqual(Double quantity);

    void deleteAllByUserId(Long id);
}
