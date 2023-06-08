package edu.tku.web.controller.system;

import edu.tku.db.model.User;
import edu.tku.db.model.Role;
import edu.tku.db.model.Func;
import edu.tku.db.repository.UserRepository;
import edu.tku.db.repository.RoleRepository;
import edu.tku.db.repository.FuncRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import edu.tku.web.entity.CustomUserDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private FuncRepository funcRepository;

    @GetMapping("/user")
    public String page(Model model, @RequestParam(name = "userId", required = false) String userId) {
        List<User> users = new ArrayList<>();
        if (userId != null && !userId.equals("")) {
            userRepository.findById(userId).ifPresent(user -> users.add(user));
        } else {
            users.addAll(userRepository.findAll());
        }
        model.addAttribute("users", users);
        
        get_TopMenu(model);
        return "system/user";
    }

    @GetMapping("/user/detail")
    public String pageDetail(Model model, @RequestParam(name = "userId", required = false) String userId) {
        User user = userRepository.findById(StringUtils.defaultString(userId, "")).orElse(new User());
        model.addAttribute("user", user);
        
        List<Role> roles = new ArrayList<>();
        roles.addAll(roleRepository.findAll());
        model.addAttribute("roles", roles);
        
        get_TopMenu(model);
        return "system/userDetail";
    }

    @PostMapping("/user")
    public String pageDetail(Model model, @ModelAttribute User user) {
        if (user.getAction().equals("D")) {
            userRepository.deleteById(user.getUserId());
        } else {
            user.setBranchId("");
            user.setDepId("");
            user.setToken("");
            user.setEnabled(true);
            user.setLastLoginIp("");
            user.setLastLoginTime(new Date());
            // user.setRole(roleRepository.findById(user.getRoleId()));
            
            BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
            user.setPassword(bcryptPasswordEncoder.encode(user.getPassword()));
            
            CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(user.getUserName().equals(customUserDetails.getUsername()))
                customUserDetails.setRole(roleRepository.findById(user.getRoleId()).get());
            
            userRepository.save(user);
        }
        model.addAttribute("users", userRepository.findAll());
        
        get_TopMenu(model);
        return "system/user";
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
