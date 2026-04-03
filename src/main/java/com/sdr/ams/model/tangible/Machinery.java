package com.sdr.ams.model.tangible;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sdr.ams.model.core.CoreEntity;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@Entity
@AttributeOverride(name = "name", column = @Column(name = "machine_id", nullable = false, length = 100))
@Table(
    name = "machinery",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_machinery_machine_id", columnNames = "machine_id"),
        @UniqueConstraint(name = "uk_machinery_serial_number", columnNames = "serial_number")
    }
)
public class Machinery extends CoreEntity {

    public enum Category { CONSTRUCTION, AGRICULTURAL, INDUSTRIAL, MANUFACTURING, OTHER }
    public enum Subcategory { EXCAVATOR, CNC_MACHINE, CONVEYOR, COMPRESSOR, PUMP, CRANE, WELDER, DRILL, LATHE, SAWMILL, OTHER }
    public enum FuelType { DIESEL, ELECTRIC, HYBRID, PETROL, LPG, OTHER }
    public enum MaintenanceSchedule { PREVENTIVE, PREDICTIVE, CORRECTIVE }
    public enum ConditionStatus { EXCELLENT, GOOD, FAIR, POOR, CRITICAL }
    public enum UsageType { OWNED, LEASED, RENTED, SUBCONTRACTED }
    public enum DepreciationMethod { STRAIGHT_LINE, DECLINING_BALANCE, OTHER }
    public enum OperationalCriticality { LOW, MEDIUM, HIGH }
    public enum RegulatoryClassification { HAZARDOUS, HEAVY_EQUIPMENT, PRESSURE_VESSEL, OTHER }

    @Override
    @JsonIgnore
    public String getName() {
        return super.getName();
    }

    @Override
    @JsonIgnore
    public void setName(String name) {
        super.setName(name);
    }

    // Identity and Classification
    @NotBlank(message = "Machine ID is required")
    @Size(max = 100, message = "Machine ID must be at most 100 characters")
    @Pattern(regexp = "^[A-Za-z0-9_-]+$", message = "Machine ID must be alphanumeric")
    public String getMachineId() {
        return getName();
    }

    public void setMachineId(String machineId) {
        setName(machineId);
    }

