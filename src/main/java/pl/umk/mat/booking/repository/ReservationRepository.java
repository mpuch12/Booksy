package pl.umk.mat.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.umk.mat.booking.model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
