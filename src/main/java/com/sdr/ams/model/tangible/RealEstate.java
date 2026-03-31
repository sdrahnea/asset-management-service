package com.sdr.ams.model.tangible;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;
import java.util.LinkedHashSet;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sdr.ams.model.core.CoreEntity;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
@Entity
@Table(
    name = "real_estate",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_real_estate_cadastral_number", columnNames = "cadastral_number"),
        @UniqueConstraint(name = "uk_real_estate_land_registry_number", columnNames = "land_registry_number")
    }
)
public class RealEstate extends CoreEntity {
    public enum PropertyType {
        LAND,
        APARTMENT,
        HOUSE,
        COMMERCIAL,
        INDUSTRIAL,
        MIXED_USE
    }
    public enum ConstructionType {
        BRICK,
        CONCRETE,
        WOOD,
        STEEL
    }
    public enum Condition {
        NEW,
        RENOVATED,
        GOOD,
        NEEDS_RENOVATION,
        DERELICT
    }
    public enum ZoningType {
        RESIDENTIAL,
        COMMERCIAL,
        AGRICULTURAL,
        MIXED
    }
    public enum LandShape {
        REGULAR,
        IRREGULAR
    }
    public enum Topography {
        FLAT,
        SLOPED,
        HILLSIDE
    }
    public enum AccessRoadType {
        ASPHALT,
        GRAVEL,
        DIRT
    }
    public enum OwnershipType {
        INDIVIDUAL,
        COMPANY,
        CO_OWNED,
        CONCESSION
    }
    public enum HeatingType {
        CENTRAL,
        GAS_BOILER,
        HEAT_PUMP,
        DISTRICT_HEATING
    }
    public enum EnergyEfficiencyClass {
        A,
        B,
        C,
        D,
        E,
        F,
        G
    }
    public enum NeighborhoodType {
        URBAN,
        SUBURBAN,
        RURAL
    }
    public enum MaintenanceStatus {
        UP_TO_DATE,
        OVERDUE,
        UNKNOWN
    }
    @NotNull(message = "Property type is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PropertyType propertyType;
    @Size(max = 100, message = "Sub type must be at most 100 characters")
    @Column(length = 100)
    private String subType;
    @Column(length = 2000)
    private String description;
    @Max(value = 9999, message = "Year built is invalid")
    @Column
    private Integer yearBuilt;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ConstructionType constructionType;
    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private Condition condition;
    @NotBlank(message = "Address is required")
    @Size(max = 255, message = "Address must be at most 255 characters")
    @Column(nullable = false, length = 255)
    private String address;
    @DecimalMin(value = "-90.0", message = "Latitude must be greater than or equal to -90")
    @DecimalMax(value = "90.0", message = "Latitude must be less than or equal to 90")
    @Column(precision = 10, scale = 7)
    private BigDecimal latitude;
    @DecimalMin(value = "-180.0", message = "Longitude must be greater than or equal to -180")
    @DecimalMax(value = "180.0", message = "Longitude must be less than or equal to 180")
    @Column(precision = 10, scale = 7)
    private BigDecimal longitude;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ZoningType zoningType;
    @NotNull(message = "Land area is required")
    @DecimalMin(value = "0.0", message = "Land area must be greater than or equal to 0")
    @Digits(integer = 17, fraction = 2)
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal landArea;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private LandShape landShape;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Topography topography;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private AccessRoadType accessRoadType;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "real_estate_utilities", joinColumns = @JoinColumn(name = "real_estate_id"))
    @Column(name = "utility_name", length = 50)
    private Set<String> utilitiesAvailability = new LinkedHashSet<>();
    @DecimalMin(value = "0.0", message = "Built area must be greater than or equal to 0")
    @Digits(integer = 17, fraction = 2)
    @Column(precision = 19, scale = 2)
    private BigDecimal builtArea;
    @DecimalMin(value = "0.0", message = "Usable area must be greater than or equal to 0")
    @Digits(integer = 17, fraction = 2)
    @Column(precision = 19, scale = 2)
    private BigDecimal usableArea;
    @PositiveOrZero(message = "Floors must be greater than or equal to 0")
    @Column
    private Integer floors;
    @PositiveOrZero(message = "Rooms must be greater than or equal to 0")
    @Column
    private Integer rooms;
    @PositiveOrZero(message = "Bathrooms must be greater than or equal to 0")
    @Column
    private Integer bathrooms;
    @PositiveOrZero(message = "Balconies must be greater than or equal to 0")
    @Column
    private Integer balconies;
    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private HeatingType heatingType;
    @Enumerated(EnumType.STRING)
    @Column(name = "energy_efficiency_class", length = 1)
    private EnergyEfficiencyClass energyEfficiencyClass;
    @Size(max = 100, message = "Parking must be at most 100 characters")
    @Column(length = 100)
    private String parking;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "real_estate_amenities", joinColumns = @JoinColumn(name = "real_estate_id"))
    @Column(name = "amenity_name", length = 50)
    private Set<String> amenities = new LinkedHashSet<>();
    
    @Column(length = 2000)
    private String encumbrances;
    @Column(length = 2000)
    private String buildingPermits;
    @Column(length = 2000)
    private String usageRestrictions;
    @NotNull(message = "Ownership type is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OwnershipType ownershipType;
    @NotBlank(message = "Owner name is required")
    @Size(max = 255, message = "Owner name must be at most 255 characters")
    @Column(nullable = false, length = 255)
    private String ownerName;
    @NotBlank(message = "Cadastral number is required")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "Cadastral number must be alphanumeric")
    @Size(max = 100, message = "Cadastral number must be at most 100 characters")
    @Column(nullable = false, length = 100)
    private String cadastralNumber;
    @NotBlank(message = "Land registry number is required")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "Land registry number must be alphanumeric")
    @Size(max = 100, message = "Land registry number must be at most 100 characters")
    @Column(nullable = false, length = 100)
    private String landRegistryNumber;
    @NotNull(message = "Current market value is required")
    @DecimalMin(value = "0.0", message = "Current market value must be greater than or equal to 0")
    @Digits(integer = 17, fraction = 2)
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal currentMarketValue;
    @NotNull(message = "Valuation date is required")
    @Column(nullable = false)
    private LocalDate valuationDate;
    @DecimalMin(value = "0.0", message = "Purchase price must be greater than or equal to 0")
    @Digits(integer = 17, fraction = 2)
    @Column(precision = 19, scale = 2)
    private BigDecimal purchasePrice;
    @DecimalMin(value = "0.0", message = "Rental income must be greater than or equal to 0")
    @Digits(integer = 17, fraction = 2)
    @Column(precision = 19, scale = 2)
    private BigDecimal rentalIncome;
    @DecimalMin(value = "0.0", message = "Operating costs must be greater than or equal to 0")
    @Digits(integer = 17, fraction = 2)
    @Column(precision = 19, scale = 2)
    private BigDecimal operatingCosts;
    @DecimalMin(value = "0.0", message = "Taxes must be greater than or equal to 0")
    @Digits(integer = 17, fraction = 2)
    @Column(precision = 19, scale = 2)
    private BigDecimal taxes;
    @DecimalMin(value = "0.0", message = "Insurance value must be greater than or equal to 0")
    @Digits(integer = 17, fraction = 2)
    @Column(precision = 19, scale = 2)
    private BigDecimal insuranceValue;
    @Digits(integer = 9, fraction = 2)
    @Column(precision = 11, scale = 2)
    private BigDecimal yieldIndicators;
    @Size(max = 100, message = "Depreciation method must be at most 100 characters")
    @Column(length = 100)
    private String depreciationMethod;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private NeighborhoodType neighborhoodType;
    @Column(length = 1000)
    private String proximityToTransport;
    @Column(length = 2000)
    private String nearbyFacilities;
    @Column(length = 2000)
    private String marketTrends;
    @Column(length = 2000)
    private String environmentalRisks;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private MaintenanceStatus maintenanceStatus;
    @Column
    private LocalDate lastRenovationDate;
    @Column(length = 2000)
    private String renovationDetails;
    @Column(length = 2000)
    private String structuralIssues;
    @DecimalMin(value = "0.0", message = "Expected CAPEX must be greater than or equal to 0")
    @Digits(integer = 17, fraction = 2)
    @Column(precision = 19, scale = 2)
    private BigDecimal expectedCapex;
    
    @Digits(integer = 5, fraction = 2)
    @Column(precision = 7, scale = 2)
    private BigDecimal legalRiskScore;
    @Digits(integer = 5, fraction = 2)
    @Column(precision = 7, scale = 2)
    private BigDecimal marketRiskScore;
    @Digits(integer = 5, fraction = 2)
    @Column(precision = 7, scale = 2)
    private BigDecimal structuralRiskScore;
    @Digits(integer = 5, fraction = 2)
    @Column(precision = 7, scale = 2)
    private BigDecimal environmentalRiskScore;
    @Digits(integer = 5, fraction = 2)
    @Column(precision = 7, scale = 2)
    private BigDecimal overallAssessmentScore;
    @Column(length = 2000)
    private String complianceFlags;
    @NotBlank(message = "Data source is required")
    @Size(max = 100, message = "Data source must be at most 100 characters")
    @Column(nullable = false, length = 100)
    private String dataSource;
    @Size(max = 100, message = "Responsible agent must be at most 100 characters")
    @Column(length = 100)
    private String responsibleAgent;
    @Column(length = 500)
    private String tags;
    @Column(length = 4000)
    private String notes;
    @Override
    @JsonIgnore
    public String getName() { return super.getName(); }
    @Override
    @JsonIgnore
    public void setName(String name) { super.setName(name); }
    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title must be at most 255 characters")
    public String getTitle() {
        return getName();
    }
    public void setTitle(String title) {
        setName(title);
    }
    @AssertTrue(message = "Latitude and longitude must both be provided together")
    public boolean isCoordinatePairValid() {
        return (latitude == null && longitude == null) || (latitude != null && longitude != null);
    }
    @AssertTrue(message = "Year built must be less than or equal to the current year")
    public boolean isYearBuiltValid() {
        return yearBuilt == null || yearBuilt <= Year.now().getValue();
    }
    @AssertTrue(message = "Valuation date must be today or earlier")
    public boolean isValuationDateValid() {
        return valuationDate == null || !valuationDate.isAfter(LocalDate.now());
    }
    @AssertTrue(message = "Last renovation date must be today or earlier")
    public boolean isLastRenovationDateValid() {
        return lastRenovationDate == null || !lastRenovationDate.isAfter(LocalDate.now());
    }
    @AssertTrue(message = "Built area must be greater than or equal to usable area")
    public boolean isAreaRelationshipValid() {
        return builtArea == null || usableArea == null || builtArea.compareTo(usableArea) >= 0;
    }
    public PropertyType getPropertyType() { return propertyType; }
    public void setPropertyType(PropertyType propertyType) { this.propertyType = propertyType; }
    public String getSubType() { return subType; }
    public void setSubType(String subType) { this.subType = subType; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Integer getYearBuilt() { return yearBuilt; }
    public void setYearBuilt(Integer yearBuilt) { this.yearBuilt = yearBuilt; }
    public ConstructionType getConstructionType() { return constructionType; }
    public void setConstructionType(ConstructionType constructionType) { this.constructionType = constructionType; }
    public Condition getCondition() { return condition; }
    public void setCondition(Condition condition) { this.condition = condition; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public BigDecimal getLatitude() { return latitude; }
    public void setLatitude(BigDecimal latitude) { this.latitude = latitude; }
    public BigDecimal getLongitude() { return longitude; }
    public void setLongitude(BigDecimal longitude) { this.longitude = longitude; }
    public ZoningType getZoningType() { return zoningType; }
    public void setZoningType(ZoningType zoningType) { this.zoningType = zoningType; }
    public BigDecimal getLandArea() { return landArea; }
    public void setLandArea(BigDecimal landArea) { this.landArea = landArea; }
    public LandShape getLandShape() { return landShape; }
    public void setLandShape(LandShape landShape) { this.landShape = landShape; }
    public Topography getTopography() { return topography; }
    public void setTopography(Topography topography) { this.topography = topography; }
    public AccessRoadType getAccessRoadType() { return accessRoadType; }
    public void setAccessRoadType(AccessRoadType accessRoadType) { this.accessRoadType = accessRoadType; }
    public Set<String> getUtilitiesAvailability() { return utilitiesAvailability; }
    public void setUtilitiesAvailability(Set<String> utilitiesAvailability) { this.utilitiesAvailability = utilitiesAvailability != null ? utilitiesAvailability : new LinkedHashSet<>(); }
    public BigDecimal getBuiltArea() { return builtArea; }
    public void setBuiltArea(BigDecimal builtArea) { this.builtArea = builtArea; }
    public BigDecimal getUsableArea() { return usableArea; }
    public void setUsableArea(BigDecimal usableArea) { this.usableArea = usableArea; }
    public Integer getFloors() { return floors; }
    public void setFloors(Integer floors) { this.floors = floors; }
    public Integer getRooms() { return rooms; }
    public void setRooms(Integer rooms) { this.rooms = rooms; }
    public Integer getBathrooms() { return bathrooms; }
    public void setBathrooms(Integer bathrooms) { this.bathrooms = bathrooms; }
    public Integer getBalconies() { return balconies; }
    public void setBalconies(Integer balconies) { this.balconies = balconies; }
    public HeatingType getHeatingType() { return heatingType; }
    public void setHeatingType(HeatingType heatingType) { this.heatingType = heatingType; }
    public EnergyEfficiencyClass getEnergyEfficiencyClass() { return energyEfficiencyClass; }
    public void setEnergyEfficiencyClass(EnergyEfficiencyClass energyEfficiencyClass) { this.energyEfficiencyClass = energyEfficiencyClass; }
    public String getParking() { return parking; }
    public void setParking(String parking) { this.parking = parking; }
    public Set<String> getAmenities() { return amenities; }
    public void setAmenities(Set<String> amenities) { this.amenities = amenities != null ? amenities : new LinkedHashSet<>(); }
    
    
    public String getEncumbrances() { return encumbrances; }
    public void setEncumbrances(String encumbrances) { this.encumbrances = encumbrances; }
    public String getBuildingPermits() { return buildingPermits; }
    public void setBuildingPermits(String buildingPermits) { this.buildingPermits = buildingPermits; }
    public String getUsageRestrictions() { return usageRestrictions; }
    public void setUsageRestrictions(String usageRestrictions) { this.usageRestrictions = usageRestrictions; }
    public OwnershipType getOwnershipType() { return ownershipType; }
    public void setOwnershipType(OwnershipType ownershipType) { this.ownershipType = ownershipType; }
    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }
    public String getCadastralNumber() { return cadastralNumber; }
    public void setCadastralNumber(String cadastralNumber) { this.cadastralNumber = cadastralNumber; }
    public String getLandRegistryNumber() { return landRegistryNumber; }
    public void setLandRegistryNumber(String landRegistryNumber) { this.landRegistryNumber = landRegistryNumber; }
    public BigDecimal getCurrentMarketValue() { return currentMarketValue; }
    public void setCurrentMarketValue(BigDecimal currentMarketValue) { this.currentMarketValue = currentMarketValue; }
    public LocalDate getValuationDate() { return valuationDate; }
    public void setValuationDate(LocalDate valuationDate) { this.valuationDate = valuationDate; }
    public BigDecimal getPurchasePrice() { return purchasePrice; }
    public void setPurchasePrice(BigDecimal purchasePrice) { this.purchasePrice = purchasePrice; }
    public BigDecimal getRentalIncome() { return rentalIncome; }
    public void setRentalIncome(BigDecimal rentalIncome) { this.rentalIncome = rentalIncome; }
    public BigDecimal getOperatingCosts() { return operatingCosts; }
    public void setOperatingCosts(BigDecimal operatingCosts) { this.operatingCosts = operatingCosts; }
    public BigDecimal getTaxes() { return taxes; }
    public void setTaxes(BigDecimal taxes) { this.taxes = taxes; }
    public BigDecimal getInsuranceValue() { return insuranceValue; }
    public void setInsuranceValue(BigDecimal insuranceValue) { this.insuranceValue = insuranceValue; }
    public BigDecimal getYieldIndicators() { return yieldIndicators; }
    public void setYieldIndicators(BigDecimal yieldIndicators) { this.yieldIndicators = yieldIndicators; }
    public String getDepreciationMethod() { return depreciationMethod; }
    public void setDepreciationMethod(String depreciationMethod) { this.depreciationMethod = depreciationMethod; }
    public NeighborhoodType getNeighborhoodType() { return neighborhoodType; }
    public void setNeighborhoodType(NeighborhoodType neighborhoodType) { this.neighborhoodType = neighborhoodType; }
    public String getProximityToTransport() { return proximityToTransport; }
    public void setProximityToTransport(String proximityToTransport) { this.proximityToTransport = proximityToTransport; }
    public String getNearbyFacilities() { return nearbyFacilities; }
    public void setNearbyFacilities(String nearbyFacilities) { this.nearbyFacilities = nearbyFacilities; }
    public String getMarketTrends() { return marketTrends; }
    public void setMarketTrends(String marketTrends) { this.marketTrends = marketTrends; }
    public String getEnvironmentalRisks() { return environmentalRisks; }
    public void setEnvironmentalRisks(String environmentalRisks) { this.environmentalRisks = environmentalRisks; }
    public MaintenanceStatus getMaintenanceStatus() { return maintenanceStatus; }
    public void setMaintenanceStatus(MaintenanceStatus maintenanceStatus) { this.maintenanceStatus = maintenanceStatus; }
    public LocalDate getLastRenovationDate() { return lastRenovationDate; }
    public void setLastRenovationDate(LocalDate lastRenovationDate) { this.lastRenovationDate = lastRenovationDate; }
    public String getRenovationDetails() { return renovationDetails; }
    public void setRenovationDetails(String renovationDetails) { this.renovationDetails = renovationDetails; }
    public String getStructuralIssues() { return structuralIssues; }
    public void setStructuralIssues(String structuralIssues) { this.structuralIssues = structuralIssues; }
    public BigDecimal getExpectedCapex() { return expectedCapex; }
    public void setExpectedCapex(BigDecimal expectedCapex) { this.expectedCapex = expectedCapex; }
    
    
    public BigDecimal getLegalRiskScore() { return legalRiskScore; }
    public void setLegalRiskScore(BigDecimal legalRiskScore) { this.legalRiskScore = legalRiskScore; }
    public BigDecimal getMarketRiskScore() { return marketRiskScore; }
    public void setMarketRiskScore(BigDecimal marketRiskScore) { this.marketRiskScore = marketRiskScore; }
    public BigDecimal getStructuralRiskScore() { return structuralRiskScore; }
    public void setStructuralRiskScore(BigDecimal structuralRiskScore) { this.structuralRiskScore = structuralRiskScore; }
    public BigDecimal getEnvironmentalRiskScore() { return environmentalRiskScore; }
    public void setEnvironmentalRiskScore(BigDecimal environmentalRiskScore) { this.environmentalRiskScore = environmentalRiskScore; }
    public BigDecimal getOverallAssessmentScore() { return overallAssessmentScore; }
    public void setOverallAssessmentScore(BigDecimal overallAssessmentScore) { this.overallAssessmentScore = overallAssessmentScore; }
    public String getComplianceFlags() { return complianceFlags; }
    public void setComplianceFlags(String complianceFlags) { this.complianceFlags = complianceFlags; }
    public String getDataSource() { return dataSource; }
    public void setDataSource(String dataSource) { this.dataSource = dataSource; }
    public String getResponsibleAgent() { return responsibleAgent; }
    public void setResponsibleAgent(String responsibleAgent) { this.responsibleAgent = responsibleAgent; }
    public String getTags() { return tags; }
    public void setTags(String tags) { this.tags = tags; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}



