package pl.umk.mat.booking.company.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.umk.mat.booking.common.model.CompanyDetails;
import pl.umk.mat.booking.company.service.CompanyService;

@Controller
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/admin")
    public String adminPanel(Model model) {
        var companyDetails = companyService.getCompanyDetails();
        model.addAttribute("company", companyDetails);
        return "adminPanel";
    }

    @PostMapping("/admin/update")
    public String update(@ModelAttribute CompanyDetails companyDetails, RedirectAttributes attributes) {
        var message = companyService.updateCompanyDetails(companyDetails);
        attributes.addFlashAttribute("message", message);
        return "redirect:/admin";
    }

    @PostMapping("/admin/photo/upload")
    public String uploadPhoto(@RequestParam String type, @RequestParam(required = false) MultipartFile file, RedirectAttributes attributes) {
        var message = companyService.saveCompanyPhoto(file, type);
        attributes.addFlashAttribute("message", message);

        return "redirect:/admin";
    }

    @PostMapping("/admin/photo/delete")
    public String deletePhoto(@RequestParam Long id, RedirectAttributes attributes) {
        var message = companyService.deleteCompanyPhoto(id);
        attributes.addFlashAttribute("message", message);
        return "redirect:/admin";
    }
}
