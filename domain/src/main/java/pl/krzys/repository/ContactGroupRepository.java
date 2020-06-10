package pl.krzys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.krzys.model.ContactGroup;

public interface ContactGroupRepository extends JpaRepository<ContactGroup, Long> {
}