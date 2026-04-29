package com.sdr.ams.export;

import com.sdr.ams.model.core.CoreEntity;
import com.sdr.ams.repository.BankAccountRepository;
import com.sdr.ams.repository.BondRepository;
import com.sdr.ams.repository.BrandRepository;
import com.sdr.ams.repository.CashRepository;
import com.sdr.ams.repository.CopyrightRepository;
import com.sdr.ams.repository.InvoiceRepository;
import com.sdr.ams.repository.InventoryRepository;
import com.sdr.ams.repository.MachineryRepository;
import com.sdr.ams.repository.PatentRepository;
import com.sdr.ams.repository.RealEstateRepository;
import com.sdr.ams.repository.ReputationRepository;
import com.sdr.ams.repository.StockRepository;
import com.sdr.ams.repository.TrademarkRepository;
import com.sdr.ams.repository.VehicleRepository;
import com.sdr.ams.service.ExportService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Service
public class StaticSiteExportService {

    private static final Logger log = LoggerFactory.getLogger(StaticSiteExportService.class);

    public static final String DEFAULT_OUTPUT_DIR = "target/static-export";

    private static final Pattern NUMERIC_DETAIL_PATH = Pattern.compile("^/([^/]+)/([0-9]+)$");
    private static final List<String> LIST_REQUEST_PATHS = List.of(
        "/bank-accounts",
        "/bonds",
        "/invoices",
        "/stocks",
        "/cash",
        "/inventories",
        "/machineries",
        "/real-estates",
        "/vehicles",
        "/brands",
        "/copyrights",
        "/patents",
        "/reputations",
        "/trademarks",
        "/import"
    );

    private static final String EXPORT_STYLE = """
        .static-export-banner {
            background: #fff7ed;
            border: 1px solid #fdba74;
            border-radius: 10px;
            color: #9a3412;
            font-size: 14px;
            margin: 0 0 18px;
            padding: 12px 14px;
        }
        .static-demo-note {
            background: #eff6ff;
            border-left: 4px solid #3b82f6;
            color: #1e3a8a;
            margin-bottom: 12px;
            padding: 8px 12px;
        }
        .static-disabled-link,
        .static-disabled-action {
            color: #64748b;
            cursor: not-allowed;
            display: inline-block;
            font-weight: 600;
            opacity: 0.9;
            text-decoration: none;
        }
        .static-demo-form {
            opacity: 0.92;
        }
        .static-demo-form input,
        .static-demo-form select,
        .static-demo-form textarea,
        .static-demo-form button {
            background: #f8fafc;
        }
        """;

    private final WebApplicationContext webApplicationContext;
    private final ExportService exportService;
    private final BankAccountRepository bankAccountRepository;
    private final BondRepository bondRepository;
    private final InvoiceRepository invoiceRepository;
    private final StockRepository stockRepository;
    private final RealEstateRepository realEstateRepository;
    private final VehicleRepository vehicleRepository;
    private final PatentRepository patentRepository;
    private final ReputationRepository reputationRepository;
    private final TrademarkRepository trademarkRepository;
    private final CashRepository cashRepository;
    private final BrandRepository brandRepository;
    private final CopyrightRepository copyrightRepository;
    private final InventoryRepository inventoryRepository;
    private final MachineryRepository machineryRepository;

    public StaticSiteExportService(
        WebApplicationContext webApplicationContext,
        ExportService exportService,
        BankAccountRepository bankAccountRepository,
        BondRepository bondRepository,
        InvoiceRepository invoiceRepository,
        StockRepository stockRepository,
        RealEstateRepository realEstateRepository,
        VehicleRepository vehicleRepository,
        PatentRepository patentRepository,
        ReputationRepository reputationRepository,
        TrademarkRepository trademarkRepository,
        CashRepository cashRepository,
        BrandRepository brandRepository,
        CopyrightRepository copyrightRepository,
        InventoryRepository inventoryRepository,
        MachineryRepository machineryRepository
    ) {
        this.webApplicationContext = webApplicationContext;
        this.exportService = exportService;
        this.bankAccountRepository = bankAccountRepository;
        this.bondRepository = bondRepository;
        this.invoiceRepository = invoiceRepository;
        this.stockRepository = stockRepository;
        this.realEstateRepository = realEstateRepository;
        this.vehicleRepository = vehicleRepository;
        this.patentRepository = patentRepository;
        this.reputationRepository = reputationRepository;
        this.trademarkRepository = trademarkRepository;
        this.cashRepository = cashRepository;
        this.brandRepository = brandRepository;
        this.copyrightRepository = copyrightRepository;
        this.inventoryRepository = inventoryRepository;
        this.machineryRepository = machineryRepository;
    }

