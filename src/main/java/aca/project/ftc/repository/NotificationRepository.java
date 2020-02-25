package aca.project.ftc.repository;

import aca.project.ftc.model.entity.NotificationModel;
import org.springframework.data.repository.CrudRepository;

public interface NotificationRepository extends CrudRepository<NotificationModel, Long> {

    void deleteAllByReceiverId(Long id);

    void deleteAllBySenderId(Long id);
}
