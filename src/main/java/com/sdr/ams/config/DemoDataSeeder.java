package com.sdr.ams.config;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.sdr.ams.model.financial.BankAccount;
import com.sdr.ams.model.financial.Bond;
import com.sdr.ams.model.financial.Stock;
import com.sdr.ams.model.intangible.Brand;
import com.sdr.ams.model.intangible.Copyright;
import com.sdr.ams.model.intangible.Patent;
import com.sdr.ams.model.intangible.Reputation;
import com.sdr.ams.model.intangible.Trademark;
import com.sdr.ams.model.tangible.Cash;
import com.sdr.ams.model.tangible.Inventory;
import com.sdr.ams.model.tangible.Machinery;
import com.sdr.ams.model.tangible.RealEstate;
import com.sdr.ams.model.tangible.Vehicle;
import com.sdr.ams.repository.BankAccountRepository;
import com.sdr.ams.repository.BondRepository;
import com.sdr.ams.repository.BrandRepository;
import com.sdr.ams.repository.CashRepository;
import com.sdr.ams.repository.CopyrightRepository;
import com.sdr.ams.repository.InventoryRepository;
import com.sdr.ams.repository.MachineryRepository;
import com.sdr.ams.repository.PatentRepository;
import com.sdr.ams.repository.RealEstateRepository;
import com.sdr.ams.repository.ReputationRepository;
import com.sdr.ams.repository.StockRepository;
import com.sdr.ams.repository.TrademarkRepository;
import com.sdr.ams.repository.VehicleRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@EnableConfigurationProperties(DemoDataProperties.class)
public class DemoDataSeeder {

    private final DemoDataProperties demoDataProperties;

    public DemoDataSeeder(DemoDataProperties demoDataProperties) {
        this.demoDataProperties = demoDataProperties;
    }

    @Bean
    @ConditionalOnProperty(name = "app.demo-data.enabled", havingValue = "true", matchIfMissing = true)
    ApplicationRunner seedDemoData(
        BankAccountRepository bankAccountRepository,
        BondRepository bondRepository,
        StockRepository stockRepository,
        RealEstateRepository realEstateRepository,
        VehicleRepository vehicleRepository,
        TrademarkRepository trademarkRepository,
        CashRepository cashRepository,
        InventoryRepository inventoryRepository,
        MachineryRepository machineryRepository,
        BrandRepository brandRepository,
        CopyrightRepository copyrightRepository,
        PatentRepository patentRepository,
        ReputationRepository reputationRepository
    ) {
        return ignored -> {
            seedBankAccounts(bankAccountRepository);
            seedBonds(bondRepository);
            seedStocks(stockRepository);
            seedRealEstates(realEstateRepository);
            seedVehicles(vehicleRepository);
            seedTrademarks(trademarkRepository);
            seedCash(cashRepository);
            seedInventories(inventoryRepository);
            seedMachineries(machineryRepository);
            seedBrands(brandRepository);
            seedCopyrights(copyrightRepository);
            seedPatents(patentRepository);
            seedReputations(reputationRepository);
        };
    }

    @Transactional
    void seedBankAccounts(BankAccountRepository repository) {
        if (repository.count() > 0) {
            return;
        }

        int recordCount = recordCountFor("bank-accounts");
        for (int i = 1; i <= recordCount; i++) {
            repository.save(newBankAccount(i));
        }
    }

    @Transactional
    void seedBonds(BondRepository repository) {
        if (repository.count() > 0) {
            return;
        }

        int recordCount = recordCountFor("bonds");
        for (int i = 1; i <= recordCount; i++) {
            repository.save(newBond(i));
        }
    }

    @Transactional
    void seedStocks(StockRepository repository) {
        if (repository.count() > 0) {
            return;
        }

        int recordCount = recordCountFor("stocks");
        for (int i = 1; i <= recordCount; i++) {
            repository.save(newStock(i));
        }
    }

    @Transactional
    void seedRealEstates(RealEstateRepository repository) {
        if (repository.count() > 0) {
            return;
        }

        int recordCount = recordCountFor("real-estates");
        for (int i = 1; i <= recordCount; i++) {
            repository.save(newRealEstate(i));
        }
    }

    @Transactional
    void seedVehicles(VehicleRepository repository) {
        if (repository.count() > 0) {
            return;
        }

        int recordCount = recordCountFor("vehicles");
        for (int i = 1; i <= recordCount; i++) {
            repository.save(newVehicle(i));
        }
    }

    @Transactional
    void seedTrademarks(TrademarkRepository repository) {
        if (repository.count() > 0) {
            return;
        }

        int recordCount = recordCountFor("trademarks");
        for (int i = 1; i <= recordCount; i++) {
            repository.save(newTrademark(i));
        }
    }

    @Transactional
    void seedCash(CashRepository repository) {
        if (repository.count() > 0) {
            return;
        }

        int recordCount = recordCountFor("cash");
        for (int i = 1; i <= recordCount; i++) {
            repository.save(newCash(i));
        }
    }

