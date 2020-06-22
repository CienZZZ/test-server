package pl.krzys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.krzys.model.ContactGroup;

import java.util.Optional;

public interface ContactGroupRepository extends JpaRepository<ContactGroup, Long> {

    Optional<ContactGroup> findByName(String name);
}