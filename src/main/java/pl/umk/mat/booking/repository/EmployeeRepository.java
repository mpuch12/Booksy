package pl.umk.mat.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.umk.mat.booking.model.Employee;
import pl.umk.mat.booking.model.UserRole;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByEmail(String email);

    void deleteById(Long id);

    List<Employee> findAllByRoles_Role(String role);

    boolean existsByEmail(String email);
}
