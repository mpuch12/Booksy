package pl.umk.mat.booking.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.umk.mat.booking.common.model.WorkHours;

public interface WorkHoursRepository extends JpaRepository<WorkHours, Long> {
}
