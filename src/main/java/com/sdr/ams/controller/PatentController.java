package com.sdr.ams.controller;

import com.sdr.ams.model.intangible.Patent;
import com.sdr.ams.service.ExportService;
import com.sdr.ams.service.PatentService;
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
@RequestMapping("/patents")
public class PatentController {

    private final PatentService patentService;
    private final ExportService exportService;

    public PatentController(PatentService patentService, ExportService exportService) {
        this.patentService = patentService;
        this.exportService = exportService;
    }

    @GetMapping
    public String list(
        @RequestParam(required = false) Patent.PatentType patentType,
        @RequestParam(required = false) Patent.LegalStatus legalStatus,
        @RequestParam(required = false) String technologyField,
        @RequestParam(required = false) String assigneeOwner,
        Model model
    ) {
        model.addAttribute("patents", patentService.findAll(patentType, legalStatus, technologyField, assigneeOwner));
        model.addAttribute("selectedPatentType", patentType);
        model.addAttribute("selectedLegalStatus", legalStatus);
        model.addAttribute("selectedTechnologyField", technologyField);
        model.addAttribute("selectedAssigneeOwner", assigneeOwner);
        populateEnums(model);
        return "patents/list";
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> export(
        @RequestParam(defaultValue = "csv") String format,
        @RequestParam(required = false) Patent.PatentType patentType,
        @RequestParam(required = false) Patent.LegalStatus legalStatus,
        @RequestParam(required = false) String technologyField,
        @RequestParam(required = false) String assigneeOwner
    ) throws Exception {
        var items = patentService.findAll(patentType, legalStatus, technologyField, assigneeOwner);
        if ("excel".equalsIgnoreCase(format) || "xlsx".equalsIgnoreCase(format)) {
            byte[] data = exportService.toExcel(items, "Patent");
            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"patents.xlsx\"")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(data);
        }

        byte[] data = exportService.toCsv(items, "Patent");
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"patents.csv\"")
            .contentType(MediaType.parseMediaType("text/csv; charset=UTF-8"))
            .body(data);
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        Patent patent = new Patent();
        patent.setDataSource("manual-entry");
        patent.setPatentType(Patent.PatentType.UTILITY);
        patent.setLegalStatus(Patent.LegalStatus.PENDING);
        patent.setRegionalSystem(Patent.RegionalSystem.NONE);
        patent.setApplicationDate(LocalDate.now());
        model.addAttribute("patent", patent);
        model.addAttribute("isEdit", false);
        populateEnums(model);
        return "patents/form";
    }

    @PostMapping
    public String create(
        @Valid @ModelAttribute("patent") Patent patent,
        BindingResult bindingResult,
        Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("isEdit", false);
            populateEnums(model);
            return "patents/form";
        }
        try {
            patentService.create(patent);
        } catch (RuntimeException ex) {
            bindingResult.reject("patent.error", ex.getMessage());
            model.addAttribute("isEdit", false);
            populateEnums(model);
            return "patents/form";
        }
        return "redirect:/patents";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("patent", getOr404(id));
        return "patents/detail";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("patent", getOr404(id));
        model.addAttribute("isEdit", true);
        populateEnums(model);
        return "patents/form";
    }

    @PostMapping("/{id}")
    public String update(
        @PathVariable Long id,
        @Valid @ModelAttribute("patent") Patent patent,
        BindingResult bindingResult,
        Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("isEdit", true);
            populateEnums(model);
            return "patents/form";
        }
        try {
            patentService.update(id, patent);
        } catch (RuntimeException ex) {
            bindingResult.reject("patent.error", ex.getMessage());
            model.addAttribute("isEdit", true);
            populateEnums(model);
            return "patents/form";
        }
        return "redirect:/patents";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        patentService.delete(id);
        return "redirect:/patents";
    }

    private void populateEnums(Model model) {
        model.addAttribute("patentTypes", Patent.PatentType.values());
        model.addAttribute("legalStatuses", Patent.LegalStatus.values());
        model.addAttribute("regionalSystems", Patent.RegionalSystem.values());
        model.addAttribute("infringementRisks", Patent.InfringementRisk.values());
        model.addAttribute("blockingPotentials", Patent.BlockingPotential.values());
    }

    private Patent getOr404(Long id) {
        try {
            return patentService.findById(id);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(NOT_FOUND, ex.getMessage(), ex);
        }
    }
}
