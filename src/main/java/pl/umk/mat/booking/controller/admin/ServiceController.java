package pl.umk.mat.booking.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.umk.mat.booking.model.Service;
import pl.umk.mat.booking.service.ServiceService;

@Controller
public class ServiceController {
    private final ServiceService serviceService;

    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @GetMapping("/admin/service")
    public String serviceMenu(Model model){
        model.addAttribute("serviceList", serviceService.getAllServices());
        model.addAttribute("service", new Service());
        model.addAttribute("employeeList", serviceService.getAllEmployeeForService());
        return "servicePanel";
    }

    @PostMapping("/admin/service/add")
    public String addNewService(@ModelAttribute Service service, @RequestParam(value = "selected", required = false) long[] selectedEmployees, RedirectAttributes attributes){
        if(serviceService.saveService(service, selectedEmployees))
            attributes.addFlashAttribute("message", "Usługa dodana pomyślnie");
        else
            attributes.addFlashAttribute("message", "Wystąpił błąd zapisu");
        return "redirect:/admin/service";
    }

    @GetMapping("/admin/service/change")
    private String getServiceInfo(@RequestParam Long id, Model model,RedirectAttributes attributes){
        model.addAttribute("service", serviceService.getSpecifiedService(id));
        model.addAttribute("employeeList", serviceService.getAllEmployeeForService());
        return "modifyService";
    }

    @PostMapping("/admin/service/change")
    private String changeServiceInfo (@ModelAttribute Service service, @RequestParam(value = "selected", required = false) long[] selectedEmployees,RedirectAttributes attributes){
        if(serviceService.saveService(service, selectedEmployees))
            attributes.addFlashAttribute("message", "Zmiany zostały zaakceptowane pomyślnie");
        else
            attributes.addFlashAttribute("message", "Wystąpił błąd podczas zapisywania zmian");
        return "redirect:/admin/service";
    }
}
