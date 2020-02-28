package aca.project.ftc.repository;

import aca.project.ftc.model.constants.NotificationStatus;
import aca.project.ftc.model.entity.NotificationModel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends CrudRepository<NotificationModel, Long> {
    @Override
    Optional<NotificationModel> findById(Long aLong);

    @Override
    boolean existsById(Long aLong);

    List<NotificationModel> findAllBySenderIdAndStatusIsOrStatusIsOrderByUpdatedAtDesc(Long id, NotificationStatus notificationStatusAccepted , NotificationStatus notificationStatusRejected);
    List<NotificationModel> findAllByReceiverIdAndStatusIsOrderByUpdatedAtDesc(Long id, NotificationStatus notificationStatusPending);

    void deleteAllByReceiverId(Long id);

    void deleteAllBySenderId(Long id);
}
