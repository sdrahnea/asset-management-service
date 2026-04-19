package com.sdr.ams.controller;

import com.sdr.ams.model.core.ImportRow;
import com.sdr.ams.model.financial.BankAccount;
import com.sdr.ams.model.financial.Bond;
import com.sdr.ams.model.financial.Invoice;
import com.sdr.ams.model.financial.InvoiceItem;
import com.sdr.ams.model.financial.InvoiceParty;
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
import com.sdr.ams.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;

/**
 * Handles bulk CSV/Excel import for all 14 entity types.
 * Routes: GET  /import/{entity}             - upload form
 *         GET  /import/{entity}/template     - download CSV template
 *         GET  /import/{entity}/template-xlsx- download Excel template
 *         POST /import/{entity}/preview      - parse file, store in session, show preview
 *         POST /import/{entity}/commit       - commit valid rows
 */
@Controller
@RequestMapping("/import")
public class ImportController {

    private final ImportService importService;
    private final TransactionTemplate transactionTemplate;

    // Rich services
    private final BankAccountService bankAccountService;
    private final BondService bondService;
    private final InvoiceService invoiceService;
    private final PatentService patentService;
    private final RealEstateService realEstateService;
    private final ReputationService reputationService;
    private final StockService stockService;
    private final TrademarkService trademarkService;
    private final VehicleService vehicleService;

    // Generic services
    private final BrandService brandService;
    private final CashService cashService;
    private final CopyrightService copyrightService;
    private final InventoryService inventoryService;
    private final MachineryService machineryService;

    /** Describes import metadata for a single entity type. */
    record EntitySpec(
        String entityTitle,
        String listPath,
        List<String> headers,
        Function<Map<String, String>, String> creator  // returns null on success, error message on failure
    ) {}

    private final Map<String, EntitySpec> registry;

    public ImportController(
        ImportService importService,
        PlatformTransactionManager transactionManager,
        BankAccountService bankAccountService,
        BondService bondService,
        InvoiceService invoiceService,
        PatentService patentService,
        RealEstateService realEstateService,
        ReputationService reputationService,
        StockService stockService,
        TrademarkService trademarkService,
        VehicleService vehicleService,
        BrandService brandService,
        CashService cashService,
        CopyrightService copyrightService,
        InventoryService inventoryService,
        MachineryService machineryService
    ) {
        this.importService = importService;
        this.transactionTemplate = new TransactionTemplate(transactionManager);
        this.bankAccountService = bankAccountService;
        this.bondService = bondService;
        this.invoiceService = invoiceService;
        this.patentService = patentService;
        this.realEstateService = realEstateService;
        this.reputationService = reputationService;
        this.stockService = stockService;
        this.trademarkService = trademarkService;
        this.vehicleService = vehicleService;
        this.brandService = brandService;
        this.cashService = cashService;
        this.copyrightService = copyrightService;
        this.inventoryService = inventoryService;
        this.machineryService = machineryService;

        this.registry = buildRegistry();
    }

