package pl.umk.mat.booking.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.umk.mat.booking.model.Employee;
import pl.umk.mat.booking.model.Photo;
import pl.umk.mat.booking.model.UserRole;
import pl.umk.mat.booking.repository.EmployeeRepository;
import pl.umk.mat.booking.repository.UserRoleRepository;

import javax.validation.Validator;
import java.nio.file.*;
import java.util.List;
import java.util.Optional;

import static pl.umk.mat.booking.common.security.SecurityConfig.DEFAULT_ROLE;


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
        employee.setPhoto(saveEmployeePhoto(file));
        boolean isSavedWithPhoto = false;
        if (employee.getPhoto().getPath().equals("default.jpg"))
            isSavedWithPhoto = true;
        addWithDefaultRole(employee);
        return isSavedWithPhoto;
    }

    public boolean deleteEmployee(Long employeeId) {
        try {
            deleteEmployeePhoto(employeeId);
            employeeRepository.deleteById(employeeId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void deleteEmployeePhoto(Long employeeId) throws Exception {
        Employee employee = employeeRepository.findById(employeeId).get();
        String photoPath = employee.getPhoto().getPath();
        photoPath = photoPath.replace("%20", " ");
        if (!photoPath.equals("default.jpg")) {
            Path path = FileSystems.getDefault().getPath("./src/main/resources/static/employee/", photoPath);
            try {
                Files.delete(path);
            } catch (NoSuchFileException ignore) {
            } catch (Exception e) {
                throw new Exception();
            }
        }
    }

    public Employee getSpecifiedEmployee(Long id) {
        return employeeRepository.findById(id).get();
    }

    public boolean updateEmployee(Employee employee, String field, MultipartFile file) {
        Optional<Employee> byId = employeeRepository.findById(employee.getId());
        if (byId.isPresent()) {
            Employee employeeSaved = byId.get();
            switch (field) {
                case "name" -> employeeSaved.setName(employee.getName());
                case "email" -> employeeSaved.setEmail(employee.getEmail());
                case "photo" -> {
                    try {
                        deleteEmployeePhoto(employee.getId());
                    } catch (Exception ignore) {
                    }
                    employeeSaved.setPhoto(saveEmployeePhoto(file));
                }
            }
            try {
                employeeRepository.save(employeeSaved);
                return true;
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
    }

    private Photo saveEmployeePhoto(MultipartFile file) {
        final String type = "employee";
        Photo photo = new Photo("default.jpg", type);

        Optional<String> fileNameOptional = photoService.saveFileInDirectory(file, type);

        if (fileNameOptional.isPresent()) {
            String fileName = fileNameOptional.get();
            photo = new Photo(fileName, type);
        }

        return photo;
    }

    public void addWithDefaultRole(Employee employee) {
        try {
            validator.validate(employee);
        } catch (Exception e) {
            e.printStackTrace();
        }
        UserRole defaultRole = userRoleRepository.findByRole(DEFAULT_ROLE);
        employee.getRoles().add(defaultRole);
        String passwordHash = passwordEncoder.encode(employee.getPassword());
        employee.setPassword(passwordHash);
        employeeRepository.save(employee);
    }
}
