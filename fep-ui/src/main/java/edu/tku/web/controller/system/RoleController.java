package edu.tku.web.controller.system;

import edu.tku.db.model.Role;
import edu.tku.db.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class RoleController {
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/role")
    public String page(Model model, @RequestParam(name = "roleId", required = false) String roleId) {
        List<Role> roles = new ArrayList<>();
        if(roleId != null && !roleId.equals("")) {
            roleRepository.findById(roleId).ifPresent(role -> roles.add(role));
        }else{
            roles.addAll(roleRepository.findAll());
        }
        model.addAttribute("roles", roles);
        return "system/role";
    }
    @GetMapping("/role/detail")
    public String pageDetail(Model model, @RequestParam(name = "roleId", required = false) String roleId, @RequestParam(name = "action") String action) {
        Role role = new Role();
        if(roleId != null && !roleId.equals("")) {
            role = roleRepository.findById(roleId).orElse(new Role());
        }
        String label = "修改";
        if(action.equals("A")) {
            label = "新增";
        }
        model.addAttribute("role", role);
        model.addAttribute("action", action);
        model.addAttribute("actionLabel", label);
        return "system/roleDetail";
    }
    @PostMapping("/role/detail")
    public String pageDetail(Model model, @RequestParam(required = false) String roleId, @RequestParam(required = false) String roleName, @RequestParam(required = false) String roleDesc, @RequestParam String action) {
        switch (action) {
            case "D":
                roleRepository.deleteById(roleId);
                break;
            case "A":
                Role role = new Role();
                role.setRoleId(UUID.randomUUID().toString());
                role.setRoleName(roleName);
                role.setRoleDesc(roleDesc);
                role.setFunctions("Function");
                roleRepository.save(role);
                break;
            case "M":
                role = new Role();
                role.setRoleId(roleId);
                role.setRoleName(roleName);
                role.setRoleDesc(roleDesc);
                role.setFunctions("Function");
                roleRepository.save(role);
                break;
        }

        model.addAttribute("roles", roleRepository.findAll());
        return "system/role";
    }

    @GetMapping("/role/add")
    public String pageAdd(Model model) {
        return "system/roleAdd";
    }
    @PostMapping("/api/v1/role")
    public ResponseEntity add(@RequestBody Role role) {


        return ResponseEntity.ok().build();
    }
}
