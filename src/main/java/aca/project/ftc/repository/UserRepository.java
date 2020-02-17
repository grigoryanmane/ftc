package aca.project.ftc.repository;

import org.springframework.data.repository.CrudRepository;
import aca.project.ftc.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

//    List<User> findByNotification(Long id);
//    List<Notification> findByIdOrderByNotification(Long id);

}
