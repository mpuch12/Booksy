package pl.umk.mat.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.umk.mat.booking.model.CompanyDetails;

public interface CompanyDetailsRepository extends JpaRepository<CompanyDetails, Long> {
}
