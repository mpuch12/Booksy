package pl.umk.mat.booking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.umk.mat.booking.service.EmployeeService;

@Controller
public class MainController {

    @GetMapping("/")
    public String home(Model model){
        return "index";
    }

}