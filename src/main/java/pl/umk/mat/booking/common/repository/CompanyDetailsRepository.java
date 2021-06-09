package pl.umk.mat.booking.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.umk.mat.booking.common.model.CompanyDetails;

public interface CompanyDetailsRepository extends JpaRepository<CompanyDetails, Long> {
    CompanyDetails findById(long id);
}
