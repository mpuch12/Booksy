package pl.umk.mat.booking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.umk.mat.booking.model.Employee;
import pl.umk.mat.booking.model.Photo;
import pl.umk.mat.booking.model.UserRole;
import pl.umk.mat.booking.repository.EmployeeRepository;
import pl.umk.mat.booking.repository.UserRoleRepository;

import javax.validation.Validator;
import java.util.List;
import java.util.Optional;

import static pl.umk.mat.booking.security.SecurityConfig.DEFAULT_ROLE;


@Service
public class EmployeeService {
    private EmployeeRepository employeeRepository;
    private UserRoleRepository userRoleRepository;
    private PasswordEncoder passwordEncoder;
    private Validator validator;
    private PhotoService photoService;

    public EmployeeService(EmployeeRepository employeeRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder, Validator validator, PhotoService photoService) {
        this.employeeRepository = employeeRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.validator = validator;
        this.photoService = photoService;
    }
    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }

    public boolean addEmployee(Employee employee, MultipartFile file) {
        final String type = "employee";
        boolean isSavedWithPhoto = false;
        Photo photo = new Photo("default.jpg", type);

        Optional<String> fileNameOptional = photoService.saveFileInDirectory(file, type);

        if (fileNameOptional.isPresent()) {
            String fileName = fileNameOptional.get();
            photo = new Photo(fileName, type);
            isSavedWithPhoto = true;
        }

        employee.setPhoto(photo);
        addWithDefaultRole(employee);

        return isSavedWithPhoto;
    }

    public boolean deleteEmployee(Long employeeId) {
        try {
            employeeRepository.deleteById(employeeId);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public Employee getSpecifiedEmployee(Long id) {
        return employeeRepository.findById(id).get();
    }

    public boolean updateEmployee(Employee employee, String field) {
        Optional<Employee> byId = employeeRepository.findById(employee.getId());
        if(byId.isPresent()) {
            Employee employeeSaved = byId.get();
            switch (field) {
                case "name" -> employeeSaved.setName(employee.getName());
                case "email" -> employeeSaved.setEmail(employee.getEmail());
            }
            try {
                employeeRepository.save(employeeSaved);
                return true;
            }catch (Exception e){
                return false;
            }
        }else{
            return false;
        }
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
