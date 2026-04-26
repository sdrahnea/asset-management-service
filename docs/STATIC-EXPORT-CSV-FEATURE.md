# Static Export with CSV Feature

## Overview
CSV export has been integrated into the static HTML export feature. When you generate a static HTML bundle, it now includes CSV files for all entity types that can be downloaded directly from the generated static site.

## What Changed

### 1. **Enhanced StaticSiteExportService**
Located at: `src/main/java/com/sdr/ams/export/StaticSiteExportService.java`

#### New Dependencies Injected:
- `ExportService` - handles CSV generation
- Additional repositories:
  - `CashRepository`
  - `BrandRepository`
  - `CopyrightRepository`
  - `InventoryRepository`
  - `MachineryRepository`

#### New Methods:
- `generateCsvExports(Path outputDirectory)` - Generates CSV files for all 14 entity types during static export
- `mapExportToCsvFile(String href, ExportPage currentPage)` - Maps export links to generated CSV files

#### Enhanced Methods:
- `rewriteAnchors()` - Now handles export links specially, converting them to point to static CSV files
- `isInteractiveOnlyPath()` - Updated to allow `/entity/export` paths (they're now mapped to CSV files)

### 2. **Updated ExportSummary Record**
The `ExportSummary` record now includes:
```java
public record ExportSummary(
    Path outputDirectory,
    int pageCount,
    int csvFileCount,  // NEW
    OffsetDateTime generatedAt
)
```

### 3. **StaticSiteExportLauncher Updates**
Output now shows CSV file count:
```
Static demo export completed.
Output directory: target/static-export
Pages exported: 150
CSV files exported: 14
Generated at: 2026-04-18T10:30:00+03:00
```

### 4. **Enhanced Bundle README**
The generated `README.md` now includes:
- CSV export information
- List of all available CSV files
- Instructions for importing CSV files into Excel/Google Sheets

## Generated CSV Files

When you run the static export, the following CSV files are created under `data/` directory:

```
data/
├── bank-accounts.csv
├── bonds.csv
├── invoices.csv
├── stocks.csv
├── cash.csv
├── inventories.csv
├── machineries.csv
├── real-estates.csv
├── vehicles.csv
├── brands.csv
├── copyrights.csv
├── patents.csv
├── reputations.csv
└── trademarks.csv
```

## How It Works

### Export Link Handling
When a user clicks the "Export CSV" button on a list page:

1. **In Live Application**: The link goes to `/entity-type/export?format=csv` → downloads CSV file directly
2. **In Static Export**: 
   - The link is rewritten during post-processing
   - Instead of pointing to `/entity-type/export`, it points to the relative path `../../data/entity-type.csv`
   - Users can download the pre-generated CSV file

### Link Rewriting Logic
```
/bonds/export?format=csv → ../../../data/bonds.csv (relative to bond detail page)
/invoices/export → ../../data/invoices.csv (relative to invoice list page)
```

## Running Static Export with CSV

```powershell
# Default output to target/static-export
./mvnw.cmd spring-boot:run "-Dspring-boot.run.main-class=com.sdr.ams.export.StaticSiteExportLauncher"

# Custom output directory
./mvnw.cmd spring-boot:run `
  "-Dspring-boot.run.main-class=com.sdr.ams.export.StaticSiteExportLauncher" `
  "-Dspring-boot.run.arguments=--app.static-export.output-dir=target/demo-site"
```

## Directory Structure of Generated Bundle

```
static-export/
├── index.html                          # Dashboard
├── bundle/index.html                   # bundle list (if applicable)
├── README.md                           # Bundle documentation with CSV list
├── .nojekyll                           # GitHub Pages marker
├── assets/                             # Static assets
│   ├── css/
│   ├── js/
│   └── images/
└── data/                              # CSV EXPORTS (NEW!)
    ├── bank-accounts.csv
    ├── bonds.csv
    ├── invoices.csv
    ├── stocks.csv
    ├── cash.csv
    ├── inventories.csv
    ├── machineries.csv
    ├── real-estates.csv
    ├── vehicles.csv
    ├── brands.csv
    ├── copyrights.csv
    ├── patents.csv
    ├── reputations.csv
    └── trademarks.csv
```

## Usage Scenarios

### 1. **Demo Deployment**
Deploy the static bundle to a web server, and users can:
- Browse all entity lists and details as static HTML
- Download CSV files for further analysis in Excel/Google Sheets
- Share the bundle with stakeholders without needing a live application

### 2. **Data Analysis**
Download CSV files and use them in:
- Excel for pivot tables and charts
- Google Sheets for collaborative analysis
- Python/R for data science workflows
- SQL databases for integration

### 3. **Offline Availability**
The entire bundle (HTML + CSV) can be packaged and shared offline without any backend dependency.

## Testing

The `StaticSiteExportServiceTest` has been updated to verify:
- CSV files are generated (`csvFileCount > 0`)
- CSV files exist in the output directory (`data/bank-accounts.csv`)
- CSV content is valid and not empty

Run the test:
```powershell
./mvnw.cmd test -Dtest=StaticSiteExportServiceTest
```

## Benefits

✅ **No Backend Required**: Users can download and analyze data from static HTML pages  
✅ **Flexible Export**: All entity types automatically included  
✅ **Compatible**: CSV files work with Excel, Google Sheets, Power BI, Tableau, etc.  
✅ **Scalable**: Works with any demo data size (auto-generated based on seeded data)  
✅ **Professional**: Properly formatted CSV with escaped special characters  
✅ **Convenient**: Pre-generated during export, no server-side processing needed  

## Technical Details

### CSV Generation Process
1. During static export, `generateCsvExports()` is called after HTML pages are generated
2. For each entity type:
   - Fetches all records from the repository
   - Uses `ExportService.toCsv()` to convert to CSV format
   - Writes to `output-dir/data/entity-type.csv`
3. Returns count of successfully generated CSV files

### Link Transformation
When parsing HTML during post-processing:
1. Detects anchor tags with href matching `/entity/export` pattern
2. Checks if format parameter is `csv` or absent (defaults to csv)
3. Replaces href with relative path to generated CSV file
4. Preserves button styling and accessibility

### Error Handling
- If CSV generation fails for a single entity, the export continues (logged as warning)
- Errors don't prevent overall export completion
- CSV count reflects only successfully generated files

## Future Enhancements

Potential improvements:
- Excel (XLSX) export generation during static export
- JSON export format
- Filtered CSV exports (by category, status, etc.)
- Compressed bundle format (ZIP)
- CSV schema/metadata documentation

## Files Modified

1. `src/main/java/com/sdr/ams/export/StaticSiteExportService.java` - Core enhancement
2. `src/main/java/com/sdr/ams/export/StaticSiteExportLauncher.java` - Output display updated
3. `src/test/java/com/sdr/ams/export/StaticSiteExportServiceTest.java` - Test coverage added

## References

- AGENTS.md - Updated with CSV export capability
- docs/static-export.md - Original static export documentation
- docs/STATIC-EXPORT-CSV-FEATURE.md - This file

