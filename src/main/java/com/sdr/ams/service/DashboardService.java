package com.sdr.ams.service;

import com.sdr.ams.model.financial.*;
import com.sdr.ams.model.tangible.*;
import com.sdr.ams.model.intangible.*;
import com.sdr.ams.repository.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;

@Service
public class DashboardService {

    public record RecentActivity(String entityType, String category, String displayName, Instant timestamp, String link) {
        public String formattedTimestamp() {
            if (timestamp == null) return "—";
            return timestamp.toString().replace("T", " ").substring(0, 16);
        }
    }

    public record AlertItem(String severity, String category, String message, String link) {}

    public record CategoryStats(String name, long count, BigDecimal value, String color, String icon) {}

    public record DashboardStats(
        long totalAssets,
        BigDecimal totalPortfolioValue,
        List<CategoryStats> categories,
        Map<String, Long> entityCounts,
        Map<String, BigDecimal> entityValues,
        List<RecentActivity> recentActivity,
        List<AlertItem> alerts
    ) {}

    private final BankAccountRepository bankAccountRepo;
    private final BondRepository bondRepo;
    private final InvoiceRepository invoiceRepo;
    private final StockRepository stockRepo;
    private final CashRepository cashRepo;
    private final InventoryRepository inventoryRepo;
    private final MachineryRepository machineryRepo;
    private final RealEstateRepository realEstateRepo;
    private final VehicleRepository vehicleRepo;
    private final BrandRepository brandRepo;
    private final CopyrightRepository copyrightRepo;
    private final PatentRepository patentRepo;
    private final ReputationRepository reputationRepo;
    private final TrademarkRepository trademarkRepo;

    public DashboardService(
        BankAccountRepository bankAccountRepo, BondRepository bondRepo,
        InvoiceRepository invoiceRepo, StockRepository stockRepo,
        CashRepository cashRepo, InventoryRepository inventoryRepo,
        MachineryRepository machineryRepo, RealEstateRepository realEstateRepo,
        VehicleRepository vehicleRepo, BrandRepository brandRepo,
        CopyrightRepository copyrightRepo, PatentRepository patentRepo,
        ReputationRepository reputationRepo, TrademarkRepository trademarkRepo
    ) {
        this.bankAccountRepo = bankAccountRepo;
        this.bondRepo = bondRepo;
        this.invoiceRepo = invoiceRepo;
        this.stockRepo = stockRepo;
        this.cashRepo = cashRepo;
        this.inventoryRepo = inventoryRepo;
        this.machineryRepo = machineryRepo;
        this.realEstateRepo = realEstateRepo;
        this.vehicleRepo = vehicleRepo;
        this.brandRepo = brandRepo;
        this.copyrightRepo = copyrightRepo;
        this.patentRepo = patentRepo;
        this.reputationRepo = reputationRepo;
        this.trademarkRepo = trademarkRepo;
    }

