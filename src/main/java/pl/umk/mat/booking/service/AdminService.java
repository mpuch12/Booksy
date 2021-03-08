package pl.umk.mat.booking.service;

import org.springframework.stereotype.Service;
import pl.umk.mat.booking.model.CompanyDetails;
import pl.umk.mat.booking.repository.CompanyDetailsRepository;
import pl.umk.mat.booking.repository.EmployeeRepository;

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

        switch (field) {
            case "name" -> savedCompanyDetails.setName(companyDetails.getName());
            case "address" -> savedCompanyDetails.setAddress(companyDetails.getAddress());
            default -> companyDetailsRepository.save(savedCompanyDetails);
        }
    }
}
