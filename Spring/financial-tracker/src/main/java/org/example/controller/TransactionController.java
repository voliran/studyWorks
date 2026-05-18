package org.example.controller;

import org.example.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Controller
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/")
    public String summary(Model model) {
        model.addAttribute("transactions", transactionService.findAll());
        return "summary";
    }

    @PostMapping("/expenses/add")
    public String addExpense(@RequestParam(name = "amount") BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0 || amount.compareTo(BigDecimal.valueOf(100_000_000)) > 0)  {
            throw new IllegalArgumentException("Сумма должна быть больше 0 и не больше 100_000_000");
        }
        transactionService.saveExpense(amount);
        return "redirect:/";
    }

    @PostMapping("/incomes/add")
    public String addIncome(@RequestParam(name = "amount") BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Сумма должна быть больше 0 и не больше 100_000_000");
        }
        transactionService.saveIncome(amount);
        return "redirect:/";
    }
}
