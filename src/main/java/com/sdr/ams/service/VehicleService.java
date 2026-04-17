package com.sdr.ams.service;

import com.sdr.ams.model.tangible.Vehicle;
import com.sdr.ams.repository.VehicleRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.Locale;

@Service
@Transactional
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Transactional(readOnly = true)
    public List<Vehicle> findAll() {
        return vehicleRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Vehicle> findAll(
        Vehicle.VehicleType vehicleType,
        Vehicle.RegistrationStatus registrationStatus,
        Vehicle.OwnershipType ownershipType
    ) {
        return vehicleRepository.search(vehicleType, registrationStatus, ownershipType);
    }

    @Transactional(readOnly = true)
    public Vehicle findById(Long id) {
        return vehicleRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Vehicle not found for id: " + id));
    }

    public Vehicle create(Vehicle vehicle) {
        normalizeAndValidate(vehicle, null);
        return vehicleRepository.save(vehicle);
    }

    public Vehicle update(Long id, Vehicle input) {
        Vehicle existing = findById(id);
        BeanUtils.copyProperties(input, existing, "id", "createdAt", "createdBy", "updatedAt", "updatedBy");
        normalizeAndValidate(existing, id);
        return vehicleRepository.save(existing);
    }

    public void delete(Long id) {
        vehicleRepository.deleteById(id);
    }

    private void normalizeAndValidate(Vehicle vehicle, Long excludeId) {
        vehicle.setVehicleId(normalizeIdentifier(vehicle.getVehicleId()));
        vehicle.setVin(normalizeIdentifier(vehicle.getVin()));
        vehicle.setLicensePlate(normalizeIdentifier(vehicle.getLicensePlate()));
        vehicle.setRegistrationCountry(normalizeIdentifier(vehicle.getRegistrationCountry()));

        vehicle.setMake(trimToNull(vehicle.getMake()));
        vehicle.setModel(trimToNull(vehicle.getModel()));
        vehicle.setTrimVariant(trimToNull(vehicle.getTrimVariant()));
        vehicle.setBodyType(trimToNull(vehicle.getBodyType()));
        vehicle.setColor(trimToNull(vehicle.getColor()));
        vehicle.setEngineCapacity(trimToNull(vehicle.getEngineCapacity()));
        vehicle.setPowerOutput(trimToNull(vehicle.getPowerOutput()));
        vehicle.setTorque(trimToNull(vehicle.getTorque()));
        vehicle.setFuelConsumption(trimToNull(vehicle.getFuelConsumption()));
        vehicle.setBatteryCapacity(trimToNull(vehicle.getBatteryCapacity()));
        vehicle.setEmissionStandard(trimToNull(vehicle.getEmissionStandard()));
        vehicle.setDimensions(trimToNull(vehicle.getDimensions()));

        vehicle.setHomologationDocuments(trimToNull(vehicle.getHomologationDocuments()));
        vehicle.setComplianceCertificates(trimToNull(vehicle.getComplianceCertificates()));
        vehicle.setOwnerName(trimToNull(vehicle.getOwnerName()));
        vehicle.setLeasingDetails(trimToNull(vehicle.getLeasingDetails()));
        vehicle.setDriverAssignments(trimToNull(vehicle.getDriverAssignments()));
        vehicle.setServiceHistory(trimToNull(vehicle.getServiceHistory()));
        vehicle.setAccidentHistory(trimToNull(vehicle.getAccidentHistory()));
        vehicle.setFaultCodes(trimToNull(vehicle.getFaultCodes()));
        vehicle.setWarrantyStatus(trimToNull(vehicle.getWarrantyStatus()));
        vehicle.setComplianceFlags(trimToNull(vehicle.getComplianceFlags()));

        vehicle.setRegistrationDocuments(trimToNull(vehicle.getRegistrationDocuments()));
        vehicle.setInsuranceDocuments(trimToNull(vehicle.getInsuranceDocuments()));
        vehicle.setInspectionReports(trimToNull(vehicle.getInspectionReports()));
        vehicle.setServiceInvoices(trimToNull(vehicle.getServiceInvoices()));
        vehicle.setAccidentReports(trimToNull(vehicle.getAccidentReports()));
        vehicle.setPhotos(trimToNull(vehicle.getPhotos()));
        vehicle.setInternalNotes(trimToNull(vehicle.getInternalNotes()));

        vehicle.setDataSource(trimToNull(vehicle.getDataSource()));
        vehicle.setResponsibleAgent(trimToNull(vehicle.getResponsibleAgent()));
        vehicle.setNotes(trimToNull(vehicle.getNotes()));

        validateMandatoryFields(vehicle);
        validateBusinessRules(vehicle);
        validateUniqueness(vehicle, excludeId);
    }

    private void validateMandatoryFields(Vehicle vehicle) {
        requireText(vehicle.getVehicleId(), "Vehicle ID is required");
        requireText(vehicle.getVin(), "VIN is required");
        requireText(vehicle.getLicensePlate(), "License plate is required");
        requireText(vehicle.getMake(), "Make is required");
        requireText(vehicle.getModel(), "Model is required");
        requireText(vehicle.getDataSource(), "Data source is required");

        if (vehicle.getVehicleType() == null) {
            throw new IllegalArgumentException("Vehicle type is required");
        }
        if (vehicle.getEngineType() == null) {
            throw new IllegalArgumentException("Engine type is required");
        }
        if (vehicle.getYearOfManufacture() == null) {
            throw new IllegalArgumentException("Year of manufacture is required");
        }
    }

    private void validateBusinessRules(Vehicle vehicle) {
        int currentYear = Year.now().getValue();
        if (vehicle.getYearOfManufacture() != null && vehicle.getYearOfManufacture() > currentYear) {
            throw new IllegalArgumentException("Year of manufacture must be less than or equal to the current year");
        }

        if (vehicle.getRegistrationDate() != null && vehicle.getRegistrationDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Registration date must be today or earlier");
        }
        if (vehicle.getPurchaseDate() != null && vehicle.getPurchaseDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Purchase date must be today or earlier");
        }
        if (vehicle.getValuationDate() != null && vehicle.getValuationDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Valuation date must be today or earlier");
        }

        if (vehicle.getLastServiceDate() != null
            && vehicle.getNextServiceDue() != null
            && vehicle.getNextServiceDue().isBefore(vehicle.getLastServiceDate())) {
            throw new IllegalArgumentException("Next service due must be greater than or equal to last service date");
        }
    }

    private void validateUniqueness(Vehicle vehicle, Long excludeId) {
        if (vehicleRepository.existsVehicleId(vehicle.getVehicleId(), excludeId)) {
            throw new IllegalArgumentException("Vehicle ID must be unique");
        }
        if (vehicleRepository.existsVin(vehicle.getVin(), excludeId)) {
            throw new IllegalArgumentException("VIN must be unique");
        }
        if (vehicleRepository.existsLicensePlateInCountry(
            vehicle.getLicensePlate(),
            vehicle.getRegistrationCountry(),
            excludeId
        )) {
            throw new IllegalArgumentException("License plate must be unique per registration country");
        }
    }

    private String normalizeIdentifier(String value) {
        String trimmed = trimToNull(value);
        if (trimmed == null) {
            return null;
        }
        return trimmed.replaceAll("\\s+", "").toUpperCase(Locale.ROOT);
    }

    private String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private void requireText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
    }
}
