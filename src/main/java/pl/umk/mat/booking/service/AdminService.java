package pl.umk.mat.booking.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.umk.mat.booking.model.CompanyDetails;
import pl.umk.mat.booking.model.Photo;
import pl.umk.mat.booking.repository.CompanyDetailsRepository;
import pl.umk.mat.booking.repository.EmployeeRepository;
import pl.umk.mat.booking.repository.PhotoRepository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Service
public class AdminService {

    private static final String UPLOAD_DIR = "src/main/resources/static/";

    private EmployeeRepository employeeRepository;
    private CompanyDetailsRepository companyDetailsRepository;
    private PhotoRepository photoRepository;

    public AdminService(EmployeeRepository employeeRepository, CompanyDetailsRepository companyDetailsRepository, PhotoRepository photoRepository) {
        this.employeeRepository = employeeRepository;
        this.companyDetailsRepository = companyDetailsRepository;
        this.photoRepository = photoRepository;
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

    public boolean saveFile(MultipartFile file, String type) {

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String dir = UPLOAD_DIR;

        switch (type){
            case "work" -> dir = dir + "work/";
            case "workshop" -> dir = dir + "workshop/";
        }

        try {
            Path path = Paths.get(dir + fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            String fileNameWithoutSpaces = fileName.replaceAll(" ", "%20");
            CompanyDetails companyDetails = companyDetailsRepository.findById(1);
            Photo photo = new Photo(fileNameWithoutSpaces,type, companyDetails);

            companyDetails.addPhoto(photo);
            companyDetailsRepository.save(companyDetails);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public void deletePhoto(Long id) {
        Optional<Photo> photo = photoRepository.findById(id);
        photoRepository.delete(photo.get());
    }
}