    public DashboardStats buildStats() {
        // --- Counts ---
        long bankAccountCount = bankAccountRepo.count();
        long bondCount = bondRepo.count();
        long invoiceCount = invoiceRepo.count();
        long stockCount = stockRepo.count();
        long cashCount = cashRepo.count();
        long inventoryCount = inventoryRepo.count();
        long machineryCount = machineryRepo.count();
        long realEstateCount = realEstateRepo.count();
        long vehicleCount = vehicleRepo.count();
        long brandCount = brandRepo.count();
        long copyrightCount = copyrightRepo.count();
        long patentCount = patentRepo.count();
        long reputationCount = reputationRepo.count();
        long trademarkCount = trademarkRepo.count();

        long financialCount = bankAccountCount + bondCount + invoiceCount + stockCount;
        long tangibleCount = cashCount + inventoryCount + machineryCount + realEstateCount + vehicleCount;
        long intangibleCount = brandCount + copyrightCount + patentCount + reputationCount + trademarkCount;
        long totalAssets = financialCount + tangibleCount + intangibleCount;

        // --- Portfolio Values ---
        BigDecimal bondValue      = sum(bondRepo.findAll().stream().map(Bond::getFaceValue));
        BigDecimal stockValue     = sum(stockRepo.findAll().stream().map(Stock::getMarketCap));
        BigDecimal invoiceValue   = sum(invoiceRepo.findAll().stream().map(Invoice::getTotalAmount));
        BigDecimal realEstateValue= sum(realEstateRepo.findAll().stream().map(RealEstate::getCurrentMarketValue));
        BigDecimal vehicleValue   = sum(vehicleRepo.findAll().stream().map(Vehicle::getCurrentMarketValue));
        BigDecimal cashValue      = sum(cashRepo.findAll().stream().map(Cash::getAmount));
        BigDecimal inventoryValue = sum(inventoryRepo.findAll().stream().map(Inventory::getBookValue));
        BigDecimal machineryValue = sum(machineryRepo.findAll().stream().map(Machinery::getBookValue));
        BigDecimal brandValue     = sum(brandRepo.findAll().stream().map(Brand::getBrandValuation));
        BigDecimal copyrightValue = sum(copyrightRepo.findAll().stream().map(Copyright::getValuation));
        BigDecimal patentValue    = sum(patentRepo.findAll().stream().map(Patent::getValuation));
        BigDecimal trademarkValue = sum(trademarkRepo.findAll().stream().map(Trademark::getValuation));

        BigDecimal financialValue  = bondValue.add(stockValue).add(invoiceValue);
        BigDecimal tangibleValue   = realEstateValue.add(vehicleValue).add(cashValue).add(inventoryValue).add(machineryValue);
        BigDecimal intangibleValue = brandValue.add(copyrightValue).add(patentValue).add(trademarkValue);

        BigDecimal totalPortfolioValue = financialValue.add(tangibleValue).add(intangibleValue);

        // --- Category stats ---
        List<CategoryStats> categories = List.of(
            new CategoryStats("Financial", financialCount, financialValue, "#4f46e5", "💳"),
            new CategoryStats("Tangible", tangibleCount, tangibleValue, "#0891b2", "🏗️"),
            new CategoryStats("Intangible", intangibleCount, intangibleValue, "#059669", "💡")
        );

        // --- Entity counts map ---
        Map<String, Long> entityCounts = new LinkedHashMap<>();
        entityCounts.put("Bank Accounts", bankAccountCount);
        entityCounts.put("Bonds", bondCount);
        entityCounts.put("Invoices", invoiceCount);
        entityCounts.put("Stocks", stockCount);
        entityCounts.put("Cash", cashCount);
        entityCounts.put("Inventory", inventoryCount);
        entityCounts.put("Machinery", machineryCount);
        entityCounts.put("Real Estate", realEstateCount);
        entityCounts.put("Vehicles", vehicleCount);
        entityCounts.put("Brands", brandCount);
        entityCounts.put("Copyrights", copyrightCount);
        entityCounts.put("Patents", patentCount);
        entityCounts.put("Reputations", reputationCount);
        entityCounts.put("Trademarks", trademarkCount);

        // --- Entity values map ---
        Map<String, BigDecimal> entityValues = new LinkedHashMap<>();
        entityValues.put("Bank Accounts", BigDecimal.ZERO);
        entityValues.put("Bonds", bondValue);
        entityValues.put("Invoices", invoiceValue);
        entityValues.put("Stocks", stockValue);
        entityValues.put("Cash", cashValue);
        entityValues.put("Inventory", inventoryValue);
        entityValues.put("Machinery", machineryValue);
        entityValues.put("Real Estate", realEstateValue);
        entityValues.put("Vehicles", vehicleValue);
        entityValues.put("Brands", brandValue);
        entityValues.put("Copyrights", copyrightValue);
        entityValues.put("Patents", patentValue);
        entityValues.put("Reputations", BigDecimal.ZERO);
        entityValues.put("Trademarks", trademarkValue);

        // --- Recent Activity (last 10 by updatedAt across all entities) ---
        List<RecentActivity> recent = new ArrayList<>();
        bankAccountRepo.findAll().forEach(e -> recent.add(new RecentActivity("Bank Account", "Financial", e.getName(), e.getUpdatedAt(), "/bank-accounts/" + e.getId())));
        bondRepo.findAll().forEach(e -> recent.add(new RecentActivity("Bond", "Financial", e.getBondId(), e.getUpdatedAt(), "/bonds/" + e.getId())));
        invoiceRepo.findAll().forEach(e -> recent.add(new RecentActivity("Invoice", "Financial", e.getInvoiceNumber(), e.getUpdatedAt(), "/invoices/" + e.getId())));
        stockRepo.findAll().forEach(e -> recent.add(new RecentActivity("Stock", "Financial", e.getStockId(), e.getUpdatedAt(), "/stocks/" + e.getId())));
        cashRepo.findAll().forEach(e -> recent.add(new RecentActivity("Cash", "Tangible", e.getCashId(), e.getUpdatedAt(), "/cash/" + e.getId())));
        inventoryRepo.findAll().forEach(e -> recent.add(new RecentActivity("Inventory", "Tangible", e.getInventoryId(), e.getUpdatedAt(), "/inventories/" + e.getId())));
        machineryRepo.findAll().forEach(e -> recent.add(new RecentActivity("Machinery", "Tangible", e.getMachineId(), e.getUpdatedAt(), "/machineries/" + e.getId())));
        realEstateRepo.findAll().forEach(e -> recent.add(new RecentActivity("Real Estate", "Tangible", e.getTitle(), e.getUpdatedAt(), "/real-estates/" + e.getId())));
        vehicleRepo.findAll().forEach(e -> recent.add(new RecentActivity("Vehicle", "Tangible", e.getVehicleId(), e.getUpdatedAt(), "/vehicles/" + e.getId())));
        brandRepo.findAll().forEach(e -> recent.add(new RecentActivity("Brand", "Intangible", e.getBrandId(), e.getUpdatedAt(), "/brands/" + e.getId())));
        copyrightRepo.findAll().forEach(e -> recent.add(new RecentActivity("Copyright", "Intangible", e.getCopyrightId(), e.getUpdatedAt(), "/copyrights/" + e.getId())));
        patentRepo.findAll().forEach(e -> recent.add(new RecentActivity("Patent", "Intangible", e.getPatentId(), e.getUpdatedAt(), "/patents/" + e.getId())));
        reputationRepo.findAll().forEach(e -> recent.add(new RecentActivity("Reputation", "Intangible", e.getReputationId(), e.getUpdatedAt(), "/reputations/" + e.getId())));
        trademarkRepo.findAll().forEach(e -> recent.add(new RecentActivity("Trademark", "Intangible", e.getTrademarkId(), e.getUpdatedAt(), "/trademarks/" + e.getId())));

        recent.sort(Comparator.comparing(RecentActivity::timestamp, Comparator.nullsLast(Comparator.reverseOrder())));
        List<RecentActivity> top10 = recent.stream().limit(10).toList();

        // --- Alerts ---
        LocalDate today = LocalDate.now();
        List<AlertItem> alerts = new ArrayList<>();

        invoiceRepo.findOverdue(today).forEach(i ->
            alerts.add(new AlertItem("danger", "Invoice",
                "Invoice " + i.getInvoiceNumber() + " was due on " + i.getDueDate() + " and is unpaid",
                "/invoices/" + i.getId())));

        patentRepo.findExpiringBefore(today.plusDays(90)).forEach(p ->
            alerts.add(new AlertItem(p.getExpiryDate().isBefore(today) ? "danger" : "warning", "Patent",
                "Patent " + p.getPatentId() + " expires on " + p.getExpiryDate(),
                "/patents/" + p.getId())));

        trademarkRepo.findExpiringBefore(today.plusDays(90)).forEach(t ->
            alerts.add(new AlertItem(t.getExpirationDate().isBefore(today) ? "danger" : "warning", "Trademark",
                "Trademark " + t.getTrademarkId() + " expires on " + t.getExpirationDate(),
                "/trademarks/" + t.getId())));

        vehicleRepo.findInspectionDueBefore(today.plusDays(30)).forEach(v ->
            alerts.add(new AlertItem(v.getInspectionExpiryDate().isBefore(today) ? "danger" : "warning", "Vehicle",
                "Vehicle " + v.getVehicleId() + " inspection expires on " + v.getInspectionExpiryDate(),
                "/vehicles/" + v.getId())));

        alerts.sort(Comparator.comparing(a -> a.severity().equals("danger") ? 0 : 1));

        return new DashboardStats(totalAssets, totalPortfolioValue, categories, entityCounts, entityValues, top10, alerts);
    }

    private BigDecimal sum(Stream<BigDecimal> stream) {
        return stream.filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}




