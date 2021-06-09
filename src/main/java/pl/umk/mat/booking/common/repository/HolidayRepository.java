package pl.umk.mat.booking.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.umk.mat.booking.common.model.Holiday;

public interface HolidayRepository extends JpaRepository<Holiday, Long> {
}
