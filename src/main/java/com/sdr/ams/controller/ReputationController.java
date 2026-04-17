package com.sdr.ams.controller;

import com.sdr.ams.model.intangible.Reputation;
import com.sdr.ams.service.ExportService;
import com.sdr.ams.service.ReputationService;
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
@RequestMapping("/reputations")
public class ReputationController {

    private final ReputationService reputationService;
    private final ExportService exportService;

    public ReputationController(ReputationService reputationService, ExportService exportService) {
        this.reputationService = reputationService;
        this.exportService = exportService;
    }

    @GetMapping
    public String list(
        @RequestParam(required = false) String entityId,
        @RequestParam(required = false) Reputation.EntityType entityType,
        @RequestParam(required = false) Reputation.TrendDirection trendDirection,
        @RequestParam(required = false) Reputation.CompetitivePosition competitivePosition,
        Model model
    ) {
        model.addAttribute("reputations", reputationService.findAll(entityId, entityType, trendDirection, competitivePosition));
        model.addAttribute("selectedEntityId", entityId);
        model.addAttribute("selectedEntityType", entityType);
        model.addAttribute("selectedTrendDirection", trendDirection);
        model.addAttribute("selectedCompetitivePosition", competitivePosition);
        populateEnums(model);
        return "reputations/list";
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> export(
        @RequestParam(defaultValue = "csv") String format,
        @RequestParam(required = false) String entityId,
        @RequestParam(required = false) Reputation.EntityType entityType,
        @RequestParam(required = false) Reputation.TrendDirection trendDirection,
        @RequestParam(required = false) Reputation.CompetitivePosition competitivePosition
    ) throws Exception {
        var items = reputationService.findAll(entityId, entityType, trendDirection, competitivePosition);
        if ("excel".equalsIgnoreCase(format) || "xlsx".equalsIgnoreCase(format)) {
            byte[] data = exportService.toExcel(items, "Reputation");
            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"reputations.xlsx\"")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(data);
        }

        byte[] data = exportService.toCsv(items, "Reputation");
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"reputations.csv\"")
            .contentType(MediaType.parseMediaType("text/csv; charset=UTF-8"))
            .body(data);
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        Reputation reputation = new Reputation();
        reputation.setDataSource("manual-entry");
        reputation.setEntityType(Reputation.EntityType.COMPANY);
        reputation.setTrendDirection(Reputation.TrendDirection.STABLE);
        model.addAttribute("reputation", reputation);
        model.addAttribute("isEdit", false);
        populateEnums(model);
        return "reputations/form";
    }

    @PostMapping
    public String create(
        @Valid @ModelAttribute("reputation") Reputation reputation,
        BindingResult bindingResult,
        Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("isEdit", false);
            populateEnums(model);
            return "reputations/form";
        }
        try {
            reputationService.create(reputation);
        } catch (RuntimeException ex) {
            bindingResult.reject("reputation.error", ex.getMessage());
            model.addAttribute("isEdit", false);
            populateEnums(model);
            return "reputations/form";
        }
        return "redirect:/reputations";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("reputation", getOr404(id));
        return "reputations/detail";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("reputation", getOr404(id));
        model.addAttribute("isEdit", true);
        populateEnums(model);
        return "reputations/form";
    }

    @PostMapping("/{id}")
    public String update(
        @PathVariable Long id,
        @Valid @ModelAttribute("reputation") Reputation reputation,
        BindingResult bindingResult,
        Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("isEdit", true);
            populateEnums(model);
            return "reputations/form";
        }
        try {
            reputationService.update(id, reputation);
        } catch (RuntimeException ex) {
            bindingResult.reject("reputation.error", ex.getMessage());
            model.addAttribute("isEdit", true);
            populateEnums(model);
            return "reputations/form";
        }
        return "redirect:/reputations";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        reputationService.delete(id);
        return "redirect:/reputations";
    }

    private void populateEnums(Model model) {
        model.addAttribute("entityTypes", Reputation.EntityType.values());
        model.addAttribute("geographicScopes", Reputation.GeographicScope.values());
        model.addAttribute("mediaCoverageTones", Reputation.MediaCoverageTone.values());
        model.addAttribute("eventTypes", Reputation.EventType.values());
        model.addAttribute("trendDirections", Reputation.TrendDirection.values());
        model.addAttribute("competitivePositions", Reputation.CompetitivePosition.values());
        model.addAttribute("regulatoryEnvironments", Reputation.RegulatoryEnvironment.values());
    }

    private Reputation getOr404(Long id) {
        try {
            return reputationService.findById(id);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(NOT_FOUND, ex.getMessage(), ex);
        }
    }
}
