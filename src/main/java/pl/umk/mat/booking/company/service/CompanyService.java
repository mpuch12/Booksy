package pl.umk.mat.booking.company.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.umk.mat.booking.common.model.CompanyDetails;
import pl.umk.mat.booking.common.model.Photo;
import pl.umk.mat.booking.common.repository.CompanyDetailsRepository;
import pl.umk.mat.booking.company.Utils;

@Service
public class CompanyService {
    private CompanyDetailsRepository companyDetailsRepository;
    private PhotoService photoService;

    public CompanyService(CompanyDetailsRepository companyDetailsRepository, PhotoService photoService) {
        this.companyDetailsRepository = companyDetailsRepository;
        this.photoService = photoService;
    }

    public CompanyDetails getCompanyDetails() {
        return companyDetailsRepository.findAll().get(0);
    }

    public String updateCompanyDetails(CompanyDetails companyDetails) {
        var savedCompanyDetails = getCompanyDetails();
        try {
            var updatedCompanyDetails = Utils.updateObject(companyDetails, savedCompanyDetails);
            companyDetailsRepository.save(updatedCompanyDetails);
            return "Zmiany zostały zaakceptowane";
        } catch (Exception e) {
            return "Wystąpił nieoczekiwany błąd";
        }
    }

    public String saveCompanyPhoto(MultipartFile file, String type) {
        try {
            var fileNameOptional = photoService.saveFileInDirectory(file, type);

            if (fileNameOptional.isEmpty()) {
                return "Należy dodać plik";
            }

            var fileName = fileNameOptional.get();

            var companyDetails = getCompanyDetails();
            var photo = new Photo(fileName, type);

            companyDetails.addPhoto(photo);
            companyDetailsRepository.save(companyDetails);
            return "Pomyślnie przesłano";
        }catch (Exception e){
            return "Wystąpił nieoczekiwany błąd";
        }
    }

    public String deleteCompanyPhoto(Long id) {
        return photoService.deletePhoto(id);
    }
}
