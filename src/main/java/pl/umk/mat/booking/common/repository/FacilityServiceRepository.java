package pl.umk.mat.booking.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.umk.mat.booking.common.model.FacilityService;

public interface FacilityServiceRepository extends JpaRepository<FacilityService, Long> {
}
