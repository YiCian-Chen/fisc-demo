package edu.tku.web.controller.system;

import edu.tku.db.model.Role;
import edu.tku.db.model.Func;
import edu.tku.db.repository.RoleRepository;
import edu.tku.db.repository.FuncRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import edu.tku.web.entity.CustomUserDetails;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RoleController {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private FuncRepository funcRepository;
    
    @GetMapping("/role")
    public String page(Model model, @RequestParam(name = "roleId", required = false) String roleId) {
        List<Role> roles = new ArrayList<>();
        if(roleId != null && !roleId.equals("")) {
            roleRepository.findById(roleId).ifPresent(role -> roles.add(role));
        }else{
            roles.addAll(roleRepository.findAll());
        }
        model.addAttribute("roles", roles);
        
        get_TopMenu(model);
        return "system/role";
    }
    @GetMapping("/role/detail")
    public String pageDetail(Model model, @RequestParam(name = "roleId", required = false) String roleId) {
        Role role = roleRepository.findById(StringUtils.defaultString(roleId, "")).orElse(new Role());
        model.addAttribute("role", role);
        
        get_TopMenu(model);
        return "system/roleDetail";
    }
    @PostMapping("/role")
    public String pageDetail(Model model, @ModelAttribute Role role) {
        if(role.getAction().equals("D")) {
            roleRepository.deleteById(role.getRoleId());
        }else {
            if (role.getAction().equals("C"))
                role.setFunctions("{\"folder.system\":[],\"system.users\":[\"q\",\"m\"],\"system.roles\":[\"q\",\"m\"],\"system.permissions\":[\"q\",\"m\"],\"folder.fisc\":[],\"fisc.banks\":[\"q\",\"m\"]}");
            else if (role.getAction().equals("U"))
                role.setFunctions(roleRepository.findById(role.getRoleId()).get().getFunctions());
            roleRepository.save(role);
        }
        model.addAttribute("roles", roleRepository.findAll());

        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (role.getRoleId().equals(customUserDetails.getRole().getRoleId()))
            customUserDetails.setRole(roleRepository.findById(role.getRoleId()).get());
        
        get_TopMenu(model);
        return "system/role";
    }

    public void get_TopMenu(Model model){
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        //get role funcs
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
