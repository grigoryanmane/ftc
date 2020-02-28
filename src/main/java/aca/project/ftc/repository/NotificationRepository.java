package aca.project.ftc.repository;

import aca.project.ftc.model.entity.NotificationModel;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface NotificationRepository extends CrudRepository<NotificationModel, Long> {
    @Override
    Optional<NotificationModel> findById(Long aLong);

    @Override
    boolean existsById(Long aLong);

    void deleteAllByReceiverId(Long id);

    void deleteAllBySenderId(Long id);
}
