package pl.umk.mat.booking.controller;

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
import pl.umk.mat.booking.service.AdminService;

import java.util.List;

@Controller
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/admin")
    public String adminPanel(Model model){
        CompanyDetails companyDetails = adminService.getCompanyDetails();
        List<Photo> photos = companyDetails.getPhotos();
        model.addAttribute("company", companyDetails);
        model.addAttribute("workPhotos", photos);
        model.addAttribute("workshopPhotos", photos);
        return "adminPanel";
    }

    @PostMapping("/admin/update")
    public String update(@RequestParam String field, @ModelAttribute CompanyDetails companyDetails, RedirectAttributes attributes){
        adminService.updateCompanyDetails(companyDetails, field);
        attributes.addFlashAttribute("message","Zmiany zostały zaakceptowane");
        return "redirect:/admin";
    }

    @PostMapping("/admin/photo/upload")
    public String uploadFile(@RequestParam String type, @RequestParam("file") MultipartFile file, RedirectAttributes attributes) {
        if (file.isEmpty()) {
            attributes.addFlashAttribute("message", "Należy dodać plik");
        }else
            if(adminService.saveFile(file, type))
                attributes.addFlashAttribute("message", "Pomyślnie przesłano");
            else
                attributes.addFlashAttribute("message", "Wystąpił nieoczekiwany błąd");

        return "redirect:/admin";
    }

    @PostMapping("/admin/photo/delete")
    public String uploadFile(@RequestParam Long id,RedirectAttributes attributes) {
        adminService.deletePhoto(id);
        attributes.addFlashAttribute("message", "Usunięto");
        return "redirect:/admin";
    }

}
