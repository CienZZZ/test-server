package pl.krzys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.krzys.model.CongMapping;

public interface CongMappingRepository extends JpaRepository<CongMapping, Long> {
}