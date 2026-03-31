package com.sdr.ams.service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Year;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import com.sdr.ams.model.tangible.RealEstate;
import com.sdr.ams.repository.RealEstateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional
public class RealEstateService {
    private final RealEstateRepository realEstateRepository;
    public RealEstateService(RealEstateRepository realEstateRepository) {
        this.realEstateRepository = realEstateRepository;
    }
    @Transactional(readOnly = true)
    public List<RealEstate> findAll(
        RealEstate.PropertyType propertyType,
        RealEstate.OwnershipType ownershipType,
        RealEstate.NeighborhoodType neighborhoodType,
        LocalDate valuationDate,
        RealEstate.MaintenanceStatus maintenanceStatus
    ) {
        return realEstateRepository.search(propertyType, ownershipType, neighborhoodType, valuationDate, maintenanceStatus);
    }
    @Transactional(readOnly = true)
    public List<RealEstate> findAll() {
        return realEstateRepository.findAll();
    }
    @Transactional(readOnly = true)
    public RealEstate findById(Long id) {
        return realEstateRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("RealEstate not found for id: " + id));
    }
    public RealEstate create(

            RealEstate realEstate) {
        normalizeAndValidate(realEstate, null);
        return realEstateRepository.save(realEstate);
    }
    public RealEstate update(Long id, RealEstate input) {
        RealEstate existing = findById(id);
        copyFields(existing, input);
        normalizeAndValidate(existing, id);
        return realEstateRepository.save(existing);
    }

    public void delete(Long id) {
        realEstateRepository.deleteById(id);
    }

    private void normalizeAndValidate(RealEstate realEstate, Long excludeId) {
        realEstate.setTitle(trimToNull(realEstate.getTitle()));
        realEstate.setSubType(trimToNull(realEstate.getSubType()));
        realEstate.setDescription(trimToNull(realEstate.getDescription()));
        realEstate.setAddress(trimToNull(realEstate.getAddress()));
        realEstate.setParking(trimToNull(realEstate.getParking()));
        realEstate.setEncumbrances(trimToNull(realEstate.getEncumbrances()));
        realEstate.setBuildingPermits(trimToNull(realEstate.getBuildingPermits()));
        realEstate.setUsageRestrictions(trimToNull(realEstate.getUsageRestrictions()));
        realEstate.setOwnerName(trimToNull(realEstate.getOwnerName()));
        realEstate.setDepreciationMethod(trimToNull(realEstate.getDepreciationMethod()));
        realEstate.setProximityToTransport(trimToNull(realEstate.getProximityToTransport()));
        realEstate.setNearbyFacilities(trimToNull(realEstate.getNearbyFacilities()));
        realEstate.setMarketTrends(trimToNull(realEstate.getMarketTrends()));
        realEstate.setEnvironmentalRisks(trimToNull(realEstate.getEnvironmentalRisks()));
        realEstate.setRenovationDetails(trimToNull(realEstate.getRenovationDetails()));
        realEstate.setStructuralIssues(trimToNull(realEstate.getStructuralIssues()));
        realEstate.setComplianceFlags(trimToNull(realEstate.getComplianceFlags()));
        realEstate.setDataSource(trimToNull(realEstate.getDataSource()));
        realEstate.setResponsibleAgent(trimToNull(realEstate.getResponsibleAgent()));
        realEstate.setTags(trimToNull(realEstate.getTags()));
        realEstate.setNotes(trimToNull(realEstate.getNotes()));
        realEstate.setCadastralNumber(normalizeIdentifier(realEstate.getCadastralNumber()));
        realEstate.setLandRegistryNumber(normalizeIdentifier(realEstate.getLandRegistryNumber()));
        realEstate.setUtilitiesAvailability(normalizeSet(realEstate.getUtilitiesAvailability()));
        realEstate.setAmenities(normalizeSet(realEstate.getAmenities()));
        validateMandatoryFields(realEstate);
        validateBusinessRules(realEstate);
        calculateYieldIndicators(realEstate);
        validateUniqueness(realEstate, excludeId);
    }
    private void validateMandatoryFields(RealEstate realEstate) {
        requireText(realEstate.getTitle(), "Title is required");
        requireText(realEstate.getAddress(), "Address is required");
        requireText(realEstate.getOwnerName(), "Owner name is required");
        requireText(realEstate.getCadastralNumber(), "Cadastral number is required");
        requireText(realEstate.getLandRegistryNumber(), "Land registry number is required");
        requireText(realEstate.getDataSource(), "Data source is required");
        if (realEstate.getPropertyType() == null) {
            throw new IllegalArgumentException("Property type is required");
        }
        if (realEstate.getOwnershipType() == null) {
            throw new IllegalArgumentException("Ownership type is required");
        }
        if (realEstate.getLandArea() == null) {
            throw new IllegalArgumentException("Land area is required");
        }
        if (realEstate.getCurrentMarketValue() == null) {
            throw new IllegalArgumentException("Current market value is required");
        }
        if (realEstate.getValuationDate() == null) {
            throw new IllegalArgumentException("Valuation date is required");
        }
    }
    private void validateBusinessRules(RealEstate realEstate) {
        if (!realEstate.getCadastralNumber().matches("^[A-Z0-9]+$")) {
            throw new IllegalArgumentException("Cadastral number must be alphanumeric");
        }
        if (!realEstate.getLandRegistryNumber().matches("^[A-Z0-9]+$")) {
            throw new IllegalArgumentException("Land registry number must be alphanumeric");
        }
        if ((realEstate.getLatitude() == null) != (realEstate.getLongitude() == null)) {
            throw new IllegalArgumentException("Latitude and longitude must both be provided together");
        }
        if (realEstate.getYearBuilt() != null && realEstate.getYearBuilt() > Year.now().getValue()) {
            throw new IllegalArgumentException("Year built must be less than or equal to the current year");
        }
        if (realEstate.getValuationDate() != null && realEstate.getValuationDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Valuation date must be today or earlier");
        }
        if (realEstate.getLastRenovationDate() != null && realEstate.getLastRenovationDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Last renovation date must be today or earlier");
        }
        if (realEstate.getBuiltArea() != null && realEstate.getUsableArea() != null
            && realEstate.getBuiltArea().compareTo(realEstate.getUsableArea()) < 0) {
            throw new IllegalArgumentException("Built area must be greater than or equal to usable area");
        }
    }
    private void calculateYieldIndicators(RealEstate realEstate) {
        if (realEstate.getPurchasePrice() != null
            && realEstate.getRentalIncome() != null
            && realEstate.getPurchasePrice().compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal yield = realEstate.getRentalIncome()
                .multiply(BigDecimal.valueOf(100))
                .divide(realEstate.getPurchasePrice(), 2, RoundingMode.HALF_UP);
            realEstate.setYieldIndicators(yield);
        }
    }
    private void validateUniqueness(RealEstate realEstate, Long excludeId) {
        if (realEstateRepository.existsCadastralNumber(realEstate.getCadastralNumber(), excludeId)) {
            throw new IllegalArgumentException("Cadastral number must be unique");
        }
        if (realEstateRepository.existsLandRegistryNumber(realEstate.getLandRegistryNumber(), excludeId)) {
            throw new IllegalArgumentException("Land registry number must be unique");
        }
    }
    private void copyFields(RealEstate target, RealEstate source) {
        target.setTitle(source.getTitle());
        target.setPropertyType(source.getPropertyType());
        target.setSubType(source.getSubType());
        target.setDescription(source.getDescription());
        target.setYearBuilt(source.getYearBuilt());
        target.setConstructionType(source.getConstructionType());
        target.setCondition(source.getCondition());
        target.setAddress(source.getAddress());
        target.setLatitude(source.getLatitude());
        target.setLongitude(source.getLongitude());
        target.setZoningType(source.getZoningType());
        target.setLandArea(source.getLandArea());
        target.setLandShape(source.getLandShape());
        target.setTopography(source.getTopography());
        target.setAccessRoadType(source.getAccessRoadType());
        target.setUtilitiesAvailability(source.getUtilitiesAvailability());
        target.setBuiltArea(source.getBuiltArea());
        target.setUsableArea(source.getUsableArea());
        target.setFloors(source.getFloors());
        target.setRooms(source.getRooms());
        target.setBathrooms(source.getBathrooms());
        target.setBalconies(source.getBalconies());
        target.setHeatingType(source.getHeatingType());
        target.setEnergyEfficiencyClass(source.getEnergyEfficiencyClass());
        target.setParking(source.getParking());
        target.setAmenities(source.getAmenities());
        target.setEncumbrances(source.getEncumbrances());
        target.setBuildingPermits(source.getBuildingPermits());
        target.setUsageRestrictions(source.getUsageRestrictions());
        target.setOwnershipType(source.getOwnershipType());
        target.setOwnerName(source.getOwnerName());
        target.setCadastralNumber(source.getCadastralNumber());
        target.setLandRegistryNumber(source.getLandRegistryNumber());
        target.setCurrentMarketValue(source.getCurrentMarketValue());
        target.setValuationDate(source.getValuationDate());
        target.setPurchasePrice(source.getPurchasePrice());
        target.setRentalIncome(source.getRentalIncome());
        target.setOperatingCosts(source.getOperatingCosts());
        target.setTaxes(source.getTaxes());
        target.setInsuranceValue(source.getInsuranceValue());
        target.setYieldIndicators(source.getYieldIndicators());
        target.setDepreciationMethod(source.getDepreciationMethod());
        target.setNeighborhoodType(source.getNeighborhoodType());
        target.setProximityToTransport(source.getProximityToTransport());
        target.setNearbyFacilities(source.getNearbyFacilities());
        target.setMarketTrends(source.getMarketTrends());
        target.setEnvironmentalRisks(source.getEnvironmentalRisks());
        target.setMaintenanceStatus(source.getMaintenanceStatus());
        target.setLastRenovationDate(source.getLastRenovationDate());
        target.setRenovationDetails(source.getRenovationDetails());
        target.setStructuralIssues(source.getStructuralIssues());
        target.setExpectedCapex(source.getExpectedCapex());
        target.setLegalRiskScore(source.getLegalRiskScore());
        target.setMarketRiskScore(source.getMarketRiskScore());
        target.setStructuralRiskScore(source.getStructuralRiskScore());
        target.setEnvironmentalRiskScore(source.getEnvironmentalRiskScore());
        target.setOverallAssessmentScore(source.getOverallAssessmentScore());
        target.setComplianceFlags(source.getComplianceFlags());
        target.setDataSource(source.getDataSource());
        target.setResponsibleAgent(source.getResponsibleAgent());
        target.setTags(source.getTags());
        target.setNotes(source.getNotes());
    }
    private Set<String> normalizeSet(Set<String> values) {
        Set<String> normalized = new LinkedHashSet<>();
        if (values == null) {
            return normalized;
        }
        for (String value : values) {
            String trimmed = trimToNull(value);
            if (trimmed != null) {
                normalized.add(trimmed);
            }
        }
        return normalized;
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



