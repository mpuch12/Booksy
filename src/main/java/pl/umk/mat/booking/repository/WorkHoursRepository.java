package pl.umk.mat.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.umk.mat.booking.model.WorkHours;

public interface WorkHoursRepository extends JpaRepository<WorkHours, Long> {
}
