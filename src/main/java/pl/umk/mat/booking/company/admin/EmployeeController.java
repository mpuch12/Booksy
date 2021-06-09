package pl.umk.mat.booking.company.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.umk.mat.booking.common.model.Employee;
import pl.umk.mat.booking.company.service.EmployeeService;

@Controller
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/admin/employee/all")
    public String getEmployees(Model model) {
        model.addAttribute("employees", employeeService.getAllEmployee());
        model.addAttribute("employee", new Employee());
        return "employeePanel";
    }

    @PostMapping("/admin/employee/add")
    public String addEmployee(@ModelAttribute Employee employee, @RequestParam(required = false) MultipartFile file,
                              RedirectAttributes attributes) {
        var message = employeeService.addEmployee(employee, file);
        attributes.addFlashAttribute("message", message);
        return "redirect:/admin/employee/all";
    }

    @PostMapping("/admin/employee/delete")
    public String deleteEmployee(@RequestParam Long id, RedirectAttributes attributes) {
        var message = employeeService.deleteEmployee(id);
        attributes.addFlashAttribute("message", message);
        return "redirect:/admin/employee/all";
    }

    @GetMapping("/admin/employee")
    public String getEmployeeInfo(@RequestParam Long id, Model model) {
        model.addAttribute("employee", employeeService.getSpecifiedEmployee(id));
        return "modifyEmployee";
    }

    @PostMapping("/admin/employee/change")
    public String changeEmployeeInfo(@ModelAttribute Employee employee,
                                     @RequestParam(required = false) MultipartFile file, RedirectAttributes attributes) {
        var message = employeeService.updateEmployee(employee, file);
        attributes.addFlashAttribute("message", message);

        var redirectedUrl = "/admin/employee?id=" + employee.getId();
        return "redirect:" + redirectedUrl;
    }


}