    @NotNull(message = "Category is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private Subcategory subcategory;

    @NotBlank(message = "Manufacturer is required")
    @Size(max = 100, message = "Manufacturer must be at most 100 characters")
    @Column(nullable = false, length = 100)
    private String manufacturer;

    @NotBlank(message = "Model is required")
    @Size(max = 100, message = "Model must be at most 100 characters")
    @Column(nullable = false, length = 100)
    private String model;

    @NotBlank(message = "Serial number is required")
    @Size(max = 100, message = "Serial number must be at most 100 characters")
    @Column(nullable = false, length = 100, unique = true)
    private String serialNumber;

    @NotNull(message = "Year of manufacture is required")
    @Column(nullable = false)
    private Integer yearOfManufacture;

    @Size(max = 100, message = "Version/configuration must be at most 100 characters")
    @Column(length = 100)
    private String versionConfiguration;

    @Size(max = 1000, message = "Description must be at most 1000 characters")
    @Column(length = 1000)
    private String description;

    // Technical Specifications
    @Size(max = 50, message = "Power rating must be at most 50 characters")
    @Column(length = 50)
    private String powerRating;

    @Size(max = 50, message = "Voltage/electrical requirements must be at most 50 characters")
    @Column(length = 50)
    private String voltageElectricalRequirements;

    @Size(max = 100, message = "Capacity must be at most 100 characters")
    @Column(length = 100)
    private String capacity;

    @Size(max = 100, message = "Dimensions must be at most 100 characters")
    @Column(length = 100)
    private String dimensions;

    @DecimalMin(value = "0.0", message = "Weight must be greater than or equal to 0")
    @Digits(integer = 10, fraction = 2)
    @Column(precision = 12, scale = 2)
    private BigDecimal weight;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private FuelType fuelType;

    @Size(max = 500, message = "Performance metrics must be at most 500 characters")
    @Column(length = 500)
    private String performanceMetrics;

    @Size(max = 100, message = "Safety class/certifications must be at most 100 characters")
    @Column(length = 100)
    private String safetyClassCertifications;

    @Size(max = 200, message = "Operating environment requirements must be at most 200 characters")
    @Column(length = 200)
    private String operatingEnvironmentRequirements;

    // Location and Ownership
    @Size(max = 200, message = "Current location must be at most 200 characters")
    @Column(length = 200)
    private String currentLocation;

    @Size(max = 1000, message = "Previous locations must be at most 1000 characters")
    @Column(length = 1000)
    private String previousLocations;

    @Size(max = 100, message = "Owner must be at most 100 characters")
    @Column(length = 100)
    private String owner;

    @Size(max = 100, message = "Custodian must be at most 100 characters")
    @Column(length = 100)
    private String custodian;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private UsageType usageType;

    // Maintenance and Condition
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ConditionStatus conditionStatus;

    @PositiveOrZero(message = "Operating hours must be greater than or equal to 0")
    @Column
    private Long operatingHours;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private MaintenanceSchedule maintenanceSchedule;

    @Column
    private LocalDate lastMaintenanceDate;

    @Column
    private LocalDate nextMaintenanceDue;

    @Size(max = 4000, message = "Maintenance history must be at most 4000 characters")
    @Column(length = 4000)
    private String maintenanceHistory;

    @Size(max = 100, message = "Warranty status must be at most 100 characters")
    @Column(length = 100)
    private String warrantyStatus;

    @Size(max = 100, message = "Service provider must be at most 100 characters")
    @Column(length = 100)
    private String serviceProvider;

    // Financial and Valuation
    @Column
    private LocalDate purchaseDate;

    @DecimalMin(value = "0.0", message = "Purchase price must be greater than or equal to 0")
    @Digits(integer = 17, fraction = 2)
    @Column(precision = 19, scale = 2)
    private BigDecimal purchasePrice;

    @DecimalMin(value = "0.0", message = "Current book value must be greater than or equal to 0")
    @Digits(integer = 17, fraction = 2)
    @Column(precision = 19, scale = 2)
    private BigDecimal bookValue;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private DepreciationMethod depreciationMethod;

    @DecimalMin(value = "0.0", message = "Depreciation rate must be greater than or equal to 0")
    @Digits(integer = 5, fraction = 2)
    @Column(precision = 7, scale = 2)
    private BigDecimal depreciationRate;

    @DecimalMin(value = "0.0", message = "Residual value must be greater than or equal to 0")
    @Digits(integer = 17, fraction = 2)
    @Column(precision = 19, scale = 2)
    private BigDecimal residualValue;

    @DecimalMin(value = "0.0", message = "Insurance value must be greater than or equal to 0")
    @Digits(integer = 17, fraction = 2)
    @Column(precision = 19, scale = 2)
    private BigDecimal insuranceValue;

    @DecimalMin(value = "0.0", message = "Operating cost must be greater than or equal to 0")
    @Digits(integer = 17, fraction = 2)
    @Column(precision = 19, scale = 2)
    private BigDecimal operatingCost;

    @DecimalMin(value = "0.0", message = "Maintenance cost must be greater than or equal to 0")
    @Digits(integer = 17, fraction = 2)
    @Column(precision = 19, scale = 2)
    private BigDecimal maintenanceCost;

    // Compliance and Documentation
    @Size(max = 500, message = "Certificates must be at most 500 characters")
    @Column(length = 500)
    private String certificates;

    @Size(max = 1000, message = "Inspection reports must be at most 1000 characters")
    @Column(length = 1000)
    private String inspectionReports;

    @Size(max = 500, message = "Operating manuals must be at most 500 characters")
    @Column(length = 500)
    private String operatingManuals;

    @Size(max = 500, message = "Permits or licenses must be at most 500 characters")
    @Column(length = 500)
    private String permitsLicenses;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private RegulatoryClassification regulatoryClassification;

    // Risk and Assessment Indicators
    @Digits(integer = 5, fraction = 2)
    @Column(precision = 7, scale = 2)
    private BigDecimal failureRiskScore;

    @Digits(integer = 5, fraction = 2)
    @Column(precision = 7, scale = 2)
    private BigDecimal safetyRiskScore;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private OperationalCriticality operationalCriticality;

    @PositiveOrZero(message = "Downtime hours must be greater than or equal to 0")
    @Column
    private Long downtimeHours;

    @DecimalMin(value = "0.0", message = "Utilization rate must be greater than or equal to 0")
    @DecimalMax(value = "100.0", message = "Utilization rate must be less than or equal to 100")
    @Digits(integer = 5, fraction = 2)
    @Column(precision = 7, scale = 2)
    private BigDecimal utilizationRate;

    @Size(max = 200, message = "Environmental impact indicators must be at most 200 characters")
    @Column(length = 200)
    private String environmentalImpactIndicators;

    @Size(max = 1000, message = "Compliance flags must be at most 1000 characters")
    @Column(length = 1000)
    private String complianceFlags;

    // Metadata
    @NotBlank(message = "Data source is required")
    @Size(max = 100, message = "Data source must be at most 100 characters")
    @Column(nullable = false, length = 100)
    private String dataSource;

    @Size(max = 100, message = "Responsible analyst must be at most 100 characters")
    @Column(length = 100)
    private String responsibleAnalyst;

    @Size(max = 500, message = "Tags/labels must be at most 500 characters")
    @Column(length = 500)
    private String tagsLabels;

    @Size(max = 2000, message = "Notes/comments must be at most 2000 characters")
    @Column(length = 2000)
    private String notesComments;

    // Validation methods
    @AssertTrue(message = "Year of manufacture must be less than or equal to the current year")
    public boolean isYearOfManufactureValid() {
        return yearOfManufacture == null || yearOfManufacture <= Year.now().getValue();
    }

    @AssertTrue(message = "Purchase date must be today or earlier")
    public boolean isPurchaseDateValid() {
        return purchaseDate == null || !purchaseDate.isAfter(LocalDate.now());
    }

    @AssertTrue(message = "Last maintenance date must be today or earlier")
    public boolean isLastMaintenanceDateValid() {
        return lastMaintenanceDate == null || !lastMaintenanceDate.isAfter(LocalDate.now());
    }

    @AssertTrue(message = "Next maintenance due must be greater than or equal to last maintenance date")
    public boolean isMaintenanceDateRangeValid() {
        return lastMaintenanceDate == null || nextMaintenanceDue == null || !nextMaintenanceDue.isBefore(lastMaintenanceDate);
    }

    // Getters and Setters
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
    public Subcategory getSubcategory() { return subcategory; }
    public void setSubcategory(Subcategory subcategory) { this.subcategory = subcategory; }
    public String getManufacturer() { return manufacturer; }
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public String getSerialNumber() { return serialNumber; }
    public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }
    public Integer getYearOfManufacture() { return yearOfManufacture; }
    public void setYearOfManufacture(Integer yearOfManufacture) { this.yearOfManufacture = yearOfManufacture; }
    public String getVersionConfiguration() { return versionConfiguration; }
    public void setVersionConfiguration(String versionConfiguration) { this.versionConfiguration = versionConfiguration; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getPowerRating() { return powerRating; }
    public void setPowerRating(String powerRating) { this.powerRating = powerRating; }
    public String getVoltageElectricalRequirements() { return voltageElectricalRequirements; }
    public void setVoltageElectricalRequirements(String voltageElectricalRequirements) { this.voltageElectricalRequirements = voltageElectricalRequirements; }
    public String getCapacity() { return capacity; }
    public void setCapacity(String capacity) { this.capacity = capacity; }
    public String getDimensions() { return dimensions; }
    public void setDimensions(String dimensions) { this.dimensions = dimensions; }
    public BigDecimal getWeight() { return weight; }
    public void setWeight(BigDecimal weight) { this.weight = weight; }
    public FuelType getFuelType() { return fuelType; }
    public void setFuelType(FuelType fuelType) { this.fuelType = fuelType; }
    public String getPerformanceMetrics() { return performanceMetrics; }
    public void setPerformanceMetrics(String performanceMetrics) { this.performanceMetrics = performanceMetrics; }
    public String getSafetyClassCertifications() { return safetyClassCertifications; }
    public void setSafetyClassCertifications(String safetyClassCertifications) { this.safetyClassCertifications = safetyClassCertifications; }
    public String getOperatingEnvironmentRequirements() { return operatingEnvironmentRequirements; }
    public void setOperatingEnvironmentRequirements(String operatingEnvironmentRequirements) { this.operatingEnvironmentRequirements = operatingEnvironmentRequirements; }
    public String getCurrentLocation() { return currentLocation; }
    public void setCurrentLocation(String currentLocation) { this.currentLocation = currentLocation; }
    public String getPreviousLocations() { return previousLocations; }
    public void setPreviousLocations(String previousLocations) { this.previousLocations = previousLocations; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public String getCustodian() { return custodian; }
    public void setCustodian(String custodian) { this.custodian = custodian; }
    public UsageType getUsageType() { return usageType; }
    public void setUsageType(UsageType usageType) { this.usageType = usageType; }
    public ConditionStatus getConditionStatus() { return conditionStatus; }
    public void setConditionStatus(ConditionStatus conditionStatus) { this.conditionStatus = conditionStatus; }
    public Long getOperatingHours() { return operatingHours; }
    public void setOperatingHours(Long operatingHours) { this.operatingHours = operatingHours; }
    public MaintenanceSchedule getMaintenanceSchedule() { return maintenanceSchedule; }
    public void setMaintenanceSchedule(MaintenanceSchedule maintenanceSchedule) { this.maintenanceSchedule = maintenanceSchedule; }
    public LocalDate getLastMaintenanceDate() { return lastMaintenanceDate; }
    public void setLastMaintenanceDate(LocalDate lastMaintenanceDate) { this.lastMaintenanceDate = lastMaintenanceDate; }
    public LocalDate getNextMaintenanceDue() { return nextMaintenanceDue; }
    public void setNextMaintenanceDue(LocalDate nextMaintenanceDue) { this.nextMaintenanceDue = nextMaintenanceDue; }
    public String getMaintenanceHistory() { return maintenanceHistory; }
    public void setMaintenanceHistory(String maintenanceHistory) { this.maintenanceHistory = maintenanceHistory; }
    public String getWarrantyStatus() { return warrantyStatus; }
    public void setWarrantyStatus(String warrantyStatus) { this.warrantyStatus = warrantyStatus; }
    public String getServiceProvider() { return serviceProvider; }
    public void setServiceProvider(String serviceProvider) { this.serviceProvider = serviceProvider; }
    public LocalDate getPurchaseDate() { return purchaseDate; }
    public void setPurchaseDate(LocalDate purchaseDate) { this.purchaseDate = purchaseDate; }
    public BigDecimal getPurchasePrice() { return purchasePrice; }
    public void setPurchasePrice(BigDecimal purchasePrice) { this.purchasePrice = purchasePrice; }
    public BigDecimal getBookValue() { return bookValue; }
    public void setBookValue(BigDecimal bookValue) { this.bookValue = bookValue; }
    public DepreciationMethod getDepreciationMethod() { return depreciationMethod; }
    public void setDepreciationMethod(DepreciationMethod depreciationMethod) { this.depreciationMethod = depreciationMethod; }
    public BigDecimal getDepreciationRate() { return depreciationRate; }
    public void setDepreciationRate(BigDecimal depreciationRate) { this.depreciationRate = depreciationRate; }
    public BigDecimal getResidualValue() { return residualValue; }
    public void setResidualValue(BigDecimal residualValue) { this.residualValue = residualValue; }
    public BigDecimal getInsuranceValue() { return insuranceValue; }
    public void setInsuranceValue(BigDecimal insuranceValue) { this.insuranceValue = insuranceValue; }
    public BigDecimal getOperatingCost() { return operatingCost; }
    public void setOperatingCost(BigDecimal operatingCost) { this.operatingCost = operatingCost; }
    public BigDecimal getMaintenanceCost() { return maintenanceCost; }
    public void setMaintenanceCost(BigDecimal maintenanceCost) { this.maintenanceCost = maintenanceCost; }
    public String getCertificates() { return certificates; }
    public void setCertificates(String certificates) { this.certificates = certificates; }
    public String getInspectionReports() { return inspectionReports; }
    public void setInspectionReports(String inspectionReports) { this.inspectionReports = inspectionReports; }
    public String getOperatingManuals() { return operatingManuals; }
    public void setOperatingManuals(String operatingManuals) { this.operatingManuals = operatingManuals; }
    public String getPermitsLicenses() { return permitsLicenses; }
    public void setPermitsLicenses(String permitsLicenses) { this.permitsLicenses = permitsLicenses; }
    public RegulatoryClassification getRegulatoryClassification() { return regulatoryClassification; }
    public void setRegulatoryClassification(RegulatoryClassification regulatoryClassification) { this.regulatoryClassification = regulatoryClassification; }
    public BigDecimal getFailureRiskScore() { return failureRiskScore; }
    public void setFailureRiskScore(BigDecimal failureRiskScore) { this.failureRiskScore = failureRiskScore; }
    public BigDecimal getSafetyRiskScore() { return safetyRiskScore; }
    public void setSafetyRiskScore(BigDecimal safetyRiskScore) { this.safetyRiskScore = safetyRiskScore; }
    public OperationalCriticality getOperationalCriticality() { return operationalCriticality; }
    public void setOperationalCriticality(OperationalCriticality operationalCriticality) { this.operationalCriticality = operationalCriticality; }
    public Long getDowntimeHours() { return downtimeHours; }
    public void setDowntimeHours(Long downtimeHours) { this.downtimeHours = downtimeHours; }
    public BigDecimal getUtilizationRate() { return utilizationRate; }
    public void setUtilizationRate(BigDecimal utilizationRate) { this.utilizationRate = utilizationRate; }
    public String getEnvironmentalImpactIndicators() { return environmentalImpactIndicators; }
    public void setEnvironmentalImpactIndicators(String environmentalImpactIndicators) { this.environmentalImpactIndicators = environmentalImpactIndicators; }
    public String getComplianceFlags() { return complianceFlags; }
    public void setComplianceFlags(String complianceFlags) { this.complianceFlags = complianceFlags; }
    public String getDataSource() { return dataSource; }
    public void setDataSource(String dataSource) { this.dataSource = dataSource; }
    public String getResponsibleAnalyst() { return responsibleAnalyst; }
    public void setResponsibleAnalyst(String responsibleAnalyst) { this.responsibleAnalyst = responsibleAnalyst; }
    public String getTagsLabels() { return tagsLabels; }
    public void setTagsLabels(String tagsLabels) { this.tagsLabels = tagsLabels; }
    public String getNotesComments() { return notesComments; }
    public void setNotesComments(String notesComments) { this.notesComments = notesComments; }
}
