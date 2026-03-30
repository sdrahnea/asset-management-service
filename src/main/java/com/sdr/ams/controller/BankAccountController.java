package com.sdr.ams.controller;
import com.sdr.ams.model.financial.BankAccount;
import com.sdr.ams.service.BankAccountService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import static org.springframework.http.HttpStatus.NOT_FOUND;
@Controller
@RequestMapping("/bank-accounts")
public class BankAccountController {
    private final BankAccountService bankAccountService;
    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }
    @GetMapping
    public String list(Model model) {
        model.addAttribute("bankAccounts", bankAccountService.findAll());
        return "bank-accounts/list";
    }
    @GetMapping("/new")
    public String createForm(Model model) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setStatus(BankAccount.Status.ACTIVE);
        bankAccount.setOwnershipType(BankAccount.OwnershipType.INDIVIDUAL);
        bankAccount.setAccountType(BankAccount.AccountType.CURRENT);
        bankAccount.setCurrency("EUR");
        model.addAttribute("bankAccount", bankAccount);
        model.addAttribute("isEdit", false);
        populateEnums(model);
        return "bank-accounts/form";
    }
    @PostMapping
    public String create(
        @Valid @ModelAttribute("bankAccount") BankAccount bankAccount,
        BindingResult bindingResult,
        Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("isEdit", false);
            populateEnums(model);
            return "bank-accounts/form";
        }
        try {
            bankAccountService.create(bankAccount);
        } catch (IllegalArgumentException ex) {
            bindingResult.reject("bankAccount.error", ex.getMessage());
            model.addAttribute("isEdit", false);
            populateEnums(model);
            return "bank-accounts/form";
        }
        return "redirect:/bank-accounts";
    }
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("bankAccount", getOr404(id));
        return "bank-accounts/detail";
    }
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        BankAccount bankAccount = getOr404(id);
        model.addAttribute("bankAccount", bankAccount);
        model.addAttribute("isEdit", true);
        populateEnums(model);
        return "bank-accounts/form";
    }
    @PostMapping("/{id}")
    public String update(
        @PathVariable Long id,
        @Valid @ModelAttribute("bankAccount") BankAccount bankAccount,
        BindingResult bindingResult,
        Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("isEdit", true);
            populateEnums(model);
            return "bank-accounts/form";
        }
        try {
            bankAccountService.update(id, bankAccount);
        } catch (IllegalArgumentException ex) {
            bindingResult.reject("bankAccount.error", ex.getMessage());
            model.addAttribute("isEdit", true);
            populateEnums(model);
            return "bank-accounts/form";
        }
        return "redirect:/bank-accounts";
    }
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        bankAccountService.delete(id);
        return "redirect:/bank-accounts";
    }
    private void populateEnums(Model model) {
        model.addAttribute("accountTypes", BankAccount.AccountType.values());
        model.addAttribute("statuses", BankAccount.Status.values());
        model.addAttribute("ownershipTypes", BankAccount.OwnershipType.values());
    }
    private BankAccount getOr404(Long id) {
        try {
            return bankAccountService.findById(id);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(NOT_FOUND, ex.getMessage(), ex);
        }
    }
}
