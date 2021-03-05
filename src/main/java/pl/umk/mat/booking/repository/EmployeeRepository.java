package pl.umk.mat.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.umk.mat.booking.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
