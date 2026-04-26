package com.sdr.ams.export;

import com.sdr.ams.AssetManagementServiceApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.nio.file.Path;

public final class StaticSiteExportLauncher {

    private StaticSiteExportLauncher() {
    }

    public static void main(String[] args) throws Exception {
        try (ConfigurableApplicationContext context = new SpringApplicationBuilder(AssetManagementServiceApplication.class)
            .web(WebApplicationType.SERVLET)
            .properties("server.port=-1")
            .run(args)) {

            Environment environment = context.getEnvironment();
            Path outputDirectory = Path.of(environment.getProperty(
                "app.static-export.output-dir",
                StaticSiteExportService.DEFAULT_OUTPUT_DIR
            ));

            StaticSiteExportService exporter = context.getBean(StaticSiteExportService.class);
            StaticSiteExportService.ExportSummary summary = exporter.exportTo(outputDirectory);

            System.out.println("Static demo export completed.");
            System.out.println("Output directory: " + summary.outputDirectory().toAbsolutePath());
            System.out.println("Pages exported: " + summary.pageCount());
            System.out.println("CSV files exported: " + summary.csvFileCount());
            System.out.println("Generated at: " + summary.generatedAt());
        }
    }
}

