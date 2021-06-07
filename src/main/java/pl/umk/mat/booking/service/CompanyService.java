package pl.umk.mat.booking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.umk.mat.booking.model.CompanyDetails;
import pl.umk.mat.booking.model.Photo;
import pl.umk.mat.booking.repository.CompanyDetailsRepository;

import java.util.Optional;

@Service
public class CompanyService {
    private CompanyDetailsRepository companyDetailsRepository;
    private PhotoService photoService;

    public CompanyService(CompanyDetailsRepository companyDetailsRepository, PhotoService photoService) {
        this.companyDetailsRepository = companyDetailsRepository;
        this.photoService = photoService;
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
        Optional<String> fileNameOptional = photoService.saveFileInDirectory(file, type);

        if (fileNameOptional.isEmpty())
            return false;

        String fileName = fileNameOptional.get();

        CompanyDetails companyDetails = companyDetailsRepository.findById(1);
        Photo photo = new Photo(fileName, type);

        companyDetails.addPhoto(photo);
        companyDetailsRepository.save(companyDetails);
        return true;
    }

    public void deleteCompanyPhoto(Long id) {
        photoService.deletePhoto(id);
    }
}
