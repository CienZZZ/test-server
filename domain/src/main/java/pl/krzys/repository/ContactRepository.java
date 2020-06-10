package pl.krzys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.krzys.model.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}