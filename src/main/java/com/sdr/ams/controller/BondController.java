package com.sdr.ams.controller;

import com.sdr.ams.model.financial.Bond;
import com.sdr.ams.service.BondService;
import com.sdr.ams.service.DetailPdfReportService;
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
@RequestMapping("/bonds")
public class BondController {

    private final BondService bondService;
    private final ExportService exportService;
    private final DetailPdfReportService detailPdfReportService;

    public BondController(BondService bondService, ExportService exportService, DetailPdfReportService detailPdfReportService) {
        this.bondService = bondService;
        this.exportService = exportService;
        this.detailPdfReportService = detailPdfReportService;
    }

    @GetMapping
    public String list(
        @RequestParam(required = false) String issuer,
        @RequestParam(required = false) Bond.BondType bondType,
        @RequestParam(required = false) Bond.TradingStatus tradingStatus,
        @RequestParam(required = false) String currency,
        Model model
    ) {
        model.addAttribute("bonds", bondService.findAll(issuer, bondType, tradingStatus, currency));
        model.addAttribute("selectedIssuer", issuer);
        model.addAttribute("selectedBondType", bondType);
        model.addAttribute("selectedTradingStatus", tradingStatus);
        model.addAttribute("selectedCurrency", currency);
        populateEnums(model);
        return "bonds/list";
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> export(
        @RequestParam(defaultValue = "csv") String format,
        @RequestParam(required = false) String issuer,
        @RequestParam(required = false) Bond.BondType bondType,
        @RequestParam(required = false) Bond.TradingStatus tradingStatus,
        @RequestParam(required = false) String currency
    ) throws Exception {
        var items = bondService.findAll(issuer, bondType, tradingStatus, currency);
        if ("excel".equalsIgnoreCase(format) || "xlsx".equalsIgnoreCase(format)) {
            byte[] data = exportService.toExcel(items, "Bond");
            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"bonds.xlsx\"")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(data);
        }

        byte[] data = exportService.toCsv(items, "Bond");
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"bonds.csv\"")
            .contentType(MediaType.parseMediaType("text/csv; charset=UTF-8"))
            .body(data);
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        Bond bond = new Bond();
        bond.setDataSource("manual-entry");
        bond.setBondType(Bond.BondType.CORPORATE);
        bond.setTradingStatus(Bond.TradingStatus.ACTIVE);
        model.addAttribute("bond", bond);
        model.addAttribute("isEdit", false);
        populateEnums(model);
        return "bonds/form";
    }

    @PostMapping
    public String create(
        @Valid @ModelAttribute("bond") Bond bond,
        BindingResult bindingResult,
        Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("isEdit", false);
            populateEnums(model);
            return "bonds/form";
        }

        try {
            bondService.create(bond);
        } catch (RuntimeException ex) {
            bindingResult.reject("bond.error", ex.getMessage());
            model.addAttribute("isEdit", false);
            populateEnums(model);
            return "bonds/form";
        }

        return "redirect:/bonds";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("bond", getOr404(id));
        return "bonds/detail";
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> detailPdf(@PathVariable Long id) {
        Bond bond = getOr404(id);
        byte[] data = detailPdfReportService.buildEntityDetailPdf("Bond", bond);
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"bond-" + id + ".pdf\"")
            .contentType(MediaType.APPLICATION_PDF)
            .body(data);
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("bond", getOr404(id));
        model.addAttribute("isEdit", true);
        populateEnums(model);
        return "bonds/form";
    }

    @PostMapping("/{id}")
    public String update(
        @PathVariable Long id,
        @Valid @ModelAttribute("bond") Bond bond,
        BindingResult bindingResult,
        Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("isEdit", true);
            populateEnums(model);
            return "bonds/form";
        }

        try {
            bondService.update(id, bond);
        } catch (RuntimeException ex) {
            bindingResult.reject("bond.error", ex.getMessage());
            model.addAttribute("isEdit", true);
            populateEnums(model);
            return "bonds/form";
        }

        return "redirect:/bonds";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        bondService.delete(id);
        return "redirect:/bonds";
    }

    private void populateEnums(Model model) {
        model.addAttribute("bondTypes", Bond.BondType.values());
        model.addAttribute("couponTypes", Bond.CouponType.values());
        model.addAttribute("couponFrequencies", Bond.CouponFrequency.values());
        model.addAttribute("dayCountConventions", Bond.DayCountConvention.values());
        model.addAttribute("redemptionTypes", Bond.RedemptionType.values());
        model.addAttribute("seniorityTypes", Bond.Seniority.values());
        model.addAttribute("tradingStatuses", Bond.TradingStatus.values());
    }

    private Bond getOr404(Long id) {
        try {
            return bondService.findById(id);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(NOT_FOUND, ex.getMessage(), ex);
        }
    }
}
