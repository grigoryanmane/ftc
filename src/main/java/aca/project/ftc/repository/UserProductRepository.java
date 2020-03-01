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

    Page<UserProductModel> findAllByUserIdAndProductIdAndIsActiveOrderByUpdatedAtDesc(Long userId, Long productId, Boolean isActive, Pageable pageable);

    Page<UserProductModel> findAllByUserIdAndProductIdOrderByUpdatedAtDesc(Long userId, Long productId, Pageable pageable);

    Page<UserProductModel> findAllByUserIdAndIsActiveOrderByUpdatedAtDesc(Long userId, Boolean isActive, Pageable pageable);

    Page<UserProductModel> findAllByProductIdAndIsActiveOrderByUpdatedAtDesc(Long productId, Boolean isActive, Pageable pageable);


    @Override
    boolean existsById(Long aLong);

    List<UserProductModel> findByProductId(Long id);


    Optional<UserProductModel> findByAmountIsLessThanEqual(Double amount);

    Optional<UserProductModel> findByQuantityIsGreaterThan(Double quantity);

    Optional<UserProductModel> findByQuantityIsLessThanEqual(Double quantity);

    void deleteAllByUserId(Long id);
}
