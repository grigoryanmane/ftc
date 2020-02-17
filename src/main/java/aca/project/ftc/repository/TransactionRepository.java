package aca.project.ftc.repository;

import aca.project.ftc.model.Project;
import aca.project.ftc.model.Transaction;
import aca.project.ftc.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    Optional<Transaction> findAllByUserAndProject(User userId, Project projectId);

    Optional<Transaction> findAllByUserIdAndProjectId(Long userId, Long projectId);

    Optional<Transaction> findAllByUserId(Long id);

    Optional<Transaction> findAllByProjectId(Long id);

    Optional<Transaction> findAllByProjectNameContaining(String name);

    Optional<Transaction> findAllByProjectDescription(String name);

    Optional<Transaction> findAllByProjectIdAndStatus(Long project_id, Transaction.Status status);


}


