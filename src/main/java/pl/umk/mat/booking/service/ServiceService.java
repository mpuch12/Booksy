package pl.umk.mat.booking.service;

import org.springframework.stereotype.Service;
import pl.umk.mat.booking.model.Employee;
import pl.umk.mat.booking.repository.EmployeeRepository;
import pl.umk.mat.booking.repository.ServiceRepository;

import java.util.List;

import static pl.umk.mat.booking.common.security.SecurityConfig.DEFAULT_ROLE;

@Service
public class ServiceService {
    private ServiceRepository serviceRepository;
    private EmployeeRepository employeeRepository;

    public ServiceService(ServiceRepository serviceRepository, EmployeeRepository employeeRepository) {
        this.serviceRepository = serviceRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<pl.umk.mat.booking.model.Service> getAllServices() {
        return serviceRepository.findAll();
    }

    public pl.umk.mat.booking.model.Service getSpecifiedService(Long id) {
        return serviceRepository.findById(id).get();
    }

    public boolean saveService(pl.umk.mat.booking.model.Service service, long[] selectedEmployees) {
        if (selectedEmployees != null)
            for (long selectedEmployee : selectedEmployees)
                if (employeeRepository.existsById(selectedEmployee))
                    service.getSelectedEmployees().add(employeeRepository.findById(selectedEmployee).get());

        try {
            serviceRepository.save(service);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public List<Employee> getAllEmployeeForService() {
        return employeeRepository.findAllByRoles_Role(DEFAULT_ROLE);
    }

    public boolean delete(Long id) {
        try {
            serviceRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
