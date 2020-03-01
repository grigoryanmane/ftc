package aca.project.ftc.repository;

import aca.project.ftc.model.constants.NotificationStatus;
import aca.project.ftc.model.entity.NotificationModel;
import aca.project.ftc.model.entity.UserProductModel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends CrudRepository<NotificationModel, Long> {
    @Override
    Optional<NotificationModel> findById(Long aLong);

    @Override
    boolean existsById(Long id);
    Optional<NotificationModel> findAllByUserProductIdAndStatus(Long id, NotificationStatus accepted);

    List<NotificationModel> findAllBySenderIdAndIsActiveAndStatusIsOrStatusIsOrderByUpdatedAtDesc(Long id, Boolean isActive, NotificationStatus notificationStatusAccepted, NotificationStatus notificationStatusRejected);

    List<NotificationModel> findAllByReceiverIdAndIsActiveAndStatusIsOrderByUpdatedAtDesc(Long id, Boolean isActive, NotificationStatus notificationStatusPending);

    void deleteAllByReceiverId(Long id);

    void deleteAllBySenderId(Long id);
}
