package com.sdr.ams.controller;

import com.sdr.ams.model.tangible.Vehicle;
import com.sdr.ams.service.DetailPdfReportService;
import com.sdr.ams.service.ExportService;
import com.sdr.ams.service.VehicleService;
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
@RequestMapping("/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;
    private final ExportService exportService;
    private final DetailPdfReportService detailPdfReportService;

    public VehicleController(
        VehicleService vehicleService,
        ExportService exportService,
        DetailPdfReportService detailPdfReportService
    ) {
        this.vehicleService = vehicleService;
        this.exportService = exportService;
        this.detailPdfReportService = detailPdfReportService;
    }

    @GetMapping
    public String list(
        @RequestParam(required = false) Vehicle.VehicleType vehicleType,
        @RequestParam(required = false) Vehicle.RegistrationStatus registrationStatus,
        @RequestParam(required = false) Vehicle.OwnershipType ownershipType,
        Model model
    ) {
        model.addAttribute("vehicles", vehicleService.findAll(vehicleType, registrationStatus, ownershipType));
        model.addAttribute("selectedVehicleType", vehicleType);
        model.addAttribute("selectedRegistrationStatus", registrationStatus);
        model.addAttribute("selectedOwnershipType", ownershipType);
        populateEnums(model);
        return "vehicles/list";
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> export(
        @RequestParam(defaultValue = "csv") String format,
        @RequestParam(required = false) Vehicle.VehicleType vehicleType,
        @RequestParam(required = false) Vehicle.RegistrationStatus registrationStatus,
        @RequestParam(required = false) Vehicle.OwnershipType ownershipType
    ) throws Exception {
        var items = vehicleService.findAll(vehicleType, registrationStatus, ownershipType);
        if ("excel".equalsIgnoreCase(format) || "xlsx".equalsIgnoreCase(format)) {
            byte[] data = exportService.toExcel(items, "Vehicle");
            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"vehicles.xlsx\"")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(data);
        }

        byte[] data = exportService.toCsv(items, "Vehicle");
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"vehicles.csv\"")
            .contentType(MediaType.parseMediaType("text/csv; charset=UTF-8"))
            .body(data);
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        Vehicle vehicle = new Vehicle();
        vehicle.setYearOfManufacture(LocalDate.now().getYear());
        vehicle.setDataSource("manual-entry");
        vehicle.setVehicleType(Vehicle.VehicleType.CAR);
        vehicle.setEngineType(Vehicle.EngineType.PETROL);
        vehicle.setRegistrationStatus(Vehicle.RegistrationStatus.ACTIVE);
        vehicle.setOwnershipType(Vehicle.OwnershipType.COMPANY);
        model.addAttribute("vehicle", vehicle);
        model.addAttribute("isEdit", false);
        populateEnums(model);
        return "vehicles/form";
    }

    @PostMapping
    public String create(
        @Valid @ModelAttribute("vehicle") Vehicle vehicle,
        BindingResult bindingResult,
        Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("isEdit", false);
            populateEnums(model);
            return "vehicles/form";
        }
        try {
            vehicleService.create(vehicle);
        } catch (RuntimeException ex) {
            bindingResult.reject("vehicle.error", ex.getMessage());
            model.addAttribute("isEdit", false);
            populateEnums(model);
            return "vehicles/form";
        }
        return "redirect:/vehicles";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("vehicle", getOr404(id));
        return "vehicles/detail";
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> detailPdf(@PathVariable Long id) {
        Vehicle vehicle = getOr404(id);
        byte[] data = detailPdfReportService.buildEntityDetailPdf("Vehicle", vehicle);
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"vehicle-" + id + ".pdf\"")
            .contentType(MediaType.APPLICATION_PDF)
            .body(data);
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("vehicle", getOr404(id));
        model.addAttribute("isEdit", true);
        populateEnums(model);
        return "vehicles/form";
    }

    @PostMapping("/{id}")
    public String update(
        @PathVariable Long id,
        @Valid @ModelAttribute("vehicle") Vehicle vehicle,
        BindingResult bindingResult,
        Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("isEdit", true);
            populateEnums(model);
            return "vehicles/form";
        }
        try {
            vehicleService.update(id, vehicle);
        } catch (RuntimeException ex) {
            bindingResult.reject("vehicle.error", ex.getMessage());
            model.addAttribute("isEdit", true);
            populateEnums(model);
            return "vehicles/form";
        }
        return "redirect:/vehicles";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        vehicleService.delete(id);
        return "redirect:/vehicles";
    }

    private void populateEnums(Model model) {
        model.addAttribute("vehicleTypes", Vehicle.VehicleType.values());
        model.addAttribute("engineTypes", Vehicle.EngineType.values());
        model.addAttribute("transmissions", Vehicle.Transmission.values());
        model.addAttribute("drivetrains", Vehicle.Drivetrain.values());
        model.addAttribute("registrationStatuses", Vehicle.RegistrationStatus.values());
        model.addAttribute("verificationStatuses", Vehicle.VerificationStatus.values());
        model.addAttribute("taxStatuses", Vehicle.TaxStatus.values());
        model.addAttribute("ownershipTypes", Vehicle.OwnershipType.values());
        model.addAttribute("usageCategories", Vehicle.UsageCategory.values());
        model.addAttribute("operatingRegions", Vehicle.OperatingRegion.values());
        model.addAttribute("conditionStatuses", Vehicle.ConditionStatus.values());
        model.addAttribute("maintenanceSchedules", Vehicle.MaintenanceSchedule.values());
        model.addAttribute("depreciationMethods", Vehicle.DepreciationMethod.values());
    }

    private Vehicle getOr404(Long id) {
        try {
            return vehicleService.findById(id);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(NOT_FOUND, ex.getMessage(), ex);
        }
    }
}
