package aca.project.ftc.repository;

import aca.project.ftc.model.entity.UserProductModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface UserProductRepository extends JpaRepository<UserProductModel, Long> {

    Page<UserProductModel> findByUserIdOrderByUpdatedAtDesc(Long id, Pageable pageable);

    Page<UserProductModel> findAllByIsActiveOrderByUpdatedAtDesc(Boolean isActive, Pageable pageable);


    @Override
    boolean existsById(Long aLong);


    List<UserProductModel> findByProductId(Long id);

    Optional<UserProductModel> findAllByIsActive(Boolean isActive);

    Optional<UserProductModel> findByAmountIsLessThanEqual(Double amount);

    Optional<UserProductModel> findByQuantityIsGreaterThan(Double quantity);

    Optional<UserProductModel> findByQuantityIsLessThanEqual(Double quantity);

    void deleteAllByUserId(Long id);
}
