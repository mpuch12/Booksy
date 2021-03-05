package pl.umk.mat.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.umk.mat.booking.model.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
