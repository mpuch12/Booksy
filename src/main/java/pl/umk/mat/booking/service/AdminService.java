package pl.umk.mat.booking.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.umk.mat.booking.model.CompanyDetails;
import pl.umk.mat.booking.repository.CompanyDetailsRepository;
import pl.umk.mat.booking.repository.EmployeeRepository;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Service
public class AdminService {
    private EmployeeRepository employeeRepository;
    private CompanyDetailsRepository companyDetailsRepository;

    public AdminService(EmployeeRepository employeeRepository, CompanyDetailsRepository companyDetailsRepository) {
        this.employeeRepository = employeeRepository;
        this.companyDetailsRepository = companyDetailsRepository;
    }

    public CompanyDetails getCompanyDetails() {
        return companyDetailsRepository.findById(1);
    }

    public void updateCompanyDetails(CompanyDetails companyDetails, String field) {
        CompanyDetails savedCompanyDetails = companyDetailsRepository.findById(1);

        switch (field){
            case "name":
            savedCompanyDetails.setName(companyDetails.getName());
            companyDetailsRepository.save(savedCompanyDetails);
            break;
        }
    }
}
