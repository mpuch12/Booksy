package pl.umk.mat.booking.company.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.umk.mat.booking.common.repository.PhotoRepository;

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

    Optional<String> saveFileInDirectory(MultipartFile file, String type) {
        var originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        var dir = UPLOAD_DIR;
        Optional<String> fileName = Optional.empty();

        switch (type) {
            case "work" -> dir = dir + "work/";
            case "workshop" -> dir = dir + "workshop/";
            case "employee" -> dir = dir + "employee/";
        }

        try {
            var path = Paths.get(dir + file.getOriginalFilename());
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            fileName = Optional.of(originalFileName.replaceAll(" ", "%20"));
        } catch (IOException ignored) {
        }

        return fileName;
    }

    public String deletePhoto(Long id) {
        //TODO PI-33 usuwanie z folderu static
        try {
            photoRepository.deleteById(id);
            return "Pomyślnie usunieto";
        }catch (Exception e){
            return "Wystąpił nieoczekiwany błąd";
        }
    }
}
