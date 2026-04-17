package com.sdr.ams.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import com.sdr.ams.model.financial.Invoice;
import com.sdr.ams.model.financial.InvoiceItem;
import com.sdr.ams.model.financial.InvoiceParty;
import com.sdr.ams.service.DetailPdfReportService;
import com.sdr.ams.service.ExportService;
import com.sdr.ams.service.InvoiceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
@RequestMapping("/invoices")
public class InvoiceController {

    private static final int ITEM_ROWS = 5;

    private final InvoiceService invoiceService;
    private final ExportService exportService;
    private final DetailPdfReportService detailPdfReportService;

    public InvoiceController(
        InvoiceService invoiceService,
        ExportService exportService,
        DetailPdfReportService detailPdfReportService
    ) {
        this.invoiceService = invoiceService;
        this.exportService = exportService;
        this.detailPdfReportService = detailPdfReportService;
    }

    @GetMapping
    public String list(
        @RequestParam(required = false) String invoiceNumber,
        @RequestParam(required = false) Invoice.InvoiceStatus status,
        @RequestParam(required = false) String sellerName,
        @RequestParam(required = false) String buyerName,
        @RequestParam(required = false) LocalDate issueDateFrom,
        @RequestParam(required = false) LocalDate issueDateTo,
        @RequestParam(required = false) LocalDate dueDateFrom,
        @RequestParam(required = false) LocalDate dueDateTo,
        Model model
    ) {
        model.addAttribute("invoices", invoiceService.findAll(
            invoiceNumber,
            status,
            sellerName,
            buyerName,
            issueDateFrom,
            issueDateTo,
            dueDateFrom,
            dueDateTo
        ));

        model.addAttribute("selectedInvoiceNumber", invoiceNumber);
        model.addAttribute("selectedStatus", status);
        model.addAttribute("selectedSellerName", sellerName);
        model.addAttribute("selectedBuyerName", buyerName);
        model.addAttribute("selectedIssueDateFrom", issueDateFrom);
        model.addAttribute("selectedIssueDateTo", issueDateTo);
        model.addAttribute("selectedDueDateFrom", dueDateFrom);
        model.addAttribute("selectedDueDateTo", dueDateTo);
        populateEnums(model);
        return "invoices/list";
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> export(
        @RequestParam(defaultValue = "csv") String format,
        @RequestParam(required = false) String invoiceNumber,
        @RequestParam(required = false) Invoice.InvoiceStatus status,
        @RequestParam(required = false) String sellerName,
        @RequestParam(required = false) String buyerName,
        @RequestParam(required = false) LocalDate issueDateFrom,
        @RequestParam(required = false) LocalDate issueDateTo,
        @RequestParam(required = false) LocalDate dueDateFrom,
        @RequestParam(required = false) LocalDate dueDateTo
    ) throws Exception {
        var items = invoiceService.findAll(
            invoiceNumber,
            status,
            sellerName,
            buyerName,
            issueDateFrom,
            issueDateTo,
            dueDateFrom,
            dueDateTo
        );
        if ("excel".equalsIgnoreCase(format) || "xlsx".equalsIgnoreCase(format)) {
            byte[] data = exportService.toExcel(items, "Invoice");
            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"invoices.xlsx\"")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(data);
        }

        byte[] data = exportService.toCsv(items, "Invoice");
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"invoices.csv\"")
            .contentType(MediaType.parseMediaType("text/csv; charset=UTF-8"))
            .body(data);
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        Invoice invoice = new Invoice();
        invoice.setInvoiceId(UUID.randomUUID().toString());
        invoice.setIssueDate(LocalDate.now());
        invoice.setDueDate(LocalDate.now().plusDays(30));
        invoice.setCurrency("EUR");
        invoice.setStatus(Invoice.InvoiceStatus.ISSUED);
        invoice.setAmountPaid(java.math.BigDecimal.ZERO);
        invoice.setSubtotal(java.math.BigDecimal.ZERO);
        invoice.setTotalTax(java.math.BigDecimal.ZERO);
        invoice.setTotalAmount(java.math.BigDecimal.ZERO);
        invoice.setBalanceDue(java.math.BigDecimal.ZERO);
        invoice.setSeller(new InvoiceParty());
        invoice.setBuyer(new InvoiceParty());
        invoice.setItems(new ArrayList<>());
        ensureItemSlots(invoice);

        model.addAttribute("invoice", invoice);
        model.addAttribute("isEdit", false);
        populateEnums(model);
        return "invoices/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("invoice") Invoice invoice, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            ensureNestedObjects(invoice);
            model.addAttribute("isEdit", false);
            populateEnums(model);
            return "invoices/form";
        }

        try {
            invoiceService.create(invoice);
        } catch (RuntimeException ex) {
            bindingResult.reject("invoice.error", ex.getMessage());
            ensureNestedObjects(invoice);
            model.addAttribute("isEdit", false);
            populateEnums(model);
            return "invoices/form";
        }

        return "redirect:/invoices";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("invoice", getOr404(id));
        return "invoices/detail";
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> detailPdf(@PathVariable Long id) {
        Invoice invoice = getOr404(id);
        byte[] data = detailPdfReportService.buildEntityDetailPdf("Invoice", invoice);
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"invoice-" + id + ".pdf\"")
            .contentType(MediaType.APPLICATION_PDF)
            .body(data);
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Invoice invoice = getOr404(id);
        ensureNestedObjects(invoice);
        model.addAttribute("invoice", invoice);
        model.addAttribute("isEdit", true);
        populateEnums(model);
        return "invoices/form";
    }

    @PostMapping("/{id}")
    public String update(
        @PathVariable Long id,
        @Valid @ModelAttribute("invoice") Invoice invoice,
        BindingResult bindingResult,
        Model model
    ) {
        if (bindingResult.hasErrors()) {
            ensureNestedObjects(invoice);
            model.addAttribute("isEdit", true);
            populateEnums(model);
            return "invoices/form";
        }

        try {
            invoiceService.update(id, invoice);
        } catch (RuntimeException ex) {
            bindingResult.reject("invoice.error", ex.getMessage());
            ensureNestedObjects(invoice);
            model.addAttribute("isEdit", true);
            populateEnums(model);
            return "invoices/form";
        }

        return "redirect:/invoices";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        invoiceService.delete(id);
        return "redirect:/invoices";
    }

    private void populateEnums(Model model) {
        model.addAttribute("statuses", Invoice.InvoiceStatus.values());
        model.addAttribute("paymentMethods", Invoice.PaymentMethod.values());
    }

    private void ensureNestedObjects(Invoice invoice) {
        if (invoice.getSeller() == null) {
            invoice.setSeller(new InvoiceParty());
        }
        if (invoice.getBuyer() == null) {
            invoice.setBuyer(new InvoiceParty());
        }
        if (invoice.getItems() == null) {
            invoice.setItems(new ArrayList<>());
        }
        ensureItemSlots(invoice);
    }

    private void ensureItemSlots(Invoice invoice) {
        while (invoice.getItems().size() < ITEM_ROWS) {
            invoice.getItems().add(new InvoiceItem());
        }
    }

    private Invoice getOr404(Long id) {
        try {
            return invoiceService.findById(id);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(NOT_FOUND, ex.getMessage(), ex);
        }
    }
}

