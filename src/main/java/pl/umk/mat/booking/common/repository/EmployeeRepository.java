package pl.umk.mat.booking.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.umk.mat.booking.common.model.Employee;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByEmail(String email);

    void deleteById(Long id);

    List<Employee> findAllByRoles_Role(String role);

    boolean existsByEmail(String email);
}