    @Transactional
    void seedInventories(InventoryRepository repository) {
        if (repository.count() > 0) {
            return;
        }

        int recordCount = recordCountFor("inventories");
        for (int i = 1; i <= recordCount; i++) {
            repository.save(newInventory(i));
        }
    }

    @Transactional
    void seedMachineries(MachineryRepository repository) {
        if (repository.count() > 0) {
            return;
        }

        int recordCount = recordCountFor("machineries");
        for (int i = 1; i <= recordCount; i++) {
            repository.save(newMachinery(i));
        }
    }

    @Transactional
    void seedBrands(BrandRepository repository) {
        if (repository.count() > 0) {
            return;
        }

        int recordCount = recordCountFor("brands");
        for (int i = 1; i <= recordCount; i++) {
            repository.save(newBrand(i));
        }
    }

    @Transactional
    void seedCopyrights(CopyrightRepository repository) {
        if (repository.count() > 0) {
            return;
        }

        int recordCount = recordCountFor("copyrights");
        for (int i = 1; i <= recordCount; i++) {
            repository.save(newCopyright(i));
        }
    }

    @Transactional
    void seedPatents(PatentRepository repository) {
        if (repository.count() > 0) {
            return;
        }

        int recordCount = recordCountFor("patents");
        for (int i = 1; i <= recordCount; i++) {
            repository.save(newPatent(i));
        }
    }

    @Transactional
    void seedReputations(ReputationRepository repository) {
        if (repository.count() > 0) {
            return;
        }

        int recordCount = recordCountFor("reputations");
        for (int i = 1; i <= recordCount; i++) {
            repository.save(newReputation(i));
        }
    }

    private int recordCountFor(String entityKey) {
        return demoDataProperties.recordCountFor(entityKey);
    }

    private BankAccount newBankAccount(int i) {
        BankAccount account = new BankAccount();
        String suffix = String.format("%03d", i);
        account.setName("Demo Holder " + suffix);
        account.setAccountType(BankAccount.AccountType.CURRENT);
        account.setBankName("Demo Bank");
        account.setIban("GB82WEST1234567890" + suffix);
        account.setSwiftCode("DEMOGB2L");
        account.setLocalAccountNumber("LCL-" + suffix);
        account.setBranchName("Main Branch");
        account.setCurrency("EUR");
        account.setStatus(BankAccount.Status.ACTIVE);
        account.setOwnershipType(BankAccount.OwnershipType.INDIVIDUAL);
        account.setOpenedAt(LocalDate.now().minusYears(2).minusDays(i));
        account.setAccountPurpose("Presentation account " + suffix);
        return account;
    }

    private Bond newBond(int i) {
        Bond bond = new Bond();
        String suffix = String.format("%03d", i);
        bond.setBondId("BOND-DEMO-" + suffix);
        bond.setTitle("Demo Corporate Bond " + suffix);
        bond.setIssuer("Demo Industries");
        bond.setBondType(Bond.BondType.CORPORATE);
        bond.setCurrency("EUR");
        bond.setDataSource("DEMO_SEED");
        return bond;
    }

    private Stock newStock(int i) {
        Stock stock = new Stock();
        String suffix = String.format("%03d", i);
        stock.setStockId("STK-DEMO-" + suffix);
        stock.setTickerSymbol("DM" + suffix);
        stock.setExchange("NYSE");
        stock.setCompanyName("Demo Technologies " + suffix);
        stock.setDataSource("DEMO_SEED");
        return stock;
    }

    private RealEstate newRealEstate(int i) {
        RealEstate realEstate = new RealEstate();
        String suffix = String.format("%03d", i);
        realEstate.setName("RE-DEMO-" + suffix);
        realEstate.setTitle("Demo Office Building " + suffix);
        realEstate.setPropertyType(RealEstate.PropertyType.COMMERCIAL);
        realEstate.setAddress(i + " Demo Street, Berlin");
        realEstate.setLandArea(new BigDecimal("1500.00"));
        realEstate.setOwnershipType(RealEstate.OwnershipType.COMPANY);
        realEstate.setOwnerName("Demo Holdings GmbH");
        realEstate.setCadastralNumber("CADDEMO" + suffix);
        realEstate.setLandRegistryNumber("LRDEMO" + suffix);
        realEstate.setCurrentMarketValue(new BigDecimal("2500000.00").add(BigDecimal.valueOf(i * 1000L)));
        realEstate.setValuationDate(LocalDate.now().minusDays(i));
        realEstate.setDataSource("DEMO_SEED");
        return realEstate;
    }

    private Vehicle newVehicle(int i) {
        Vehicle vehicle = new Vehicle();
        String suffix = String.format("%03d", i);
        vehicle.setVehicleId("VEH-DEMO-" + suffix);
        vehicle.setVin(String.format("WVWZZZ1JZXW%06d", i));
        vehicle.setLicensePlate("B-DEMO-" + suffix);
        vehicle.setVehicleType(Vehicle.VehicleType.CAR);
        vehicle.setMake("Volkswagen");
        vehicle.setModel("Passat");
        vehicle.setYearOfManufacture(2020 + (i % 5));
        vehicle.setEngineType(Vehicle.EngineType.DIESEL);
        vehicle.setRegistrationCountry("DE");
        vehicle.setDataSource("DEMO_SEED");
        return vehicle;
    }

