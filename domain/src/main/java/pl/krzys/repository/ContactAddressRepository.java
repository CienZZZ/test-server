package pl.krzys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.krzys.model.ContactAddress;

public interface ContactAddressRepository extends JpaRepository<ContactAddress, Long> {
}