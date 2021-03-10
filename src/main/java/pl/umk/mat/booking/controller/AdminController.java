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
import pl.umk.mat.booking.model.Service;
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

    @GetMapping("/admin/service")
    public String serviceMenu(Model model){
        model.addAttribute("serviceList", adminService.getAllServices());
        model.addAttribute("service", new Service());
        model.addAttribute("employeeList", adminService.getAllEmployee());
        return "servicePanel";
    }

    @PostMapping("/admin/service/add")
    public String addNewService(@ModelAttribute Service service, @RequestParam(value = "selected", required = false) long[] selectedEmployees, RedirectAttributes attributes){
        if(adminService.saveService(service, selectedEmployees))
            attributes.addFlashAttribute("message", "Usługa dodana pomyślnie");
        else
            attributes.addFlashAttribute("message", "Wystąpił błąd zapisu");
        return "redirect:/admin/service";
    }

    @GetMapping("admin/service/change")
    private String getServiceInfo(@RequestParam Long id, Model model,RedirectAttributes attributes){
        model.addAttribute("service", adminService.getSpecifiedService(id));
        model.addAttribute("employeeList", adminService.getAllEmployee());
        return "modifyService";
    }

    @PostMapping("admin/service/change")
    private String changeServiceInfo (@ModelAttribute Service service, @RequestParam(value = "selected", required = false) long[] selectedEmployees,RedirectAttributes attributes){
        if(adminService.saveService(service, selectedEmployees))
            attributes.addFlashAttribute("message", "Zmiany zostały zaakceptowane pomyślnie");
        else
            attributes.addFlashAttribute("message", "Wystąpił błąd podczas zapisywania zmian");
        return "redirect:/admin/service";
    }
}
