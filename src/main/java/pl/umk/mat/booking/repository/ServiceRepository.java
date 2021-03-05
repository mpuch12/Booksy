package pl.umk.mat.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.umk.mat.booking.model.Service;

public interface ServiceRepository extends JpaRepository<Service, Long> {
}
