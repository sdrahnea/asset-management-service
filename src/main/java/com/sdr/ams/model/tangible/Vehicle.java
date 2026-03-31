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
@AttributeOverride(name = "name", column = @Column(name = "vehicle_id", nullable = false, length = 100))
@Table(
    name = "vehicle",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_vehicle_vehicle_id", columnNames = "vehicle_id"),
        @UniqueConstraint(name = "uk_vehicle_vin", columnNames = "vin"),
        @UniqueConstraint(name = "uk_vehicle_license_country", columnNames = {"license_plate", "registration_country"})
    }
)
public class Vehicle extends CoreEntity {

    public enum VehicleType { CAR, TRUCK, VAN, MOTORCYCLE, BUS, TRACTOR, TRAILER, OTHER }
    public enum EngineType { PETROL, DIESEL, HYBRID, ELECTRIC, LPG, OTHER }
    public enum Transmission { MANUAL, AUTOMATIC, CVT, OTHER }
    public enum Drivetrain { FWD, RWD, AWD, FOUR_BY_FOUR, OTHER }
    public enum RegistrationStatus { ACTIVE, EXPIRED, SUSPENDED }
    public enum VerificationStatus { VALID, EXPIRED }
    public enum TaxStatus { PAID, OVERDUE }
    public enum OwnershipType { INDIVIDUAL, COMPANY, LEASED, RENTED }
    public enum UsageCategory { PERSONAL, COMMERCIAL, INDUSTRIAL, AGRICULTURAL }
    public enum OperatingRegion { URBAN, RURAL, MIXED }
    public enum ConditionStatus { EXCELLENT, GOOD, FAIR, POOR }
    public enum MaintenanceSchedule { PERIODIC, MILEAGE_BASED }
    public enum DepreciationMethod { STRAIGHT_LINE, DECLINING_BALANCE, OTHER }

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

    @NotBlank(message = "Vehicle ID is required")
    @Size(max = 100, message = "Vehicle ID must be at most 100 characters")
    @Pattern(regexp = "^[A-Za-z0-9_-]+$", message = "Vehicle ID must be alphanumeric")
    public String getVehicleId() {
        return getName();
    }

    public void setVehicleId(String vehicleId) {
        setName(vehicleId);
    }

    @NotBlank(message = "VIN is required")
    @Size(min = 11, max = 17, message = "VIN length must be between 11 and 17 characters")
    @Pattern(regexp = "^[A-HJ-NPR-Z0-9]+$", message = "VIN format is invalid")
    @Column(nullable = false, length = 17)
    private String vin;

    @NotBlank(message = "License plate is required")
    @Size(max = 20, message = "License plate must be at most 20 characters")
    @Column(nullable = false, length = 20)
    private String licensePlate;

