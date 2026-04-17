package com.sdr.ams.controller;

import com.sdr.ams.model.intangible.Trademark;
import com.sdr.ams.service.DetailPdfReportService;
import com.sdr.ams.service.ExportService;
import com.sdr.ams.service.TrademarkService;
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

import java.time.LocalDate;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
@RequestMapping("/trademarks")
public class TrademarkController {

    private final TrademarkService trademarkService;
    private final ExportService exportService;
    private final DetailPdfReportService detailPdfReportService;

    public TrademarkController(
        TrademarkService trademarkService,
        ExportService exportService,
        DetailPdfReportService detailPdfReportService
    ) {
        this.trademarkService = trademarkService;
        this.exportService = exportService;
        this.detailPdfReportService = detailPdfReportService;
    }

    @GetMapping
    public String list(
        @RequestParam(required = false) Trademark.MarkType markType,
        @RequestParam(required = false) Trademark.LegalStatus legalStatus,
        @RequestParam(required = false) Trademark.OwnerType ownerType,
        @RequestParam(required = false) Trademark.LicensingStatus licensingStatus,
        Model model
    ) {
        model.addAttribute("trademarks", trademarkService.findAll(markType, legalStatus, ownerType, licensingStatus));
        model.addAttribute("selectedMarkType", markType);
        model.addAttribute("selectedLegalStatus", legalStatus);
        model.addAttribute("selectedOwnerType", ownerType);
        model.addAttribute("selectedLicensingStatus", licensingStatus);
        populateEnums(model);
        return "trademarks/list";
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> export(
        @RequestParam(defaultValue = "csv") String format,
        @RequestParam(required = false) Trademark.MarkType markType,
        @RequestParam(required = false) Trademark.LegalStatus legalStatus,
        @RequestParam(required = false) Trademark.OwnerType ownerType,
        @RequestParam(required = false) Trademark.LicensingStatus licensingStatus
    ) throws Exception {
        var items = trademarkService.findAll(markType, legalStatus, ownerType, licensingStatus);
        if ("excel".equalsIgnoreCase(format) || "xlsx".equalsIgnoreCase(format)) {
            byte[] data = exportService.toExcel(items, "Trademark");
            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"trademarks.xlsx\"")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(data);
        }

        byte[] data = exportService.toCsv(items, "Trademark");
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"trademarks.csv\"")
            .contentType(MediaType.parseMediaType("text/csv; charset=UTF-8"))
            .body(data);
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        Trademark trademark = new Trademark();
        trademark.setDataSource("manual-entry");
        trademark.setMarkType(Trademark.MarkType.WORDMARK);
        trademark.setOwnerType(Trademark.OwnerType.CORPORATION);
        trademark.setLegalStatus(Trademark.LegalStatus.FILED);
        trademark.setLicensingStatus(Trademark.LicensingStatus.NOT_LICENSED);
        trademark.setApplicationDate(LocalDate.now());
        model.addAttribute("trademark", trademark);
        model.addAttribute("isEdit", false);
        populateEnums(model);
        return "trademarks/form";
    }

    @PostMapping
    public String create(
        @Valid @ModelAttribute("trademark") Trademark trademark,
        BindingResult bindingResult,
        Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("isEdit", false);
            populateEnums(model);
            return "trademarks/form";
        }
        try {
            trademarkService.create(trademark);
        } catch (RuntimeException ex) {
            bindingResult.reject("trademark.error", ex.getMessage());
            model.addAttribute("isEdit", false);
            populateEnums(model);
            return "trademarks/form";
        }
        return "redirect:/trademarks";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("trademark", getOr404(id));
        return "trademarks/detail";
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> detailPdf(@PathVariable Long id) {
        Trademark trademark = getOr404(id);
        byte[] data = detailPdfReportService.buildEntityDetailPdf("Trademark", trademark);
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"trademark-" + id + ".pdf\"")
            .contentType(MediaType.APPLICATION_PDF)
            .body(data);
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("trademark", getOr404(id));
        model.addAttribute("isEdit", true);
        populateEnums(model);
        return "trademarks/form";
    }

    @PostMapping("/{id}")
    public String update(
        @PathVariable Long id,
        @Valid @ModelAttribute("trademark") Trademark trademark,
        BindingResult bindingResult,
        Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("isEdit", true);
            populateEnums(model);
            return "trademarks/form";
        }
        try {
            trademarkService.update(id, trademark);
        } catch (RuntimeException ex) {
            bindingResult.reject("trademark.error", ex.getMessage());
            model.addAttribute("isEdit", true);
            populateEnums(model);
            return "trademarks/form";
        }
        return "redirect:/trademarks";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        trademarkService.delete(id);
        return "redirect:/trademarks";
    }

    private void populateEnums(Model model) {
        model.addAttribute("markTypes", Trademark.MarkType.values());
        model.addAttribute("ownerTypes", Trademark.OwnerType.values());
        model.addAttribute("legalStatuses", Trademark.LegalStatus.values());
        model.addAttribute("internationalFilingSystems", Trademark.InternationalFilingSystem.values());
        model.addAttribute("usageStatuses", Trademark.UsageStatus.values());
        model.addAttribute("licensingStatuses", Trademark.LicensingStatus.values());
        model.addAttribute("distinctivenessScores", Trademark.DistinctivenessScore.values());
        model.addAttribute("commercialImportances", Trademark.CommercialImportance.values());
    }

    private Trademark getOr404(Long id) {
        try {
            return trademarkService.findById(id);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(NOT_FOUND, ex.getMessage(), ex);
        }
    }
}
