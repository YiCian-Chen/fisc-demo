package edu.tku.web.controller.common;

import edu.tku.db.model.User;
import edu.tku.db.model.Func;
import edu.tku.db.repository.UserRepository;
import edu.tku.db.repository.FuncRepository;
import edu.tku.web.entity.CustomUserDetails;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@Log4j2
public class IndexController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FuncRepository funcRepository;
    @RequestMapping("/")
    public String index(Model model, @RequestParam(required = false) String name){
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("name", customUserDetails.getUsername());
        
        // top menu
        List<Func> funcs = new ArrayList<>();
        funcs.addAll(funcRepository.findAll());
        model.addAttribute("funcs", funcs);
        return "index";
    }
}
