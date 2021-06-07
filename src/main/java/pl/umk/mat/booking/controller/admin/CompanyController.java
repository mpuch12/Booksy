package pl.umk.mat.booking.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.umk.mat.booking.model.CompanyDetails;
import pl.umk.mat.booking.model.Photo;
import pl.umk.mat.booking.service.CompanyService;

import java.util.List;

@Controller
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/admin")
    public String adminPanel(Model model) {
        CompanyDetails companyDetails = companyService.getCompanyDetails();
        List<Photo> photos = companyDetails.getPhotos();
        model.addAttribute("company", companyDetails);
        model.addAttribute("workPhotos", photos);
        model.addAttribute("workshopPhotos", photos);
        return "adminPanel";
    }

    @PostMapping("/admin/update")
    public String update(@RequestParam String field, @ModelAttribute CompanyDetails companyDetails, RedirectAttributes attributes) {
        companyService.updateCompanyDetails(companyDetails, field);
        attributes.addFlashAttribute("message", "Zmiany zostały zaakceptowane");
        return "redirect:/admin";
    }

    @PostMapping("/admin/photo/upload")
    public String uploadPhoto(@RequestParam String type, @RequestParam("file") MultipartFile file, RedirectAttributes attributes) {
        if (file.isEmpty()) {
            attributes.addFlashAttribute("message", "Należy dodać plik");
        } else if (companyService.saveCompanyPhoto(file, type))
            attributes.addFlashAttribute("message", "Pomyślnie przesłano");
        else
            attributes.addFlashAttribute("message", "Wystąpił nieoczekiwany błąd");

        return "redirect:/admin";
    }

    @PostMapping("/admin/photo/delete")
    public String deletePhoto(@RequestParam Long id, RedirectAttributes attributes) {
        companyService.deleteCompanyPhoto(id);
        attributes.addFlashAttribute("message", "Usunięto");
        return "redirect:/admin";
    }
}
