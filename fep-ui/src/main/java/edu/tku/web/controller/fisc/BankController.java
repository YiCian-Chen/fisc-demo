package edu.tku.web.controller.fisc;

import edu.tku.db.model.Bank;
import edu.tku.db.model.Func;
import edu.tku.db.repository.BankRepository;
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
public class BankController {
    @Autowired
    private BankRepository bankRepository;
    @Autowired
    private FuncRepository funcRepository;

    @GetMapping("/bank")
    public String page(Model model, @RequestParam(name = "bankId", required = false) String bankId) {
        List<Bank> banks = new ArrayList<>();
        if(bankId != null && !bankId.equals("")) {
            bankRepository.findById(bankId).ifPresent(bank -> banks.add(bank));
        }else{
            banks.addAll(bankRepository.findAll());
        }
        model.addAttribute("banks", banks);
        
        get_TopMenu(model);
        return "fisc/bank";
    }
    @GetMapping("/bank/detail")
    public String pageDetail(Model model, @RequestParam(name = "bankId", required = false) String bankId) {
        Bank bank = bankRepository.findById(StringUtils.defaultString(bankId, "")).orElse(new Bank());
        model.addAttribute("bank", bank);
        
        get_TopMenu(model);
        return "fisc/bankDetail";
    }
    @PostMapping("/bank")
    public String pageDetail(Model model, @ModelAttribute Bank bank) {
        if(bank.getAction().equals("D")) {
            bankRepository.deleteById(bank.getBankId());
        }else {
            bank.setEnabled(true);
            bankRepository.save(bank);
        }
        model.addAttribute("banks", bankRepository.findAll());
        
        get_TopMenu(model);
        return "fisc/bank";
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
