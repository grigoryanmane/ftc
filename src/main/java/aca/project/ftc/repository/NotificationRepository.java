package aca.project.ftc.repository;

import aca.project.ftc.model.constants.NotificationStatus;
import aca.project.ftc.model.entity.NotificationModel;
import aca.project.ftc.model.entity.UserProductModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends CrudRepository<NotificationModel, Long> {
    @Override
    Optional<NotificationModel> findById(Long aLong);

    @Override
    boolean existsById(Long id);

    Optional<NotificationModel> findByUserProductIdAndStatusIn(Long id, List<NotificationStatus> status);

    List<NotificationModel> findAllBySenderIdAndIsActiveAndStatusInOrderByUpdatedAtDesc(Long id, Boolean isActive,List<NotificationStatus> status);

    List<NotificationModel> findAllByReceiverIdAndIsActiveAndStatusIsOrderByUpdatedAtDesc(Long id, Boolean isActive, NotificationStatus notificationStatusPending);

    boolean existsByUserProductIdAndSenderIdAndReceiverId(Long userProductId, Long senderId, Long receiverId);

    void deleteAllByReceiverId(Long id);

    void deleteAllBySenderId(Long id);
}
