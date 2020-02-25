package aca.project.ftc.repository;

import org.springframework.data.repository.CrudRepository;
import aca.project.ftc.model.entity.UserModel;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserModel, Long> {
    Optional<UserModel> findById(Long id);

    Optional<UserModel> findByUsername(String username);

    Optional<UserModel> findAllProductById(Long id);


//    List<User> findByNotification(Long id);
//    List<Notification> findByIdOrderByNotification(Long id);

}
