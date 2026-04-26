# Static HTML Export

The project includes a Thymeleaf-to-HTML exporter that renders the real MVC pages using the existing Spring Boot context and seeded demo data. **As of v4.0.5+, CSV export is integrated into the static bundle.**

## What it exports
- Dashboard (`/`)
- List pages for all asset modules
- Detail pages for modules that currently expose `detail.html`
- Static assets copied into the generated bundle
- **CSV data exports for all 14 entity types** (saved under `data/` directory)

## Run on Windows PowerShell
```powershell
./mvnw.cmd spring-boot:run "-Dspring-boot.run.main-class=com.sdr.ams.export.StaticSiteExportLauncher"
```

## Custom output folder
```powershell
./mvnw.cmd spring-boot:run "-Dspring-boot.run.main-class=com.sdr.ams.export.StaticSiteExportLauncher" "-Dspring-boot.run.arguments=--app.static-export.output-dir=target/demo-site"
```

## Output Structure
By default the bundle is generated under `target/static-export/`:

```
target/static-export/
├── index.html                    # Dashboard
├── bank-accounts/
│   ├── index.html               # List page
│   ├── 1.html, 2.html, etc.     # Detail pages
├── bonds/, invoices/, stocks/    # Similar structure for each entity type
├── data/                         # NEW: CSV EXPORTS
│   ├── bank-accounts.csv
│   ├── bonds.csv
│   ├── invoices.csv
│   ├── stocks.csv
│   ├── cash.csv
│   ├── inventories.csv
│   ├── machineries.csv
│   ├── real-estates.csv
│   ├── vehicles.csv
│   ├── brands.csv
│   ├── copyrights.csv
│   ├── patents.csv
│   ├── reputations.csv
│   └── trademarks.csv
├── assets/                       # CSS, JS, images
└── README.md                     # Bundle documentation with CSV list
```

## CSV Export Features
- All entity data is automatically exported to CSV during bundle generation
- CSV files are pre-generated and link to static files (no server required)
- Compatible with Excel, Google Sheets, Python, R, and other data tools
- Users clicking "Export CSV" on list/detail pages download the static CSV file
- CSV headers are humanized from field names (e.g., `bankName` → `Bank Name`)

## Deploy
Upload the generated files to any standard web server. The bundle is intentionally read-only and disables interactive create/edit/delete/import/export actions.

Supported platforms:
- Apache, Nginx, IIS
- GitHub Pages
- S3, Azure Static Web Apps
- PHP-oriented shared hosting
- Any HTTP server

## Usage Examples

### 1. Open Dashboard
Open `index.html` in a browser to view the dashboard with all entities and statistics.

### 2. Download CSV Data
- Browse list pages (e.g., `/bonds/index.html`)
- Click "Export CSV" button
- Automatically downloads the pre-generated `data/bonds.csv` file

### 3. Import into Excel
1. Open Excel or Google Sheets
2. File → Open and select a CSV file from the `data/` directory
3. Analyze data with formulas, pivot tables, charts

### 4. Programmatic Access
Use Python, R, or other languages to access and process the CSV files:
```python
import pandas as pd
bonds = pd.read_csv('data/bonds.csv')
print(bonds.describe())
```

## Performance & Sizing
- Export time depends on demo data size (typically < 2 seconds for default 25 records per entity)
- Bundle size is minimal (~5-15 MB with assets)
- CSV files are lightweight and compress well for distribution

## Notes
- This bundle is **read-only** by design
- Create/edit/delete/import/export actions are handled via static CSV files (no server needed)
- Demo charts use the CDN version of Chart.js when available
- Export links in the HTML automatically point to local CSV files