    public ExportSummary exportTo(Path outputDirectory) throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        OffsetDateTime generatedAt = OffsetDateTime.now();

        resetOutputDirectory(outputDirectory);
        copyStaticResources(outputDirectory);
        writeNoJekyll(outputDirectory);

        List<ExportPage> pages = buildPages();
        Map<String, String> routeMap = new LinkedHashMap<>();
        for (ExportPage page : pages) {
            routeMap.put(page.requestPath(), page.outputPath());
        }

        int exportedPageCount = 0;
        for (ExportPage page : pages) {
            try {
                String rendered = mockMvc.perform(get(page.requestPath()))
                    .andExpect(status().isOk())
                    .andReturn()
                    .getResponse()
                    .getContentAsString(StandardCharsets.UTF_8);

                String processed = postProcess(rendered, page, routeMap, generatedAt);
                Path target = outputDirectory.resolve(page.outputPath());
                Files.createDirectories(target.getParent());
                Files.writeString(target, processed, StandardCharsets.UTF_8);
                exportedPageCount++;
            } catch (Exception ex) {
                // Keep export resilient when a single template is temporarily broken.
                log.warn("Skipping static export page {} due to render failure", page.requestPath(), ex);
            }
        }

        DataExportSummary dataExportSummary = generateDataExports(outputDirectory);
        writeBundleReadme(outputDirectory, exportedPageCount, dataExportSummary, generatedAt);
        return new ExportSummary(
            outputDirectory,
            exportedPageCount,
            dataExportSummary.csvFileCount(),
            dataExportSummary.xlsxFileCount(),
            generatedAt
        );
    }

    private List<ExportPage> buildPages() {
        List<ExportPage> pages = new ArrayList<>();
        pages.add(new ExportPage("/", "index.html"));
        LIST_REQUEST_PATHS.forEach(path -> pages.add(new ExportPage(path, toOutputPath(path))));
        addDetailPages(pages, "/bank-accounts", bankAccountRepository);
        addDetailPages(pages, "/bonds", bondRepository);
        addDetailPages(pages, "/invoices", invoiceRepository);
        addDetailPages(pages, "/stocks", stockRepository);
        // Real estate detail template currently references non-existent fields; export its list view only.
        addDetailPages(pages, "/vehicles", vehicleRepository);
        addDetailPages(pages, "/patents", patentRepository);
        addDetailPages(pages, "/reputations", reputationRepository);
        addDetailPages(pages, "/trademarks", trademarkRepository);
        return pages;
    }

    private DataExportSummary generateDataExports(Path outputDirectory) throws IOException {
        int csvCount = 0;
        int xlsxCount = 0;
        Path exportDir = outputDirectory.resolve("data");
        Files.createDirectories(exportDir);

        for (EntityExportSource source : buildExportSources()) {
            try {
                byte[] csvData = exportService.toCsv(source.items(), source.entityName());
                Path csvFile = exportDir.resolve(source.entityKey() + ".csv");
                Files.write(csvFile, csvData);
                csvCount++;
                log.debug("Generated CSV export: {}", csvFile.getFileName());
            } catch (Exception ex) {
                log.warn("Failed to generate CSV export for {}", source.entityKey(), ex);
            }

            try {
                byte[] xlsxData = exportService.toExcel(source.items(), source.entityName());
                Path xlsxFile = exportDir.resolve(source.entityKey() + ".xlsx");
                Files.write(xlsxFile, xlsxData);
                xlsxCount++;
                log.debug("Generated Excel export: {}", xlsxFile.getFileName());
            } catch (Exception ex) {
                log.warn("Failed to generate Excel export for {}", source.entityKey(), ex);
            }
        }

        return new DataExportSummary(csvCount, xlsxCount);
    }

    private List<EntityExportSource> buildExportSources() {
        return List.of(
            new EntityExportSource("bank-accounts", "BankAccount", bankAccountRepository.findAll()),
            new EntityExportSource("bonds", "Bond", bondRepository.findAll()),
            new EntityExportSource("invoices", "Invoice", invoiceRepository.findAll()),
            new EntityExportSource("stocks", "Stock", stockRepository.findAll()),
            new EntityExportSource("cash", "Cash", cashRepository.findAll()),
            new EntityExportSource("inventories", "Inventory", inventoryRepository.findAll()),
            new EntityExportSource("machineries", "Machinery", machineryRepository.findAll()),
            new EntityExportSource("real-estates", "RealEstate", realEstateRepository.findAll()),
            new EntityExportSource("vehicles", "Vehicle", vehicleRepository.findAll()),
            new EntityExportSource("brands", "Brand", brandRepository.findAll()),
            new EntityExportSource("copyrights", "Copyright", copyrightRepository.findAll()),
            new EntityExportSource("patents", "Patent", patentRepository.findAll()),
            new EntityExportSource("reputations", "Reputation", reputationRepository.findAll()),
            new EntityExportSource("trademarks", "Trademark", trademarkRepository.findAll())
        );
    }

    private <T extends CoreEntity> void addDetailPages(
        List<ExportPage> pages,
        String basePath,
        JpaRepository<T, Long> repository
    ) {
        repository.findAll().stream()
            .map(CoreEntity::getId)
            .sorted()
            .forEach(id -> {
                String requestPath = basePath + "/" + id;
                pages.add(new ExportPage(requestPath, toOutputPath(requestPath)));
            });
    }

    private String postProcess(String html, ExportPage currentPage, Map<String, String> routeMap, OffsetDateTime generatedAt) {
        Document document = Jsoup.parse(html);
        document.outputSettings().syntax(Document.OutputSettings.Syntax.html);

        document.head().appendElement("meta")
            .attr("name", "generator")
            .attr("content", "Asset Management Static Site Exporter");
        document.head().appendElement("style").appendText(EXPORT_STYLE);
        document.head().appendElement("script")
            .append("window.Chart = window.Chart || function ChartFallback() { return null; };");

        if (document.body() != null) {
            document.body().prependElement("div")
                .addClass("static-export-banner")
                .text("Static demo snapshot generated on "
                    + generatedAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm XXX"))
                    + ". Interactive create/edit/delete/import/export actions are disabled in this HTML bundle.");
        }

        rewriteAssetReferences(document, currentPage);
        rewriteAnchors(document, currentPage, routeMap);
        rewriteForms(document);
        return document.outerHtml();
    }

    private void rewriteAssetReferences(Document document, ExportPage currentPage) {
        for (Element element : document.select("link[href], script[src], img[src]")) {
            String attribute = element.hasAttr("href") ? "href" : "src";
            String value = element.attr(attribute);
            if (value == null || value.isBlank() || !value.startsWith("/")) {
                continue;
            }
            String assetTarget = mapStaticAssetPath(value);
            if (assetTarget != null) {
                element.attr(attribute, relativize(currentPage.outputPath(), assetTarget));
            }
        }
    }

    private void rewriteAnchors(Document document, ExportPage currentPage, Map<String, String> routeMap) {
        for (Element anchor : document.select("a[href]")) {
            String href = anchor.attr("href");
            if (href == null || href.isBlank()) {
                continue;
            }
            if (!href.startsWith("/")) {
                continue;
            }

            // Keep export links functional in static bundle by remapping to generated files.
            String exportTarget = mapExportToDataFile(href);
            if (exportTarget != null) {
                anchor.attr("href", relativize(currentPage.outputPath(), exportTarget));
                continue;
            }

            String target = mapApplicationHref(href, routeMap);
            if (target == null) {
                Element replacement = new Element(Tag.valueOf("span"), "");
                replacement.addClass("static-disabled-link");
                replacement.attr("title", "Available in the live Spring Boot application");
                replacement.html(anchor.html());
                anchor.replaceWith(replacement);
                continue;
            }

            anchor.attr("href", relativize(currentPage.outputPath(), target));
        }
    }

    private String mapExportToDataFile(String href) {
        URI uri = URI.create(href);
        String path = uri.getPath();
        if (path == null || !path.matches("^/[^/]+/export$")) {
            return null;
        }

        String format = resolveExportFormat(uri.getRawQuery());
        if (format == null) {
            return null;
        }

        String entityType = path.substring(1, path.lastIndexOf('/'));
        return "data/" + entityType + "." + format;
    }

    private String resolveExportFormat(String rawQuery) {
        if (rawQuery == null || rawQuery.isBlank()) {
            return "csv";
        }

        String formatValue = null;
        for (String pair : rawQuery.split("&")) {
            int eqIndex = pair.indexOf('=');
            if (eqIndex <= 0) {
                continue;
            }
            String key = URLDecoder.decode(pair.substring(0, eqIndex), StandardCharsets.UTF_8);
            if (!"format".equalsIgnoreCase(key)) {
                continue;
            }
            formatValue = URLDecoder.decode(pair.substring(eqIndex + 1), StandardCharsets.UTF_8);
            break;
        }

        if (formatValue == null || formatValue.isBlank() || "csv".equalsIgnoreCase(formatValue)) {
            return "csv";
        }
        if ("excel".equalsIgnoreCase(formatValue) || "xlsx".equalsIgnoreCase(formatValue)) {
            return "xlsx";
        }

        return null;
    }

    private void rewriteForms(Document document) {
        for (Element form : document.select("form[action]")) {
            String method = form.attr("method");
            if (method == null || method.isBlank()) {
                method = "get";
            }

            if (!"get".equalsIgnoreCase(method)) {
                Element replacement = new Element(Tag.valueOf("span"), "");
                replacement.addClass("static-disabled-action");
                replacement.text("Action not available in static demo");
                form.replaceWith(replacement);
                continue;
            }

            form.addClass("static-demo-form");
            form.attr("action", "#");
            form.attr("onsubmit", "return false;");
            if (form.selectFirst(".static-demo-note") == null) {
                form.prependElement("p")
                    .addClass("static-demo-note")
                    .text("Filters and uploads are shown for reference only. Use the live application for interactive behavior.");
            }
            for (Element control : form.select("input, select, textarea, button")) {
                if (!"hidden".equalsIgnoreCase(control.attr("type"))) {
                    control.attr("disabled", "disabled");
                }
            }
        }
    }

    private String mapApplicationHref(String href, Map<String, String> routeMap) {
        URI uri = URI.create(href);
        String path = uri.getPath();
        if (path == null || path.isBlank()) {
            return null;
        }
        if (routeMap.containsKey(path)) {
            return routeMap.get(path);
        }

        String staticAsset = mapStaticAssetPath(path);
        if (staticAsset != null) {
            return staticAsset;
        }

        if (path.startsWith("/import/")) {
            String entityPath = path.substring("/import/".length());
            if (routeMap.containsKey("/" + entityPath)) {
                return routeMap.get("/" + entityPath);
            }
            return routeMap.get("/import");
        }

        if (isInteractiveOnlyPath(path)) {
            return null;
        }

        Matcher detailMatcher = NUMERIC_DETAIL_PATH.matcher(path);
        if (detailMatcher.matches()) {
            String listPath = "/" + detailMatcher.group(1);
            return routeMap.get(listPath);
        }

        return null;
    }

    private boolean isInteractiveOnlyPath(String path) {
        return path.endsWith("/new")
            || path.endsWith("/edit")
            || path.endsWith("/pdf")
            || path.endsWith("/delete")
            || (path.contains("/export") && !path.matches("^/[^/]+/export$"))  // Allow /entity/export but not other export paths
            || path.endsWith("/template")
            || path.endsWith("/template-xlsx")
            || path.endsWith("/preview")
            || path.endsWith("/commit")
            || path.endsWith("/result")
            || path.startsWith("/h2-console");
    }

    private String mapStaticAssetPath(String path) {
        if (path.startsWith("/css/") || path.startsWith("/js/") || path.startsWith("/images/") || path.startsWith("/img/")) {
            return "assets" + path;
        }
        return null;
    }

    private String relativize(String fromOutputPath, String toOutputPath) {
        Path from = Path.of(fromOutputPath).getParent();
        Path to = Path.of(toOutputPath);
        Path base = from == null ? Path.of("") : from;
        return base.relativize(to).toString().replace('\\', '/');
    }

    private String toOutputPath(String requestPath) {
        if ("/".equals(requestPath)) {
            return "index.html";
        }

        Matcher matcher = NUMERIC_DETAIL_PATH.matcher(requestPath);
        if (matcher.matches()) {
            return matcher.group(1) + "/" + matcher.group(2) + ".html";
        }

        return requestPath.substring(1) + "/index.html";
    }

    private void resetOutputDirectory(Path outputDirectory) throws IOException {
        if (Files.exists(outputDirectory)) {
            try (Stream<Path> walk = Files.walk(outputDirectory)) {
                walk.sorted(Comparator.reverseOrder())
                    .forEach(path -> {
                        try {
                            Files.deleteIfExists(path);
                        } catch (IOException ex) {
                            throw new IllegalStateException("Failed to delete existing export path: " + path, ex);
                        }
                    });
            }
        }
        Files.createDirectories(outputDirectory);
    }

    private void copyStaticResources(Path outputDirectory) throws IOException {
        Path sourceStaticDir = Path.of("src", "main", "resources", "static");
        Path targetStaticDir = outputDirectory.resolve("assets");

        if (Files.exists(sourceStaticDir)) {
            try (Stream<Path> walk = Files.walk(sourceStaticDir)) {
                walk.forEach(source -> copyResourcePath(sourceStaticDir, source, targetStaticDir));
            }
            return;
        }

        Path cssTarget = targetStaticDir.resolve(Path.of("css", "templates.css"));
        Files.createDirectories(cssTarget.getParent());
        try (var inputStream = getClass().getClassLoader().getResourceAsStream("static/css/templates.css")) {
            if (inputStream == null) {
                throw new IllegalStateException("Could not locate static/css/templates.css on the classpath");
            }
            Files.copy(inputStream, cssTarget);
        }
    }

    private void copyResourcePath(Path sourceRoot, Path source, Path targetRoot) {
        try {
            Path relative = sourceRoot.relativize(source);
            Path target = targetRoot.resolve(relative.toString());
            if (Files.isDirectory(source)) {
                Files.createDirectories(target);
                return;
            }
            Files.createDirectories(target.getParent());
            Files.copy(source, target);
        } catch (IOException ex) {
            throw new IllegalStateException("Failed to copy static resource: " + source, ex);
        }
    }

    private void writeNoJekyll(Path outputDirectory) throws IOException {
        Files.writeString(outputDirectory.resolve(".nojekyll"), "", StandardCharsets.UTF_8);
    }

    private void writeBundleReadme(Path outputDirectory, int exportedPageCount, DataExportSummary dataExportSummary, OffsetDateTime generatedAt) throws IOException {
        String content = """
            # Static Demo Bundle
            
            This directory contains a static HTML export of the Asset Management Service generated from the live Spring Boot Thymeleaf views and seeded demo data.
            
            ## Included
            - Dashboard: `index.html`
            - Entity list pages for all asset modules
            - Detail pages for modules that currently have `detail.html` templates
            - Static assets copied under `assets/`
            - CSV and Excel exports under `data/` for all entity types
            
            ## Generated Snapshot
            - Generated at: %s
            - Exported pages: %d
            - CSV files exported: %d
            - Excel files exported: %d
            
            ## Data Export
            All entity data is available in the `data/` directory as both `.csv` and `.xlsx` files.
            For each module (for example `bank-accounts`), both of these are generated:
            - `data/bank-accounts.csv`
            - `data/bank-accounts.xlsx`
            
            Use CSV for lightweight data exchange and Excel for native spreadsheet workflows.
            
            ## Deploy
            Upload the contents of this directory to any standard web server, including Apache, Nginx, IIS, GitHub Pages, or PHP-oriented shared hosting.
            
            ## Notes
            - This bundle is read-only by design.
            - Create/edit/delete/import actions are intentionally disabled.
            - CSV/Excel export links are functional and point to the generated data files.
            - Demo charts use the CDN version of Chart.js when available; the pages still load if the CDN is unavailable.
            """.formatted(
            generatedAt.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME),
            exportedPageCount,
            dataExportSummary.csvFileCount(),
            dataExportSummary.xlsxFileCount()
        );
        Files.writeString(outputDirectory.resolve("README.md"), content, StandardCharsets.UTF_8);
    }

    public record ExportSummary(Path outputDirectory, int pageCount, int csvFileCount, int xlsxFileCount, OffsetDateTime generatedAt) {
    }

    private record DataExportSummary(int csvFileCount, int xlsxFileCount) {
    }

    private record EntityExportSource(String entityKey, String entityName, List<?> items) {
    }

    private record ExportPage(String requestPath, String outputPath) {
    }
}