    @NotNull(message = "Vehicle type is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private VehicleType vehicleType;

    @NotBlank(message = "Make is required")
    @Size(max = 100, message = "Make must be at most 100 characters")
    @Column(nullable = false, length = 100)
    private String make;

    @NotBlank(message = "Model is required")
    @Size(max = 100, message = "Model must be at most 100 characters")
    @Column(name = "vehicle_model", nullable = false, length = 100)
    private String model;

    @Size(max = 100, message = "Trim/variant must be at most 100 characters")
    @Column(name = "trim_variant", length = 100)
    private String trimVariant;

    @Column(nullable = false)
    @NotNull(message = "Year of manufacture is required")
    private Integer yearOfManufacture;

    @Size(max = 100, message = "Body type must be at most 100 characters")
    @Column(length = 100)
    private String bodyType;

    @Size(max = 50, message = "Color must be at most 50 characters")
    @Column(length = 50)
    private String color;

    @NotNull(message = "Engine type is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EngineType engineType;

    @Size(max = 50, message = "Engine capacity must be at most 50 characters")
    @Column(length = 50)
    private String engineCapacity;

    @Size(max = 50, message = "Power output must be at most 50 characters")
    @Column(length = 50)
    private String powerOutput;

    @Size(max = 50, message = "Torque must be at most 50 characters")
    @Column(length = 50)
    private String torque;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Transmission transmission;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Drivetrain drivetrain;

    @Size(max = 100, message = "Fuel consumption must be at most 100 characters")
    @Column(length = 100)
    private String fuelConsumption;

    @Size(max = 50, message = "Battery capacity must be at most 50 characters")
    @Column(length = 50)
    private String batteryCapacity;

    @PositiveOrZero(message = "Range must be greater than or equal to 0")
    @Column(name = "electric_range_km")
    private Integer electricRangeKm;

    @Size(max = 30, message = "Emission standard must be at most 30 characters")
    @Column(length = 30)
    private String emissionStandard;

    @Size(max = 100, message = "Dimensions must be at most 100 characters")
    @Column(length = 100)
    private String dimensions;

    @DecimalMin(value = "0.0", message = "Gross weight must be greater than or equal to 0")
    @Digits(integer = 10, fraction = 2)
    @Column(precision = 12, scale = 2)
    private BigDecimal grossWeight;

    @DecimalMin(value = "0.0", message = "Payload capacity must be greater than or equal to 0")
    @Digits(integer = 10, fraction = 2)
    @Column(precision = 12, scale = 2)
    private BigDecimal payloadCapacity;

    @Size(max = 100, message = "Registration country must be at most 100 characters")
    @Column(length = 100)
    private String registrationCountry;

    @Column
    private LocalDate registrationDate;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private RegistrationStatus registrationStatus;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private VerificationStatus inspectionStatus;

    @Column
    private LocalDate inspectionExpiryDate;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private VerificationStatus insuranceStatus;

    @Column
    private LocalDate insuranceExpiryDate;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private TaxStatus taxStatus;

    @Column(length = 2000)
    private String homologationDocuments;

    @Column(length = 2000)
    private String complianceCertificates;

    @Size(max = 255, message = "Owner name must be at most 255 characters")
    @Column(length = 255)
    private String ownerName;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private OwnershipType ownershipType;

    @Column(length = 500)
    private String leasingDetails;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private UsageCategory usageCategory;

    @PositiveOrZero(message = "Current mileage must be greater than or equal to 0")
    @Column
    private Long currentMileage;

    @PositiveOrZero(message = "Average annual mileage must be greater than or equal to 0")
    @Column
    private Long averageAnnualMileage;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private OperatingRegion operatingRegion;

    @Column(length = 1000)
    private String driverAssignments;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ConditionStatus conditionStatus;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private MaintenanceSchedule maintenanceSchedule;

    @Column
    private LocalDate lastServiceDate;

    @Column
    private LocalDate nextServiceDue;

    @Column(length = 4000)
    private String serviceHistory;

    @Column(length = 4000)
    private String accidentHistory;

    @Column(length = 1000)
    private String faultCodes;

    @Column(length = 100)
    private String warrantyStatus;

    @DecimalMin(value = "0.0", message = "Purchase price must be greater than or equal to 0")
    @Digits(integer = 17, fraction = 2)
    @Column(precision = 19, scale = 2)
    private BigDecimal purchasePrice;

    @Column
    private LocalDate purchaseDate;

    @DecimalMin(value = "0.0", message = "Current market value must be greater than or equal to 0")
    @Digits(integer = 17, fraction = 2)
    @Column(precision = 19, scale = 2)
    private BigDecimal currentMarketValue;

    @Column
    private LocalDate valuationDate;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private DepreciationMethod depreciationMethod;

    @DecimalMin(value = "0.0", message = "Depreciation rate must be greater than or equal to 0")
    @DecimalMax(value = "100.0", message = "Depreciation rate must be less than or equal to 100")
    @Digits(integer = 5, fraction = 2)
    @Column(precision = 7, scale = 2)
    private BigDecimal depreciationRate;

    @DecimalMin(value = "0.0", message = "Operating costs must be greater than or equal to 0")
    @Digits(integer = 17, fraction = 2)
    @Column(precision = 19, scale = 2)
    private BigDecimal operatingCosts;

    @DecimalMin(value = "0.0", message = "Insurance cost must be greater than or equal to 0")
    @Digits(integer = 17, fraction = 2)
    @Column(precision = 19, scale = 2)
    private BigDecimal insuranceCost;

    @DecimalMin(value = "0.0", message = "Tax cost must be greater than or equal to 0")
    @Digits(integer = 17, fraction = 2)
    @Column(precision = 19, scale = 2)
    private BigDecimal taxCost;

    @Digits(integer = 5, fraction = 2)
    @Column(precision = 7, scale = 2)
    private BigDecimal mechanicalRiskScore;

    @Digits(integer = 5, fraction = 2)
    @Column(precision = 7, scale = 2)
    private BigDecimal accidentRiskScore;

    @Digits(integer = 5, fraction = 2)
    @Column(precision = 7, scale = 2)
    private BigDecimal operationalRiskScore;

    @Digits(integer = 5, fraction = 2)
    @Column(precision = 7, scale = 2)
    private BigDecimal complianceRiskScore;

    @Digits(integer = 5, fraction = 2)
    @Column(precision = 7, scale = 2)
    private BigDecimal environmentalImpactScore;

    @Digits(integer = 5, fraction = 2)
    @Column(precision = 7, scale = 2)
    private BigDecimal overallAssessmentScore;

    @Column(length = 2000)
    private String complianceFlags;

    @Column(length = 2000)
    private String registrationDocuments;

    @Column(length = 2000)
    private String insuranceDocuments;

    @Column(length = 2000)
    private String inspectionReports;

    @Column(length = 2000)
    private String serviceInvoices;

    @Column(length = 2000)
    private String accidentReports;

    @Column(length = 2000)
    private String photos;

    @Column(length = 4000)
    private String internalNotes;

    @NotBlank(message = "Data source is required")
    @Size(max = 100, message = "Data source must be at most 100 characters")
    @Column(nullable = false, length = 100)
    private String dataSource;

    @Size(max = 100, message = "Responsible agent must be at most 100 characters")
    @Column(length = 100)
    private String responsibleAgent;

    @Column(length = 4000)
    private String notes;

    @AssertTrue(message = "Year of manufacture must be less than or equal to the current year")
    public boolean isYearOfManufactureValid() {
        return yearOfManufacture == null || yearOfManufacture <= Year.now().getValue();
    }

    @AssertTrue(message = "Registration date must be today or earlier")
    public boolean isRegistrationDateValid() {
        return registrationDate == null || !registrationDate.isAfter(LocalDate.now());
    }

    @AssertTrue(message = "Purchase date must be today or earlier")
    public boolean isPurchaseDateValid() {
        return purchaseDate == null || !purchaseDate.isAfter(LocalDate.now());
    }

    @AssertTrue(message = "Valuation date must be today or earlier")
    public boolean isValuationDateValid() {
        return valuationDate == null || !valuationDate.isAfter(LocalDate.now());
    }

    @AssertTrue(message = "Next service due must be greater than or equal to last service date")
    public boolean isServiceDateRangeValid() {
        return lastServiceDate == null || nextServiceDue == null || !nextServiceDue.isBefore(lastServiceDate);
    }

    public String getVin() { return vin; }
    public void setVin(String vin) { this.vin = vin; }
    public String getLicensePlate() { return licensePlate; }
    public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }
    public VehicleType getVehicleType() { return vehicleType; }
    public void setVehicleType(VehicleType vehicleType) { this.vehicleType = vehicleType; }
    public String getMake() { return make; }
    public void setMake(String make) { this.make = make; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public String getTrimVariant() { return trimVariant; }
    public void setTrimVariant(String trimVariant) { this.trimVariant = trimVariant; }
    public Integer getYearOfManufacture() { return yearOfManufacture; }
    public void setYearOfManufacture(Integer yearOfManufacture) { this.yearOfManufacture = yearOfManufacture; }
    public String getBodyType() { return bodyType; }
    public void setBodyType(String bodyType) { this.bodyType = bodyType; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public EngineType getEngineType() { return engineType; }
    public void setEngineType(EngineType engineType) { this.engineType = engineType; }
    public String getEngineCapacity() { return engineCapacity; }
    public void setEngineCapacity(String engineCapacity) { this.engineCapacity = engineCapacity; }
    public String getPowerOutput() { return powerOutput; }
    public void setPowerOutput(String powerOutput) { this.powerOutput = powerOutput; }
    public String getTorque() { return torque; }
    public void setTorque(String torque) { this.torque = torque; }
    public Transmission getTransmission() { return transmission; }
    public void setTransmission(Transmission transmission) { this.transmission = transmission; }
    public Drivetrain getDrivetrain() { return drivetrain; }
    public void setDrivetrain(Drivetrain drivetrain) { this.drivetrain = drivetrain; }
    public String getFuelConsumption() { return fuelConsumption; }
    public void setFuelConsumption(String fuelConsumption) { this.fuelConsumption = fuelConsumption; }
    public String getBatteryCapacity() { return batteryCapacity; }
    public void setBatteryCapacity(String batteryCapacity) { this.batteryCapacity = batteryCapacity; }
    public Integer getElectricRangeKm() { return electricRangeKm; }
    public void setElectricRangeKm(Integer electricRangeKm) { this.electricRangeKm = electricRangeKm; }
    public String getEmissionStandard() { return emissionStandard; }
    public void setEmissionStandard(String emissionStandard) { this.emissionStandard = emissionStandard; }
    public String getDimensions() { return dimensions; }
    public void setDimensions(String dimensions) { this.dimensions = dimensions; }
    public BigDecimal getGrossWeight() { return grossWeight; }
    public void setGrossWeight(BigDecimal grossWeight) { this.grossWeight = grossWeight; }
    public BigDecimal getPayloadCapacity() { return payloadCapacity; }
    public void setPayloadCapacity(BigDecimal payloadCapacity) { this.payloadCapacity = payloadCapacity; }
    public String getRegistrationCountry() { return registrationCountry; }
    public void setRegistrationCountry(String registrationCountry) { this.registrationCountry = registrationCountry; }
    public LocalDate getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(LocalDate registrationDate) { this.registrationDate = registrationDate; }
    public RegistrationStatus getRegistrationStatus() { return registrationStatus; }
    public void setRegistrationStatus(RegistrationStatus registrationStatus) { this.registrationStatus = registrationStatus; }
    public VerificationStatus getInspectionStatus() { return inspectionStatus; }
    public void setInspectionStatus(VerificationStatus inspectionStatus) { this.inspectionStatus = inspectionStatus; }
    public LocalDate getInspectionExpiryDate() { return inspectionExpiryDate; }
    public void setInspectionExpiryDate(LocalDate inspectionExpiryDate) { this.inspectionExpiryDate = inspectionExpiryDate; }
    public VerificationStatus getInsuranceStatus() { return insuranceStatus; }
    public void setInsuranceStatus(VerificationStatus insuranceStatus) { this.insuranceStatus = insuranceStatus; }
    public LocalDate getInsuranceExpiryDate() { return insuranceExpiryDate; }
    public void setInsuranceExpiryDate(LocalDate insuranceExpiryDate) { this.insuranceExpiryDate = insuranceExpiryDate; }
    public TaxStatus getTaxStatus() { return taxStatus; }
    public void setTaxStatus(TaxStatus taxStatus) { this.taxStatus = taxStatus; }
    public String getHomologationDocuments() { return homologationDocuments; }
    public void setHomologationDocuments(String homologationDocuments) { this.homologationDocuments = homologationDocuments; }
    public String getComplianceCertificates() { return complianceCertificates; }
    public void setComplianceCertificates(String complianceCertificates) { this.complianceCertificates = complianceCertificates; }
    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }
    public OwnershipType getOwnershipType() { return ownershipType; }
    public void setOwnershipType(OwnershipType ownershipType) { this.ownershipType = ownershipType; }
    public String getLeasingDetails() { return leasingDetails; }
    public void setLeasingDetails(String leasingDetails) { this.leasingDetails = leasingDetails; }
    public UsageCategory getUsageCategory() { return usageCategory; }
    public void setUsageCategory(UsageCategory usageCategory) { this.usageCategory = usageCategory; }
    public Long getCurrentMileage() { return currentMileage; }
    public void setCurrentMileage(Long currentMileage) { this.currentMileage = currentMileage; }
    public Long getAverageAnnualMileage() { return averageAnnualMileage; }
    public void setAverageAnnualMileage(Long averageAnnualMileage) { this.averageAnnualMileage = averageAnnualMileage; }
    public OperatingRegion getOperatingRegion() { return operatingRegion; }
    public void setOperatingRegion(OperatingRegion operatingRegion) { this.operatingRegion = operatingRegion; }
    public String getDriverAssignments() { return driverAssignments; }
    public void setDriverAssignments(String driverAssignments) { this.driverAssignments = driverAssignments; }
    public ConditionStatus getConditionStatus() { return conditionStatus; }
    public void setConditionStatus(ConditionStatus conditionStatus) { this.conditionStatus = conditionStatus; }
    public MaintenanceSchedule getMaintenanceSchedule() { return maintenanceSchedule; }
    public void setMaintenanceSchedule(MaintenanceSchedule maintenanceSchedule) { this.maintenanceSchedule = maintenanceSchedule; }
    public LocalDate getLastServiceDate() { return lastServiceDate; }
    public void setLastServiceDate(LocalDate lastServiceDate) { this.lastServiceDate = lastServiceDate; }
    public LocalDate getNextServiceDue() { return nextServiceDue; }
    public void setNextServiceDue(LocalDate nextServiceDue) { this.nextServiceDue = nextServiceDue; }
    public String getServiceHistory() { return serviceHistory; }
    public void setServiceHistory(String serviceHistory) { this.serviceHistory = serviceHistory; }
    public String getAccidentHistory() { return accidentHistory; }
    public void setAccidentHistory(String accidentHistory) { this.accidentHistory = accidentHistory; }
    public String getFaultCodes() { return faultCodes; }
    public void setFaultCodes(String faultCodes) { this.faultCodes = faultCodes; }
    public String getWarrantyStatus() { return warrantyStatus; }
    public void setWarrantyStatus(String warrantyStatus) { this.warrantyStatus = warrantyStatus; }
    public BigDecimal getPurchasePrice() { return purchasePrice; }
    public void setPurchasePrice(BigDecimal purchasePrice) { this.purchasePrice = purchasePrice; }
    public LocalDate getPurchaseDate() { return purchaseDate; }
    public void setPurchaseDate(LocalDate purchaseDate) { this.purchaseDate = purchaseDate; }
    public BigDecimal getCurrentMarketValue() { return currentMarketValue; }
    public void setCurrentMarketValue(BigDecimal currentMarketValue) { this.currentMarketValue = currentMarketValue; }
    public LocalDate getValuationDate() { return valuationDate; }
    public void setValuationDate(LocalDate valuationDate) { this.valuationDate = valuationDate; }
    public DepreciationMethod getDepreciationMethod() { return depreciationMethod; }
    public void setDepreciationMethod(DepreciationMethod depreciationMethod) { this.depreciationMethod = depreciationMethod; }
    public BigDecimal getDepreciationRate() { return depreciationRate; }
    public void setDepreciationRate(BigDecimal depreciationRate) { this.depreciationRate = depreciationRate; }
    public BigDecimal getOperatingCosts() { return operatingCosts; }
    public void setOperatingCosts(BigDecimal operatingCosts) { this.operatingCosts = operatingCosts; }
    public BigDecimal getInsuranceCost() { return insuranceCost; }
    public void setInsuranceCost(BigDecimal insuranceCost) { this.insuranceCost = insuranceCost; }
    public BigDecimal getTaxCost() { return taxCost; }
    public void setTaxCost(BigDecimal taxCost) { this.taxCost = taxCost; }
    public BigDecimal getMechanicalRiskScore() { return mechanicalRiskScore; }
    public void setMechanicalRiskScore(BigDecimal mechanicalRiskScore) { this.mechanicalRiskScore = mechanicalRiskScore; }
    public BigDecimal getAccidentRiskScore() { return accidentRiskScore; }
    public void setAccidentRiskScore(BigDecimal accidentRiskScore) { this.accidentRiskScore = accidentRiskScore; }
    public BigDecimal getOperationalRiskScore() { return operationalRiskScore; }
    public void setOperationalRiskScore(BigDecimal operationalRiskScore) { this.operationalRiskScore = operationalRiskScore; }
    public BigDecimal getComplianceRiskScore() { return complianceRiskScore; }
    public void setComplianceRiskScore(BigDecimal complianceRiskScore) { this.complianceRiskScore = complianceRiskScore; }
    public BigDecimal getEnvironmentalImpactScore() { return environmentalImpactScore; }
    public void setEnvironmentalImpactScore(BigDecimal environmentalImpactScore) { this.environmentalImpactScore = environmentalImpactScore; }
    public BigDecimal getOverallAssessmentScore() { return overallAssessmentScore; }
    public void setOverallAssessmentScore(BigDecimal overallAssessmentScore) { this.overallAssessmentScore = overallAssessmentScore; }
    public String getComplianceFlags() { return complianceFlags; }
    public void setComplianceFlags(String complianceFlags) { this.complianceFlags = complianceFlags; }
    public String getRegistrationDocuments() { return registrationDocuments; }
    public void setRegistrationDocuments(String registrationDocuments) { this.registrationDocuments = registrationDocuments; }
    public String getInsuranceDocuments() { return insuranceDocuments; }
    public void setInsuranceDocuments(String insuranceDocuments) { this.insuranceDocuments = insuranceDocuments; }
    public String getInspectionReports() { return inspectionReports; }
    public void setInspectionReports(String inspectionReports) { this.inspectionReports = inspectionReports; }
    public String getServiceInvoices() { return serviceInvoices; }
    public void setServiceInvoices(String serviceInvoices) { this.serviceInvoices = serviceInvoices; }
    public String getAccidentReports() { return accidentReports; }
    public void setAccidentReports(String accidentReports) { this.accidentReports = accidentReports; }
    public String getPhotos() { return photos; }
    public void setPhotos(String photos) { this.photos = photos; }
    public String getInternalNotes() { return internalNotes; }
    public void setInternalNotes(String internalNotes) { this.internalNotes = internalNotes; }
    public String getDataSource() { return dataSource; }
    public void setDataSource(String dataSource) { this.dataSource = dataSource; }
    public String getResponsibleAgent() { return responsibleAgent; }
    public void setResponsibleAgent(String responsibleAgent) { this.responsibleAgent = responsibleAgent; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