    private Map<String, EntitySpec> buildRegistry() {
        Map<String, EntitySpec> map = new LinkedHashMap<>();
        map.put("bank-accounts", new EntitySpec("Bank Account", "/bank-accounts", importService.buildHeaders(BankAccount.class),
            row -> tryCreate(row, BankAccount.class, bankAccountService::create)));
        map.put("bonds", new EntitySpec("Bond", "/bonds", importService.buildHeaders(Bond.class),
            row -> tryCreate(row, Bond.class, bondService::create)));
        map.put("invoices", new EntitySpec("Invoice", "/invoices", invoiceHeaders(),
            this::tryCreateInvoice));
        map.put("patents", new EntitySpec("Patent", "/patents", importService.buildHeaders(Patent.class),
            row -> tryCreate(row, Patent.class, patentService::create)));
        map.put("real-estates", new EntitySpec("Real Estate", "/real-estates", importService.buildHeaders(RealEstate.class),
            row -> tryCreate(row, RealEstate.class, realEstateService::create)));
        map.put("reputations", new EntitySpec("Reputation", "/reputations", importService.buildHeaders(Reputation.class),
            row -> tryCreate(row, Reputation.class, reputationService::create)));
        map.put("stocks", new EntitySpec("Stock", "/stocks", importService.buildHeaders(Stock.class),
            row -> tryCreate(row, Stock.class, stockService::create)));
        map.put("trademarks", new EntitySpec("Trademark", "/trademarks", importService.buildHeaders(Trademark.class),
            row -> tryCreate(row, Trademark.class, trademarkService::create)));
        map.put("vehicles", new EntitySpec("Vehicle", "/vehicles", importService.buildHeaders(Vehicle.class),
            row -> tryCreate(row, Vehicle.class, vehicleService::create)));
        map.put("brands", new EntitySpec("Brand", "/brands", importService.buildHeaders(Brand.class),
            row -> tryCreate(row, Brand.class, brandService::create)));
        map.put("cash", new EntitySpec("Cash", "/cash", importService.buildHeaders(Cash.class),
            row -> tryCreate(row, Cash.class, cashService::create)));
        map.put("copyrights", new EntitySpec("Copyright", "/copyrights", importService.buildHeaders(Copyright.class),
            row -> tryCreate(row, Copyright.class, copyrightService::create)));
        map.put("inventories", new EntitySpec("Inventory", "/inventories", importService.buildHeaders(Inventory.class),
            row -> tryCreate(row, Inventory.class, inventoryService::create)));
        map.put("machineries", new EntitySpec("Machinery", "/machineries", importService.buildHeaders(Machinery.class),
            row -> tryCreate(row, Machinery.class, machineryService::create)));
        return Collections.unmodifiableMap(map);
    }

    private List<String> invoiceHeaders() {
        return List.of(
            "Invoice Id", "Invoice Number", "Issue Date", "Due Date", "Currency", "Status",
            "Seller Id", "Seller Name", "Seller Tax Id", "Seller Address", "Seller Contact Info",
            "Buyer Id", "Buyer Name", "Buyer Tax Id", "Buyer Address", "Buyer Contact Info",
            "Item Id", "Item Description", "Quantity", "Unit Price", "Unit Of Measure", "Tax Rate",
            "Subtotal", "Total Tax", "Total Amount", "Amount Paid", "Balance Due",
            "Payment Terms", "Payment Method", "Bank Account Iban"
        );
    }

