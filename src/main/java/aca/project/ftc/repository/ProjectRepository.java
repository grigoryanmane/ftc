package aca.project.ftc.repository;

import aca.project.ftc.model.Project;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProjectRepository extends CrudRepository<Project, Long> {

    Optional<Project> findAllByNameContaining(String name);

    Optional<Project> findAllByDescriptionContaining(String description);

    Optional<Project> findAllByUserId(Long id);

}
