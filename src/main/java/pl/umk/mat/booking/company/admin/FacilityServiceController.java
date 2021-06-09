package pl.umk.mat.booking.company.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.umk.mat.booking.common.model.FacilityService;
import pl.umk.mat.booking.company.service.FacilityServiceService;

@Controller
public class FacilityServiceController {
    private final FacilityServiceService facilityServiceService;

    public FacilityServiceController(FacilityServiceService facilityServiceService) {
        this.facilityServiceService = facilityServiceService;
    }

    @GetMapping("/admin/service/all")
    public String serviceMenu(Model model) {
        model.addAttribute("facilityServiceList", facilityServiceService.getAllFacilityServices());
        model.addAttribute("facilityService", new FacilityService());
        model.addAttribute("employeeList", facilityServiceService.getAllEmployees());
        return "servicePanel";
    }

    @PostMapping("/admin/service/delete")
    public String deleteService(@RequestParam Long id, RedirectAttributes attributes) {
        var message = facilityServiceService.delete(id);
        attributes.addFlashAttribute("message", message);
        return "redirect:/admin/service/all";
    }

    @PostMapping("/admin/service/add")
    public String addNewService(@ModelAttribute FacilityService facilityService,
                                @RequestParam(value = "selected", required = false) long[] selectedEmployees,
                                RedirectAttributes attributes) {
        var message = facilityServiceService.save(facilityService, selectedEmployees);
        attributes.addFlashAttribute("message", message);
        return "redirect:/admin/service/all";
    }

    @GetMapping("/admin/service")
    private String getServiceInfo(@RequestParam Long id, Model model) {
        model.addAttribute("facilityService", facilityServiceService.getSpecifiedFacilityService(id));
        model.addAttribute("employeeList", facilityServiceService.getAllEmployees());
        return "modifyService";
    }

    @PostMapping("/admin/service/change")
    private String changeServiceInfo(@ModelAttribute FacilityService facilityService,
                                     @RequestParam(value = "selected", required = false) long[] selectedEmployees,
                                     RedirectAttributes attributes) {
        var message = facilityServiceService.update(facilityService, selectedEmployees);
        attributes.addFlashAttribute("message", message);

        return "redirect:/admin/service/all";
    }
}