    private String tryCreateInvoice(Map<String, String> row) {
        try {
            Invoice invoice = new Invoice();
            invoice.setInvoiceId(get(row, "Invoice Id"));
            invoice.setInvoiceNumber(get(row, "Invoice Number"));
            invoice.setIssueDate(parseDate(get(row, "Issue Date")));
            invoice.setDueDate(parseDate(get(row, "Due Date")));
            invoice.setCurrency(get(row, "Currency"));

            String status = get(row, "Status");
            if (status != null) {
                invoice.setStatus(Invoice.InvoiceStatus.valueOf(status.trim().toUpperCase(Locale.ROOT)));
            }

            InvoiceParty seller = new InvoiceParty();
            seller.setPartyId(get(row, "Seller Id"));
            seller.setName(get(row, "Seller Name"));
            seller.setTaxId(get(row, "Seller Tax Id"));
            seller.setAddress(get(row, "Seller Address"));
            seller.setContactInfo(get(row, "Seller Contact Info"));
            invoice.setSeller(seller);

            InvoiceParty buyer = new InvoiceParty();
            buyer.setPartyId(get(row, "Buyer Id"));
            buyer.setName(get(row, "Buyer Name"));
            buyer.setTaxId(get(row, "Buyer Tax Id"));
            buyer.setAddress(get(row, "Buyer Address"));
            buyer.setContactInfo(get(row, "Buyer Contact Info"));
            invoice.setBuyer(buyer);

            InvoiceItem item = new InvoiceItem();
            item.setItemId(get(row, "Item Id"));
            item.setDescription(get(row, "Item Description"));
            item.setQuantity(parseDecimal(get(row, "Quantity")));
            item.setUnitPrice(parseDecimal(get(row, "Unit Price")));
            item.setUnitOfMeasure(get(row, "Unit Of Measure"));
            item.setTaxRate(parseDecimal(get(row, "Tax Rate")));
            invoice.setItems(new ArrayList<>(List.of(item)));

            invoice.setSubtotal(parseDecimal(get(row, "Subtotal")));
            invoice.setTotalTax(parseDecimal(get(row, "Total Tax")));
            invoice.setTotalAmount(parseDecimal(get(row, "Total Amount")));
            invoice.setAmountPaid(parseDecimal(get(row, "Amount Paid")));
            invoice.setBalanceDue(parseDecimal(get(row, "Balance Due")));
            invoice.setPaymentTerms(get(row, "Payment Terms"));

            String method = get(row, "Payment Method");
            if (method != null) {
                invoice.setPaymentMethod(Invoice.PaymentMethod.valueOf(method.trim().toUpperCase(Locale.ROOT)));
            }
            invoice.setBankAccountIban(get(row, "Bank Account Iban"));

            invoiceService.create(invoice);
            return null;
        } catch (Exception e) {
            return e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName();
        }
    }

    private <T> String tryCreate(Map<String, String> row, Class<T> entityClass, Function<T, T> creator) {
        try {
            T entity = importService.instantiate(entityClass, row);
            creator.apply(entity);
            return null; // success
        } catch (Exception e) {
            return e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName();
        }
    }

    // ── Endpoints ─────────────────────────────────────────────────────────────

    @GetMapping
    public String importHome(Model model) {
        model.addAttribute("entities", registry.entrySet());
        return "import/index";
    }

    @GetMapping("/{entityPath}")
    public String uploadForm(@PathVariable String entityPath, Model model) {
        EntitySpec spec = getSpecOr404(entityPath);
        model.addAttribute("entityPath", entityPath);
        model.addAttribute("entityTitle", spec.entityTitle());
        model.addAttribute("listPath", spec.listPath());
        model.addAttribute("columns", spec.headers());
        return "import/upload";
    }

