package pl.umk.mat.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.umk.mat.booking.model.Holiday;

public interface HolidayRepository extends JpaRepository<Holiday, Long> {
}
