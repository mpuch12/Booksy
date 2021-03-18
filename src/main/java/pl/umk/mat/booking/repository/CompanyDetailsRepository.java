package pl.umk.mat.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.umk.mat.booking.model.CompanyDetails;

public interface CompanyDetailsRepository extends JpaRepository<CompanyDetails, Long> {
    CompanyDetails findById(long id);
}
