package pl.umk.mat.booking.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.umk.mat.booking.common.model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
