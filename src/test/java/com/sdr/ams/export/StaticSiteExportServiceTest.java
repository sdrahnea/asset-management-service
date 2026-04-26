package com.sdr.ams.export;

import com.sdr.ams.repository.BankAccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class StaticSiteExportServiceTest {

    @Autowired
    private StaticSiteExportService staticSiteExportService;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @TempDir
    Path tempDir;

    @Test
    void exportsStaticBundleFromSeededApplicationPages() throws Exception {
        assertThat(bankAccountRepository.count()).isGreaterThan(0);

        StaticSiteExportService.ExportSummary summary = staticSiteExportService.exportTo(tempDir);

        assertThat(summary.pageCount()).isGreaterThan(10);
        assertThat(summary.csvFileCount()).isGreaterThan(0);
        assertThat(Files.exists(tempDir.resolve("index.html"))).isTrue();
        assertThat(Files.exists(tempDir.resolve("bank-accounts/index.html"))).isTrue();
        assertThat(Files.exists(tempDir.resolve("assets/css/templates.css"))).isTrue();
        assertThat(Files.exists(tempDir.resolve("README.md"))).isTrue();
        assertThat(Files.exists(tempDir.resolve("data/bank-accounts.csv"))).isTrue();

        Long firstBankAccountId = bankAccountRepository.findAll().stream()
            .map(account -> account.getId())
            .sorted()
            .findFirst()
            .orElseThrow();

        assertThat(Files.exists(tempDir.resolve("bank-accounts/" + firstBankAccountId + ".html"))).isTrue();

        String indexHtml = Files.readString(tempDir.resolve("index.html"));
        String listHtml = Files.readString(tempDir.resolve("bank-accounts/index.html"));
        String csvContent = Files.readString(tempDir.resolve("data/bank-accounts.csv"));

        assertThat(indexHtml).contains("Static demo snapshot generated");
        assertThat(indexHtml).contains("bank-accounts/index.html");
        assertThat(listHtml).contains("../assets/css/templates.css");
        assertThat(listHtml).contains("Static demo snapshot generated");
        assertThat(listHtml).doesNotContain("href=\"/bank-accounts/new\"");
        assertThat(csvContent).isNotBlank();
    }
}

