package pl.umk.mat.booking.company.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.umk.mat.booking.common.model.Employee;
import pl.umk.mat.booking.common.model.Photo;
import pl.umk.mat.booking.common.repository.EmployeeRepository;
import pl.umk.mat.booking.common.repository.UserRoleRepository;

import javax.validation.Validator;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.List;
import java.util.Set;

import static pl.umk.mat.booking.common.security.SecurityConfig.DEFAULT_ROLE;
import static pl.umk.mat.booking.common.validation.Validations.validate;
import static pl.umk.mat.booking.company.Utils.updateObject;


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

    public String addEmployee(Employee employee, MultipartFile file) {
        employee.setPhoto(saveEmployeePhoto(file));
        var message = validate(employee);
        if (message.isPresent()) {
            return message.get();
        }

        try {
            addWithDefaultRole(employee);
            return "Pracownik dodany pomyślnie";
        } catch (Exception exception) {
            return "Wystąpił nieoczekiwany błąd";
        }
    }

    public String deleteEmployee(Long employeeId) {
        try {
            deleteEmployeePhotoFromDirectory(employeeId);
            employeeRepository.deleteById(employeeId);
            return "Pracownik usunięty pomyślnie";
        } catch (Exception e) {
            return "Wystąpił błąd podczas usuwania";
        }
    }

    public Employee getSpecifiedEmployee(Long id) {
        return employeeRepository.findById(id).get();
    }

    public String updateEmployee(Employee employee, MultipartFile file) {
        try {
            var employeeSaved = employeeRepository.findById(employee.getId()).get();
            if (file != null) {
                deleteEmployeePhotoFromDirectory(employee.getId());
                var photo = saveEmployeePhoto(file);
                employee.setPhoto(photo);
            }
            updateObject(employee, employeeSaved);
            employeeRepository.save(employeeSaved);
            return "Zmiany zostały pomyslnie zaakceptowane";
        } catch (Exception exception) {
            return "Wystąpił błąd zapisu";
        }
    }

    private Photo saveEmployeePhoto(MultipartFile file) {
        final String type = "employee";
        var photo = new Photo("default.jpg", type);

        if (file.isEmpty()) {
            return photo;
        }

        var fileNameOptional = photoService.saveFileInDirectory(file, type);

        if (fileNameOptional.isPresent()) {
            String fileName = fileNameOptional.get();
            photo = new Photo(fileName, type);
        }

        return photo;
    }

    private void deleteEmployeePhotoFromDirectory(Long employeeId) {
        try {
            var employee = employeeRepository.findById(employeeId).get();
            var photoPath = employee.getPhoto().getPath();
            photoPath = photoPath.replace("%20", " ");
            if (!photoPath.equals("default.jpg")) {
                var path = FileSystems.getDefault().getPath("./src/main/resources/static/employee/", photoPath);
                Files.delete(path);
            }
        }catch (Exception ignore){}
    }

    private void addWithDefaultRole(Employee employee) {
        var defaultRole = userRoleRepository.findByRole(DEFAULT_ROLE);
        employee.setRoles(Set.of(defaultRole));
        var passwordHash = passwordEncoder.encode(employee.getPassword());
        employee.setPassword(passwordHash);
        employeeRepository.save(employee);
    }
}
