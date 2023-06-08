package edu.tku.web.controller.common;

import edu.tku.db.model.Func;
import edu.tku.db.repository.FuncRepository;
import edu.tku.web.entity.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private FuncRepository funcRepository;
    @RequestMapping("/")
    public String index(Model model, @RequestParam(required = false) String name){
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("name", customUserDetails.getUsername());
        
        get_TopMenu(model);
        return "index";
    }

    public void get_TopMenu(Model model) {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // get role funcs
        ArrayList<String> role_func_list = new ArrayList();
        for (int i = 0; i < customUserDetails.getRole().getFunctions().split(":").length - 1; i++) {
            role_func_list.add(customUserDetails.getRole().getFunctions().split(":")[i]
                    .split("\"")[customUserDetails.getRole().getFunctions().split(":")[i].split("\"").length - 1]);
        }

        // create top menu funcs
        List<Func> funcs = new ArrayList<>();
        for (int i = 0; i < role_func_list.size(); i++) {
            funcRepository.findById(role_func_list.get(i)).ifPresent(func -> funcs.add(func));
        }
        model.addAttribute("funcs", funcs);
    }
}
