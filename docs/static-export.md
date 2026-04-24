# Static HTML Export

The project includes a Thymeleaf-to-HTML exporter that renders the real MVC pages using the existing Spring Boot context and seeded demo data.

## What it exports
- Dashboard (`/`)
- List pages for all asset modules
- Detail pages for modules that currently expose `detail.html`
- Static assets copied into the generated bundle

## Run on Windows PowerShell
```powershell
./mvnw.cmd spring-boot:run "-Dspring-boot.run.main-class=com.sdr.ams.export.StaticSiteExportLauncher"
```

## Custom output folder
```powershell
./mvnw.cmd spring-boot:run "-Dspring-boot.run.main-class=com.sdr.ams.export.StaticSiteExportLauncher" "-Dspring-boot.run.arguments=--app.static-export.output-dir=target/demo-site"
```

## Output
By default the bundle is generated under `target/static-export/`.

Upload the generated files to any standard web server. The bundle is intentionally read-only and disables interactive create/edit/delete/import/export actions.

