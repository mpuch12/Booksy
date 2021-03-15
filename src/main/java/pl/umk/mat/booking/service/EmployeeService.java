package pl.umk.mat.booking.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.umk.mat.booking.model.Employee;
import pl.umk.mat.booking.model.UserRole;
import pl.umk.mat.booking.repository.EmployeeRepository;
import pl.umk.mat.booking.repository.UserRoleRepository;

import javax.validation.Validator;


@Service
public class EmployeeService {
    private static final String DEFAULT_ROLE = "USER";
    private EmployeeRepository employeeRepository;
    private UserRoleRepository userRoleRepository;
    private PasswordEncoder passwordEncoder;
    private Validator validator;

    public EmployeeService(EmployeeRepository employeeRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder, Validator validator) {
        this.employeeRepository = employeeRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.validator = validator;
    }

    public void addWithDefaultRole(Employee employee){
        try {
            validator.validate(employee);
        }catch (Exception e){
            e.printStackTrace();
        }
        UserRole defaultRole = userRoleRepository.findByRole(DEFAULT_ROLE);
        employee.getRoles().add(defaultRole);
        String passwordHash = passwordEncoder.encode(employee.getPassword());
        employee.setPassword(passwordHash);
        employeeRepository.save(employee);
    }
}
