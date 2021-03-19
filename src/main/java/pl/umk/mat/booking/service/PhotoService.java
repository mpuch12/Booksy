package pl.umk.mat.booking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.umk.mat.booking.model.CompanyDetails;
import pl.umk.mat.booking.model.Photo;
import pl.umk.mat.booking.repository.CompanyDetailsRepository;
import pl.umk.mat.booking.repository.PhotoRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Service
public class PhotoService {
    private static final String UPLOAD_DIR = "src/main/resources/static/";

    private PhotoRepository photoRepository;

    public PhotoService(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    public Optional<String> saveFileInDirectory(MultipartFile file, String type) {
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
        //todo usuwanie z folderu static
        photoRepository.deleteById(id);
    }
}
