package pl.umk.mat.booking.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.umk.mat.booking.common.model.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
