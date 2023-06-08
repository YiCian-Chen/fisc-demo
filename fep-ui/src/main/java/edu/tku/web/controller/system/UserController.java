package edu.tku.web.controller.system;

import edu.tku.db.model.User;
import edu.tku.db.model.Role;
import edu.tku.db.repository.UserRepository;
import edu.tku.db.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import aj.org.objectweb.asm.Type;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Date;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/user")
    public String page(Model model, @RequestParam(name = "userId", required = false) String userId) {
        List<User> users = new ArrayList<>();
        if (userId != null && !userId.equals("")) {
            userRepository.findById(userId).ifPresent(user -> users.add(user));
        } else {
            users.addAll(userRepository.findAll());
        }
        model.addAttribute("users", users);
        return "system/user";
    }

    @GetMapping("/user/detail")
    public String pageDetail(Model model, @RequestParam(name = "userId", required = false) String userId) {
        User user = userRepository.findById(StringUtils.defaultString(userId, "")).orElse(new User());
        model.addAttribute("user", user);
        
        List<Role> roles = new ArrayList<>();
        roles.addAll(roleRepository.findAll());
        model.addAttribute("roles", roles);
        return "system/userDetail";
    }

    @PostMapping("/user")
    public String pageDetail(Model model, @ModelAttribute User user) {
        if (user.getAction().equals("D")) {
            userRepository.deleteById(user.getUserId());
        } else {
            // System.out.println(user);
            user.setBranchId("");
            user.setDepId("");
            user.setEmail("");
            user.setToken("");
            user.setEnabled(true);
            user.setLastLoginIp("");
            user.setLastLoginTime(new Date());
            // user.setRole(roleRepository.findById(user.getRoleId()));
            // System.out.println(user);
            BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
            String userPassword = bcryptPasswordEncoder.encode(user.getPassword());
            user.setPassword(userPassword);
            userRepository.save(user);
            // System.out.println(user);
        }
        model.addAttribute("users", userRepository.findAll());
        return "system/user";
    }
}
