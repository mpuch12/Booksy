package pl.umk.mat.booking.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.umk.mat.booking.model.Employee;
import pl.umk.mat.booking.service.EmployeeService;

@Controller
public class EmployeeControler {
    private final EmployeeService employeeService;

    public EmployeeControler(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/admin/employee")
    public String getEmployees(Model model){
        model.addAttribute("employees", employeeService.getAllEmployee());
        model.addAttribute("employee", new Employee());
        return "employeePanel";
    }

    @PostMapping("/admin/employee/add")
    public String addEmployee(@ModelAttribute Employee employee, @RequestParam MultipartFile file, RedirectAttributes attributes ){
        if(employeeService.addEmployee(employee, file))
            attributes.addFlashAttribute("message", "Pracownik dodany pomyślnie");
        else
            attributes.addFlashAttribute("message", "Wystąpił nieoczekiwany błąd");

        return "redirect:/admin/employee";
    }

    @PostMapping("/admin/employee/delete")
    public String deleteEmployee(@RequestParam Long id, RedirectAttributes attributes){
        boolean isDeletedCorrectly = employeeService.deleteEmployee(id);
        if (isDeletedCorrectly)
            attributes.addFlashAttribute("message", "Pracownik usunięty pomyślnie");
        else
            attributes.addFlashAttribute("message", "Wystąpił błąd podczas usuwania");
        return "redirect:/admin/employee";
    }

    @GetMapping("/admin/employee/change")
    public String getEmployeeInfo(@RequestParam Long id, Model model){
        model.addAttribute("employee", employeeService.getSpecifiedEmployee(id));
        return "modifyEmployee";
    }

    @PostMapping("/admin/employee/change")
    public String changeEmployeeInfo(@ModelAttribute Employee employee,@RequestParam String field, @RequestParam MultipartFile file, RedirectAttributes attributes){
        boolean isUpdatedCorrectly = employeeService.updateEmployee(employee, field, file);
        if(isUpdatedCorrectly)
            attributes.addFlashAttribute("message", "Zmiany zostały pomyslnie zaakceptowane");
        else
            attributes.addFlashAttribute("message", "Wystąpił błąd zapisu");

        String redirectedUrl = "/admin/employee/change?id=" + employee.getId();
        return "redirect:"+ redirectedUrl;
    }


}
