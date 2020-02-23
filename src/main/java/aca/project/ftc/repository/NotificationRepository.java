package aca.project.ftc.repository;

import aca.project.ftc.model.NotificationModel;
import aca.project.ftc.model.ProductModel;
import aca.project.ftc.model.UserProductModel;
import org.springframework.data.repository.CrudRepository;

public interface NotificationRepository extends CrudRepository<NotificationModel, Long> {

    void deleteAllByReceiverId(Long id);

    void deleteAllBySenderId(Long id);
}
