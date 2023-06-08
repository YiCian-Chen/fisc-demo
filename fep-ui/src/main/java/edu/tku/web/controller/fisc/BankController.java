package edu.tku.web.controller.fisc;

import edu.tku.db.model.Bank;
// import edu.tku.db.model.bank;
// import edu.tku.db.repository.bankRepository;
import edu.tku.db.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class BankController {
    @Autowired
    private BankRepository bankRepository;

    @GetMapping("/bank")
    public String page(Model model, @RequestParam(name = "bankcode", required = false) String bankcode) {
        List<Bank> banks = new ArrayList<>();
        if(bankcode != null && !bankcode.equals("")) {
            bankRepository.findById(bankcode).ifPresent(bank -> banks.add(bank));
        }else{
            banks.addAll(bankRepository.findAll());
        }
        model.addAttribute("banks", banks);
        return "fisc/bank";
    }
    @GetMapping("/bank/detail")
    public String pageDetail(Model model, @RequestParam(name = "bankCode", required = false) String bankCode) {
        Bank bank = bankRepository.findById(StringUtils.defaultString(bankCode, "")).orElse(new Bank());
        model.addAttribute("bank", bank);
        return "fisc/bankDetail";
    }
    @PostMapping("/bank")
    public String pageDetail(Model model, @ModelAttribute Bank bank) {
        if(bank.getAction().equals("D")) {
            bankRepository.deleteById(bank.getBankCode());
        }else {
            bankRepository.save(bank);
        }
        model.addAttribute("banks", bankRepository.findAll());
        return "fisc/bank";
    }

}
