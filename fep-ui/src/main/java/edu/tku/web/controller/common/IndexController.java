package edu.tku.web.controller.common;

import edu.tku.db.model.User;
import edu.tku.db.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@Log4j2
public class IndexController {
    @Autowired
    private UserRepository userRepository;
    @RequestMapping("/")
    public String index(Model model, @RequestParam(required = false) String name){
        model.addAttribute("name", userRepository.findById("kenny").get().getUserName());
        return "index";
    }
}
