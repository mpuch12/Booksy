package pl.umk.mat.booking.controller;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.umk.mat.booking.model.CompanyDetails;
import pl.umk.mat.booking.service.AdminService;

import javax.websocket.server.PathParam;

@Controller
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/admin")
    public String adminPanel(Model model){
        CompanyDetails companyDetails = adminService.getCompanyDetails();
        model.addAttribute("company", companyDetails);
        return "adminPanel";
    }

    @PostMapping("/admin/update")
    public String update(@RequestParam String field, @ModelAttribute CompanyDetails companyDetails){
        adminService.updateCompanyDetails(companyDetails, field);
        return "redirect:/admin";
    }

}
