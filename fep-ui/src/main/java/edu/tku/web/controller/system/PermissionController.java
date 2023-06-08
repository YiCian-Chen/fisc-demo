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
public class PermissionController {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private FuncRepository funcRepository;
    
    @GetMapping("/permission")
    public String page(Model model, @RequestParam(name = "roleId", required = false) String roleId) {
        List<Role> roles = new ArrayList<>();
        if(roleId != null && !roleId.equals("")) {
            roleRepository.findById(roleId).ifPresent(role -> roles.add(role));
        }else{
            roles.addAll(roleRepository.findAll());
        }
        model.addAttribute("roles", roles);
        
        get_TopMenu(model);
        return "system/permission";
    }
    @GetMapping("/permission/detail")
    public String pageDetail(Model model, @RequestParam(name = "roleId", required = false) String roleId) {
        Role role = roleRepository.findById(StringUtils.defaultString(roleId, "")).orElse(new Role());
        model.addAttribute("role", role);
        
        get_TopMenu(model);
        checkboxChecked(model,roleId);
        return "system/permissionDetail";
    }
    @PostMapping("/permission")
    public String pageDetail(Model model, @ModelAttribute Role role,
                                    @RequestParam("user") String U,
                                    @RequestParam("role") String R,
                                    @RequestParam("permission") String P,
                                    @RequestParam("bank") String B) {
        if(role.getAction().equals("D")) {
            roleRepository.deleteById(role.getRoleId());
        }else {
            String func_str = "{";
            if(!U.equals("off") || !R.equals("off") || !P.equals("off")){
                func_str += "\"folder.system\":[],";
            }
            if(!U.equals("off")){
                func_str += "\"system.users\":[\"q\",\"m\"],";
            }
            if(!R.equals("off")){
                func_str += "\"system.roles\":[\"q\",\"m\"],";
            }
            if(!P.equals("off")){
                func_str += "\"system.permissions\":[\"q\",\"m\"],";
            }
            if(!B.equals("off")){
                func_str += "\"folder.fisc\":[],\"fisc.banks\":[\"q\",\"m\"]";
            }
            func_str += "}";
            
            role.setFunctions(func_str);
            roleRepository.save(role);
        }
        model.addAttribute("roles", roleRepository.findAll());
        
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (role.getRoleId().equals(customUserDetails.getRole().getRoleId()))
            customUserDetails.setRole(roleRepository.findById(role.getRoleId()).get());

        get_TopMenu(model);
        return "system/permission";
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

    public void checkboxChecked(Model model, String roleId){
        // get roleId funcs
        ArrayList<String> role_func_list = new ArrayList();
        for (int i = 0; i < roleRepository.findById(roleId).get().getFunctions().split(":").length - 1; i++) {
            role_func_list.add(roleRepository.findById(roleId).get().getFunctions().split(":")[i]
                    .split("\"")[roleRepository.findById(roleId).get().getFunctions().split(":")[i].split("\"").length - 1]);
        }
        // 權限checkbox checked
        String str = "";
        for (int i = 0; i < role_func_list.size(); i++) {
            if (role_func_list.get(i).equals("system.users")) {
                str += "U";
            } else if (role_func_list.get(i).equals("system.roles")) {
                str += "R";
            } else if (role_func_list.get(i).equals("system.permissions")) {
                str += "P";
            } else if (role_func_list.get(i).equals("fisc.banks")) {
                str += "B";
            }
        }

        if (str.indexOf("U") != -1)
            model.addAttribute("U", "on");
        else
            model.addAttribute("U", "off");
        if (str.indexOf("R") != -1)
            model.addAttribute("R", "on");
        else
            model.addAttribute("R", "off");
        if (str.indexOf("P") != -1)
            model.addAttribute("P", "on");
        else
            model.addAttribute("P", "off");
        if (str.indexOf("B") != -1)
            model.addAttribute("B", "on");
        else
            model.addAttribute("B", "off");
    }
}
