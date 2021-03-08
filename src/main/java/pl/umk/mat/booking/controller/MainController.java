package pl.umk.mat.booking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String home(){
        return "index";
    }

    @GetMapping("/panel")
    public String mainPanel(){
        return "mainPanel";
    }

    @GetMapping("/admin")
    public String adminPanel(){
        return "adminPanel";
    }


}