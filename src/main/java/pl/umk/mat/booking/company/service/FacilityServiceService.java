package pl.umk.mat.booking.company.service;

import org.springframework.stereotype.Service;
import pl.umk.mat.booking.common.model.Employee;
import pl.umk.mat.booking.common.model.FacilityService;
import pl.umk.mat.booking.common.repository.EmployeeRepository;
import pl.umk.mat.booking.common.repository.FacilityServiceRepository;
import pl.umk.mat.booking.company.Utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static pl.umk.mat.booking.common.security.SecurityConfig.DEFAULT_ROLE;
import static pl.umk.mat.booking.company.Utils.updateObject;

@Service
public class FacilityServiceService {
    private FacilityServiceRepository facilityServiceRepository;
    private EmployeeRepository employeeRepository;

    public FacilityServiceService(FacilityServiceRepository facilityServiceRepository, EmployeeRepository employeeRepository) {
        this.facilityServiceRepository = facilityServiceRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<FacilityService> getAllFacilityServices() {
        return facilityServiceRepository.findAll();
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAllByRoles_Role(DEFAULT_ROLE);
    }

    public FacilityService getSpecifiedFacilityService(Long id) {
        return facilityServiceRepository.findById(id).get();
    }

    public String save(FacilityService facilityService, long[] selectedEmployees) {
        try {
            if (selectedEmployees != null) {
                var listOfEmployees = Arrays.stream(selectedEmployees)
                        .mapToObj(v -> employeeRepository.findById(v).get())
                        .collect(Collectors.toList());
                facilityService.setSelectedEmployees(listOfEmployees);
            }
            facilityServiceRepository.save(facilityService);
            return "Zmiany zostały pomyslnie zaakceptowane";
        } catch (Exception e) {
            return "Wystąpił błąd zapisu";
        }
    }

    public String update(FacilityService facilityService, long[] selectedEmployees) {
        try {
            var savedFacilityService = facilityServiceRepository.findById(facilityService.getId()).get();
            if (selectedEmployees != null) {
                var listOfEmployees = Arrays.stream(selectedEmployees)
                        .mapToObj(v -> employeeRepository.findById(v).get())
                        .collect(Collectors.toList());
                facilityService.setSelectedEmployees(listOfEmployees);
            }
            var updatedFacilityService = updateObject(facilityService, savedFacilityService);
            facilityServiceRepository.save(updatedFacilityService);
            return "Zmiany zostały pomyslnie zaakceptowane";
        } catch (Exception exception) {
            return "Wystąpił błąd zapisu";
        }
    }

    public String delete(Long id) {
        try {
            facilityServiceRepository.deleteById(id);
            return "Usunięto pomyślnie";
        } catch (Exception e) {
            return "Wystąpił błąd podczas usuwania";
        }
    }
}
