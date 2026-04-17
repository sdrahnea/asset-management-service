package com.sdr.ams.controller;
import java.time.LocalDate;
import java.util.List;
import com.sdr.ams.model.tangible.RealEstate;
import com.sdr.ams.service.ExportService;
import com.sdr.ams.service.RealEstateService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
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
@RequestMapping("/real-estates")
public class RealEstateController {
    private static final List<String> UTILITY_OPTIONS = List.of("Electricity", "Water", "Gas", "Sewage", "Internet");
    private static final List<String> AMENITY_OPTIONS = List.of("Elevator", "Storage", "Security", "Garden", "Pool");
    private final RealEstateService realEstateService;
    private final ExportService exportService;
    public RealEstateController(RealEstateService realEstateService, ExportService exportService) {
        this.realEstateService = realEstateService;
        this.exportService = exportService;
    }
    @GetMapping
    public String list(
        @RequestParam(required = false) RealEstate.PropertyType propertyType,
        @RequestParam(required = false) RealEstate.OwnershipType ownershipType,
        @RequestParam(required = false) RealEstate.NeighborhoodType neighborhoodType,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate valuationDate,
        @RequestParam(required = false) RealEstate.MaintenanceStatus maintenanceStatus,
        Model model
    ) {
        model.addAttribute(
            "realEstates",
            realEstateService.findAll(propertyType, ownershipType, neighborhoodType, valuationDate, maintenanceStatus)
        );
        model.addAttribute("selectedPropertyType", propertyType);
        model.addAttribute("selectedOwnershipType", ownershipType);
        model.addAttribute("selectedNeighborhoodType", neighborhoodType);
        model.addAttribute("selectedValuationDate", valuationDate);
        model.addAttribute("selectedMaintenanceStatus", maintenanceStatus);
        populateOptions(model);
        return "real-estates/list";
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> export(
        @RequestParam(defaultValue = "csv") String format,
        @RequestParam(required = false) RealEstate.PropertyType propertyType,
        @RequestParam(required = false) RealEstate.OwnershipType ownershipType,
        @RequestParam(required = false) RealEstate.NeighborhoodType neighborhoodType,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate valuationDate,
        @RequestParam(required = false) RealEstate.MaintenanceStatus maintenanceStatus
    ) throws Exception {
        var items = realEstateService.findAll(propertyType, ownershipType, neighborhoodType, valuationDate, maintenanceStatus);
        if ("excel".equalsIgnoreCase(format) || "xlsx".equalsIgnoreCase(format)) {
            byte[] data = exportService.toExcel(items, "RealEstate");
            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"real-estates.xlsx\"")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(data);
        }

        byte[] data = exportService.toCsv(items, "RealEstate");
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"real-estates.csv\"")
            .contentType(MediaType.parseMediaType("text/csv; charset=UTF-8"))
            .body(data);
    }
    @GetMapping("/new")
    public String createForm(Model model) {
        RealEstate realEstate = new RealEstate();
        realEstate.setValuationDate(LocalDate.now());
        realEstate.setDataSource("manual-entry");
        realEstate.setMaintenanceStatus(RealEstate.MaintenanceStatus.UNKNOWN);
        model.addAttribute("realEstate", realEstate);
        model.addAttribute("isEdit", false);
        populateOptions(model);
        return "real-estates/form";
    }
    @PostMapping
    public String create(
        @Valid @ModelAttribute("realEstate") RealEstate realEstate,
        BindingResult bindingResult,
        Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("isEdit", false);
            populateOptions(model);
            return "real-estates/form";
        }
        try {
            realEstateService.create(realEstate);
        } catch (RuntimeException ex) {
            bindingResult.reject("realEstate.error", ex.getMessage());
            model.addAttribute("isEdit", false);
            populateOptions(model);
            return "real-estates/form";
        }
        return "redirect:/real-estates";
    }
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("realEstate", getOr404(id));
        return "real-estates/detail";
    }
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("realEstate", getOr404(id));
        model.addAttribute("isEdit", true);
        populateOptions(model);
        return "real-estates/form";
    }
    @PostMapping(path = "/{id}")
    public String update(
        @PathVariable Long id,
        @Valid @ModelAttribute("realEstate") RealEstate realEstate,
        BindingResult bindingResult,
        Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("isEdit", true);
            populateOptions(model);
            return "real-estates/form";
        }
        try {
            realEstateService.update(id, realEstate);
        } catch (RuntimeException ex) {
            bindingResult.reject("realEstate.error", ex.getMessage());
            model.addAttribute("isEdit", true);
            populateOptions(model);
            return "real-estates/form";
        }
        return "redirect:/real-estates";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        realEstateService.delete(id);
        return "redirect:/bank-accounts";
    }

    private void populateOptions(Model model) {
        model.addAttribute("propertyTypes", RealEstate.PropertyType.values());
        model.addAttribute("constructionTypes", RealEstate.ConstructionType.values());
        model.addAttribute("conditions", RealEstate.Condition.values());
        model.addAttribute("zoningTypes", RealEstate.ZoningType.values());
        model.addAttribute("landShapes", RealEstate.LandShape.values());
        model.addAttribute("topographies", RealEstate.Topography.values());
        model.addAttribute("accessRoadTypes", RealEstate.AccessRoadType.values());
        model.addAttribute("ownershipTypes", RealEstate.OwnershipType.values());
        model.addAttribute("heatingTypes", RealEstate.HeatingType.values());
        model.addAttribute("energyEfficiencyClasses", RealEstate.EnergyEfficiencyClass.values());
        model.addAttribute("neighborhoodTypes", RealEstate.NeighborhoodType.values());
        model.addAttribute("maintenanceStatuses", RealEstate.MaintenanceStatus.values());
        model.addAttribute("utilityOptions", UTILITY_OPTIONS);
        model.addAttribute("amenityOptions", AMENITY_OPTIONS);
    }
    private RealEstate getOr404(Long id) {
        try {
            return realEstateService.findById(id);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(NOT_FOUND, ex.getMessage(), ex);
        }
    }
}
