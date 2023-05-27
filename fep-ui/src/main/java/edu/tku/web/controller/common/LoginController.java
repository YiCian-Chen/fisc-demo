package edu.tku.web.controller.common;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(Model model, @RequestParam(required = false) boolean error){
        if(error) {
            model.addAttribute("error", "account or password is incorrect.");
        }
        return "login";
    }
}