    private Trademark newTrademark(int i) {
        Trademark trademark = new Trademark();
        String suffix = String.format("%03d", i);
        trademark.setTrademarkId("TM-DEMO-" + suffix);
        trademark.setMarkName("DEMOBRAND-" + suffix);
        trademark.setMarkType(Trademark.MarkType.WORDMARK);
        trademark.setOwnerName("Demo Holdings GmbH");
        trademark.setOwnerType(Trademark.OwnerType.CORPORATION);
        trademark.setApplicationNumber("APP-DEMO-" + suffix);
        trademark.setApplicationDate(LocalDate.now().minusYears(1).minusDays(i));
        trademark.setLegalStatus(Trademark.LegalStatus.FILED);
        trademark.setPriorityClaimed(false);
        trademark.setDataSource("DEMO_SEED");
        return trademark;
    }

    private Cash newCash(int i) {
        Cash cash = new Cash();
        String suffix = String.format("%03d", i);
        cash.setCashId("CASH-DEMO-" + suffix);
        cash.setAmount(new BigDecimal("125000.00").add(BigDecimal.valueOf(i * 100L)));
        cash.setCurrency("EUR");
        cash.setValuationDate(LocalDate.now().minusDays(i));
        cash.setCashType(Cash.CashType.OPERATING);
        cash.setHolderOwner("Demo Treasury " + suffix);
        cash.setSourceOfData("DEMO_SEED");
        return cash;
    }

    private Inventory newInventory(int i) {
        Inventory inventory = new Inventory();
        String suffix = String.format("%03d", i);
        inventory.setInventoryId("INV-DEMO-" + suffix);
        inventory.setCategory(Inventory.Category.INDUSTRIAL);
        inventory.setManufacturer("Demo Manufacturing");
        inventory.setModel("INV-X" + suffix);
        inventory.setSerialNumber("INV-SN-" + suffix);
        inventory.setYearOfManufacture(2020 + (i % 5));
        inventory.setDataSource("DEMO_SEED");
        return inventory;
    }

    private Machinery newMachinery(int i) {
        Machinery machinery = new Machinery();
        String suffix = String.format("%03d", i);
        machinery.setMachineId("MCH-DEMO-" + suffix);
        machinery.setCategory(Machinery.Category.CONSTRUCTION);
        machinery.setManufacturer("Caterpillar");
        machinery.setModel("CAT-" + suffix);
        machinery.setSerialNumber("MCH-SN-" + suffix);
        machinery.setYearOfManufacture(2019 + (i % 6));
        machinery.setDataSource("DEMO_SEED");
        return machinery;
    }

    private Brand newBrand(int i) {
        Brand brand = new Brand();
        String suffix = String.format("%03d", i);
        brand.setBrandId("BRAND-DEMO-" + suffix);
        brand.setBrandName("DemoBrand " + suffix);
        brand.setParentCompanyOwner("Demo Holdings GmbH");
        brand.setCountryOfOrigin("Germany");
        brand.setStatus(Brand.BrandStatus.ACTIVE);
        return brand;
    }

    private Copyright newCopyright(int i) {
        Copyright copyright = new Copyright();
        String suffix = String.format("%03d", i);
        copyright.setCopyrightId("CPR-DEMO-" + suffix);
        copyright.setTitle("Asset Management Platform Manual " + suffix);
        copyright.setWorkType(Copyright.WorkType.TEXT);
        copyright.setAuthors("Demo Author " + suffix);
        copyright.setCopyrightOwners("Demo Holdings GmbH");
        copyright.setCountryOfOrigin("Germany");
        copyright.setCopyrightStatus(Copyright.CopyrightStatus.ACTIVE);
        copyright.setSourceOfInformation("DEMO_SEED");
        return copyright;
    }

    private Patent newPatent(int i) {
        Patent patent = new Patent();
        String suffix = String.format("%03d", i);
        patent.setPatentId("PAT-DEMO-" + suffix);
        patent.setTitle("Smart Asset Tracking Method " + suffix);
        patent.setPatentType(Patent.PatentType.UTILITY);
        patent.setAssigneeOwner("Demo Holdings GmbH");
        patent.setApplicationNumber("PAT-APP-" + suffix);
        patent.setApplicationDate(LocalDate.now().minusYears(2).minusDays(i));
        patent.setLegalStatus(Patent.LegalStatus.PENDING);
        patent.setDataSource("DEMO_SEED");
        return patent;
    }

    private Reputation newReputation(int i) {
        Reputation reputation = new Reputation();
        String suffix = String.format("%03d", i);
        reputation.setReputationId("REP-DEMO-" + suffix);
        reputation.setEntityType(Reputation.EntityType.COMPANY);
        reputation.setEntityId("DEMO-HOLDINGS-" + suffix);
        reputation.setDisplayName("Demo Holdings GmbH " + suffix);
        reputation.setDataSource("DEMO_SEED");
        return reputation;
    }
}

