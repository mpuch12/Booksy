package pl.umk.mat.booking.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.umk.mat.booking.model.CompanyDetails;
import pl.umk.mat.booking.model.Employee;
import pl.umk.mat.booking.model.Photo;
import pl.umk.mat.booking.repository.CompanyDetailsRepository;
import pl.umk.mat.booking.repository.EmployeeRepository;
import pl.umk.mat.booking.repository.PhotoRepository;
import pl.umk.mat.booking.repository.ServiceRepository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private static final String UPLOAD_DIR = "src/main/resources/static/";

    private EmployeeRepository employeeRepository;
    private CompanyDetailsRepository companyDetailsRepository;
    private PhotoRepository photoRepository;
    private ServiceRepository serviceRepository;
    private EmployeeService employeeService;

    public AdminService(EmployeeRepository employeeRepository, CompanyDetailsRepository companyDetailsRepository, PhotoRepository photoRepository, ServiceRepository serviceRepository, EmployeeService employeeService) {
        this.employeeRepository = employeeRepository;
        this.companyDetailsRepository = companyDetailsRepository;
        this.photoRepository = photoRepository;
        this.serviceRepository = serviceRepository;
        this.employeeService = employeeService;
    }

    public CompanyDetails getCompanyDetails() {
        return companyDetailsRepository.findById(1);
    }

    public void updateCompanyDetails(CompanyDetails companyDetails, String field) {
        CompanyDetails savedCompanyDetails = companyDetailsRepository.findById(1);

        switch (field) {
            case "name" -> savedCompanyDetails.setName(companyDetails.getName());
            case "address" -> savedCompanyDetails.setAddress(companyDetails.getAddress());
        }

        companyDetailsRepository.save(savedCompanyDetails);

    }

    public boolean saveCompanyPhoto(MultipartFile file, String type) {

        Optional<String> fileNameOptional = saveFileInDirectory(file, type);

        if(fileNameOptional.isEmpty())
            return false;

        String fileName = fileNameOptional.get();

        CompanyDetails companyDetails = companyDetailsRepository.findById(1);
        Photo photo = new Photo(fileName,type/*, companyDetails*/);

        companyDetails.addPhoto(photo);
        companyDetailsRepository.save(companyDetails);
        return true;
    }

    private Optional<String> saveFileInDirectory(MultipartFile file, String type) {
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        String dir = UPLOAD_DIR;
        Optional<String> fileName = Optional.empty();

        switch (type){
            case "work" -> dir = dir + "work/";
            case "workshop" -> dir = dir + "workshop/";
            case "employee" -> dir = dir + "employee/";
        }

        try {
            Path path = Paths.get(dir + file.getOriginalFilename());
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            fileName = Optional.of(originalFileName.replaceAll(" ", "%20"));
        } catch (IOException ignored) {}

        return fileName;
    }

    public void deletePhoto(Long id) {
        Optional<Photo> photo = photoRepository.findById(id);
        photoRepository.delete(photo.get());
    }

    public List<pl.umk.mat.booking.model.Service> getAllServices() {
        return serviceRepository.findAll();
    }

    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }

    public boolean saveService(pl.umk.mat.booking.model.Service service, long[] selectedEmployees) {
        if(selectedEmployees != null)
            for (long selectedEmployee : selectedEmployees)
                if (employeeRepository.existsById(selectedEmployee))
                    service.getSelectedEmployees().add(employeeRepository.findById(selectedEmployee).get());

        try {
            serviceRepository.save(service);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public pl.umk.mat.booking.model.Service getSpecifiedService(Long id) {
        return serviceRepository.findById(id).get();
    }

    public boolean addEmployee(Employee employee, MultipartFile file) {
        final String type = "employee";
        boolean isSavedWithPhoto = true;
        Optional<String> fileNameOptional = saveFileInDirectory(file, type);
        Photo photo;
        if (fileNameOptional.isEmpty()) {
            isSavedWithPhoto = false;
            photo = new Photo("default.jpg", type);
        }else {
            String fileName = fileNameOptional.get();
            photo = new Photo(fileName, type);
        }
        employee.setPhoto(photo);
        employeeService.addWithDefaultRole(employee);

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
}