    @GetMapping("/{entityPath}/template")
    public ResponseEntity<byte[]> downloadTemplateCsv(@PathVariable String entityPath) {
        EntitySpec spec = getSpecOr404(entityPath);
        byte[] data = importService.generateTemplateCsv(spec.headers());
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + entityPath + "-template.csv\"")
            .contentType(MediaType.parseMediaType("text/csv; charset=UTF-8"))
            .body(data);
    }

    @GetMapping("/{entityPath}/template-xlsx")
    public ResponseEntity<byte[]> downloadTemplateExcel(@PathVariable String entityPath) throws Exception {
        EntitySpec spec = getSpecOr404(entityPath);
        byte[] data = importService.generateTemplateExcel(spec.headers(), spec.entityTitle());
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + entityPath + "-template.xlsx\"")
            .contentType(MediaType.parseMediaType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
            .body(data);
    }

    @PostMapping("/{entityPath}/preview")
    public String preview(
        @PathVariable String entityPath,
        @RequestParam("file") MultipartFile file,
        HttpSession session,
        Model model,
        RedirectAttributes redirectAttributes
    ) {
        EntitySpec spec = getSpecOr404(entityPath);

        if (file == null || file.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Please select a file to upload.");
            return "redirect:/import/" + entityPath;
        }

        List<ImportRow> rows;
        try {
            rows = importService.parseFile(file);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                "Failed to parse file: " + e.getMessage());
            return "redirect:/import/" + entityPath;
        }

        if (rows.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage",
                "The uploaded file contains no data rows.");
            return "redirect:/import/" + entityPath;
        }

        // Store rows in session for the commit step
        for (ImportRow row : rows) {
            String error = dryRunValidate(spec, row.getData());
            row.setError(error);
        }

        // Store validated rows in session for the commit step
        session.setAttribute("importRows_" + entityPath, rows);
        session.setAttribute("importSpec_" + entityPath, entityPath);

        long valid = rows.stream().filter(r -> r.getError() == null).count();
        model.addAttribute("entityPath", entityPath);
        model.addAttribute("entityTitle", spec.entityTitle());
        model.addAttribute("listPath", spec.listPath());
        model.addAttribute("rows", rows);
        model.addAttribute("totalCount", rows.size());
        model.addAttribute("validCount", valid);
        model.addAttribute("errorCount", rows.size() - valid);
        model.addAttribute("columns", spec.headers());
        return "import/preview";
    }

    @PostMapping("/{entityPath}/commit")
    public String commit(
        @PathVariable String entityPath,
        HttpSession session,
        RedirectAttributes redirectAttributes
    ) {
        EntitySpec spec = getSpecOr404(entityPath);

        @SuppressWarnings("unchecked")
        List<ImportRow> rows = (List<ImportRow>) session.getAttribute("importRows_" + entityPath);

        if (rows == null || rows.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage",
                "No import session found. Please upload a file again.");
            return "redirect:/import/" + entityPath;
        }

        int committed = 0;
        int failed = 0;
        for (ImportRow row : rows) {
            String error = spec.creator().apply(row.getData());
            if (error == null) {
                committed++;
            } else {
                row.setError(error);
                failed++;
            }
        }

        // Clear session
        session.removeAttribute("importRows_" + entityPath);
        session.removeAttribute("importSpec_" + entityPath);

        if (failed == 0) {
            redirectAttributes.addFlashAttribute("importSuccess",
                committed + " record(s) imported successfully.");
        } else {
            redirectAttributes.addFlashAttribute("importWarning",
                committed + " record(s) imported; " + failed + " failed. Check errors below.");
            redirectAttributes.addFlashAttribute("importRows", rows);
            redirectAttributes.addFlashAttribute("entityPath", entityPath);
            redirectAttributes.addFlashAttribute("entityTitle", spec.entityTitle());
            redirectAttributes.addFlashAttribute("columns", spec.headers());
            return "redirect:/import/" + entityPath + "/result";
        }

        return "redirect:" + spec.listPath() + "?imported=" + committed;
    }

    @GetMapping("/{entityPath}/result")
    public String result(@PathVariable String entityPath, Model model) {
        EntitySpec spec = getSpecOr404(entityPath);
        model.addAttribute("entityPath", entityPath);
        model.addAttribute("entityTitle", spec.entityTitle());
        model.addAttribute("listPath", spec.listPath());
        if (!model.containsAttribute("columns")) {
            model.addAttribute("columns", spec.headers());
        }
        return "import/result";
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private EntitySpec getSpecOr404(String entityPath) {
        EntitySpec spec = registry.get(entityPath);
        if (spec == null) {
            throw new org.springframework.web.server.ResponseStatusException(
                org.springframework.http.HttpStatus.NOT_FOUND,
                "Unknown entity type: " + entityPath);
        }
        return spec;
    }

    private String dryRunValidate(EntitySpec spec, Map<String, String> rowData) {
        return transactionTemplate.execute(status -> {
            String error = spec.creator().apply(rowData);
            // Always rollback during preview; commit happens in the final step.
            status.setRollbackOnly();
            return error;
        });
    }

    private String get(Map<String, String> row, String key) {
        String value = row.get(key);
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private LocalDate parseDate(String value) {
        return value == null ? null : LocalDate.parse(value);
    }

    private BigDecimal parseDecimal(String value) {
        return value == null ? null : new BigDecimal(value);
    }
}

