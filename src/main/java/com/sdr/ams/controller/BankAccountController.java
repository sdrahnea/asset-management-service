package com.sdr.ams.controller;

import com.sdr.ams.model.financial.BankAccount;
import com.sdr.ams.service.BankAccountService;
import com.sdr.ams.service.ExportService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
@RequestMapping("/bank-accounts")
public class BankAccountController {

    private final BankAccountService bankAccountService;
    private final ExportService exportService;

    public BankAccountController(BankAccountService bankAccountService, ExportService exportService) {
        this.bankAccountService = bankAccountService;
        this.exportService = exportService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("bankAccounts", bankAccountService.findAll());
        return "bank-accounts/list";
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> export(@RequestParam(defaultValue = "csv") String format) throws Exception {
        var items = bankAccountService.findAll();
        if ("excel".equalsIgnoreCase(format) || "xlsx".equalsIgnoreCase(format)) {
            byte[] data = exportService.toExcel(items, "BankAccount");
            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"bank-accounts.xlsx\"")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(data);
        }

        byte[] data = exportService.toCsv(items, "BankAccount");
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"bank-accounts.csv\"")
            .contentType(MediaType.parseMediaType("text/csv; charset=UTF-8"))
            .body(data);
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
