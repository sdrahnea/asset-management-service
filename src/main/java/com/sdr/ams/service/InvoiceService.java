package com.sdr.ams.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

import com.sdr.ams.model.financial.Invoice;
import com.sdr.ams.model.financial.InvoiceItem;
import com.sdr.ams.model.financial.InvoiceParty;
import com.sdr.ams.repository.InvoiceRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InvoiceService {

    private static final String IBAN_PATTERN = "^[A-Z]{2}[0-9]{2}[A-Z0-9]{11,30}$";

    private final InvoiceRepository invoiceRepository;

    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Transactional(readOnly = true)
    public List<Invoice> findAll() {
        return invoiceRepository.findAll();
    }

    public List<Invoice> findAll(
        String invoiceNumber,
        Invoice.InvoiceStatus status,
        String sellerName,
        String buyerName,
        LocalDate issueDateFrom,
        LocalDate issueDateTo,
        LocalDate dueDateFrom,
        LocalDate dueDateTo
    ) {
        return invoiceRepository.search(
            trimToNull(invoiceNumber),
            status,
            trimToNull(sellerName),
            trimToNull(buyerName),
            issueDateFrom,
            issueDateTo,
            dueDateFrom,
            dueDateTo
        );
    }

    @Transactional(readOnly = true)
    public Invoice findById(Long id) {
        return invoiceRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invoice not found for id: " + id));
    }

    public Invoice create(Invoice invoice) {
        normalizeAndValidate(invoice, null);
        return invoiceRepository.save(invoice);
    }

    public Invoice update(Long id, Invoice input) {
        Invoice existing = findById(id);
        BeanUtils.copyProperties(input, existing, "id", "createdAt", "createdBy", "updatedAt", "updatedBy");
        normalizeAndValidate(existing, id);
        return invoiceRepository.save(existing);
    }

    public void delete(Long id) {
        invoiceRepository.deleteById(id);
    }

    private void normalizeAndValidate(Invoice invoice, Long excludeId) {
        invoice.setInvoiceId(normalizeIdentifier(invoice.getInvoiceId()));
        invoice.setInvoiceNumber(normalizeIdentifier(invoice.getInvoiceNumber()));
        invoice.setCurrency(normalizeIdentifier(invoice.getCurrency()));
        invoice.setBankAccountIban(normalizeIdentifier(invoice.getBankAccountIban()));
        invoice.setPaymentTerms(trimToNull(invoice.getPaymentTerms()));

        invoice.setSeller(normalizeParty(invoice.getSeller()));
        invoice.setBuyer(normalizeParty(invoice.getBuyer()));
        normalizeItems(invoice);

        validateMandatoryFields(invoice);
        validateBusinessRules(invoice);
        validateUniqueness(invoice, excludeId);
    }

    private InvoiceParty normalizeParty(InvoiceParty party) {
        InvoiceParty normalized = party == null ? new InvoiceParty() : party;
        normalized.setPartyId(trimToNull(normalized.getPartyId()));
        normalized.setName(trimToNull(normalized.getName()));
        normalized.setTaxId(normalizeIdentifier(normalized.getTaxId()));
        normalized.setAddress(trimToNull(normalized.getAddress()));
        normalized.setContactInfo(trimToNull(normalized.getContactInfo()));
        return normalized;
    }

    private void normalizeItems(Invoice invoice) {
        if (invoice.getItems() == null) {
            return;
        }

        invoice.getItems().removeIf(this::isEffectivelyEmpty);
        for (InvoiceItem item : invoice.getItems()) {
            item.setItemId(normalizeIdentifier(item.getItemId()));
            item.setDescription(trimToNull(item.getDescription()));
            item.setUnitOfMeasure(trimToNull(item.getUnitOfMeasure()));
        }
    }

    private boolean isEffectivelyEmpty(InvoiceItem item) {
        return item == null
            || (trimToNull(item.getItemId()) == null
            && trimToNull(item.getDescription()) == null
            && item.getQuantity() == null
            && item.getUnitPrice() == null
            && trimToNull(item.getUnitOfMeasure()) == null
            && item.getTaxRate() == null
            && item.getTaxAmount() == null
            && item.getLineTotal() == null);
    }

    private void validateMandatoryFields(Invoice invoice) {
        requireText(invoice.getInvoiceId(), "Invoice ID is required");
        requireText(invoice.getInvoiceNumber(), "Invoice number is required");
        requireText(invoice.getCurrency(), "Currency must be ISO 4217");

        if (invoice.getIssueDate() == null) {
            throw new IllegalArgumentException("Issue date is required");
        }
        if (invoice.getDueDate() == null) {
            throw new IllegalArgumentException("Due date is required");
        }
        if (invoice.getStatus() == null) {
            throw new IllegalArgumentException("Status is required");
        }

        validateParty(invoice.getSeller(), "Seller");
        validateParty(invoice.getBuyer(), "Buyer");

        if (invoice.getItems() == null || invoice.getItems().isEmpty()) {
            throw new IllegalArgumentException("At least one invoice item is required");
        }

        requireAmount(invoice.getSubtotal(), "Subtotal is required");
        requireAmount(invoice.getTotalTax(), "Total tax is required");
        requireAmount(invoice.getTotalAmount(), "Total amount is required");
        requireAmount(invoice.getAmountPaid(), "Amount paid is required");
        requireAmount(invoice.getBalanceDue(), "Balance due is required");
    }

    private void validateParty(InvoiceParty party, String label) {
        if (party == null) {
            throw new IllegalArgumentException(label + " information is required");
        }
        requireText(party.getPartyId(), label + " ID is required");
        requireText(party.getName(), label + " name is required");
        requireText(party.getTaxId(), label + " tax ID is required");
        requireText(party.getAddress(), label + " address is required");
    }

    private void validateBusinessRules(Invoice invoice) {
        if (invoice.getDueDate().isBefore(invoice.getIssueDate())) {
            throw new IllegalArgumentException("Due date must be on or after issue date");
        }

        validateCurrency(invoice.getCurrency());
        validateIban(invoice.getBankAccountIban());

        BigDecimal computedSubtotal = BigDecimal.ZERO;
        BigDecimal computedTax = BigDecimal.ZERO;
        Set<String> itemIds = new HashSet<>();

        for (InvoiceItem item : invoice.getItems()) {
            requireText(item.getItemId(), "Item ID is required");
            requireText(item.getDescription(), "Item description is required");
            requireAmount(item.getQuantity(), "Item quantity is required");
            requireAmount(item.getUnitPrice(), "Unit price is required");
            requireAmount(item.getTaxRate(), "Tax rate is required");
            requireAmount(item.getTaxAmount(), "Tax amount is required");
            requireAmount(item.getLineTotal(), "Line total is required");

            if (item.getQuantity().compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Quantity must be greater than zero");
            }
            if (item.getTaxRate().compareTo(BigDecimal.ZERO) < 0 || item.getTaxRate().compareTo(new BigDecimal("100")) > 0) {
                throw new IllegalArgumentException("Tax rate must be between 0 and 100");
            }

            if (!itemIds.add(item.getItemId().toUpperCase(Locale.ROOT))) {
                throw new IllegalArgumentException("Item ID must be unique within invoice");
            }

            BigDecimal netAmount = scale(item.getQuantity().multiply(item.getUnitPrice()));
            BigDecimal expectedLineTotal = scale(netAmount.add(item.getTaxAmount()));
            if (expectedLineTotal.compareTo(scale(item.getLineTotal())) != 0) {
                throw new IllegalArgumentException("Line total mismatch for item: " + item.getItemId());
            }

            computedSubtotal = computedSubtotal.add(netAmount);
            computedTax = computedTax.add(scale(item.getTaxAmount()));
        }

        computedSubtotal = scale(computedSubtotal);
        computedTax = scale(computedTax);
        BigDecimal computedTotal = scale(computedSubtotal.add(computedTax));
        BigDecimal computedBalance = scale(computedTotal.subtract(invoice.getAmountPaid()));

        if (scale(invoice.getSubtotal()).compareTo(computedSubtotal) != 0) {
            throw new IllegalArgumentException("Subtotal mismatch");
        }
        if (scale(invoice.getTotalTax()).compareTo(computedTax) != 0) {
            throw new IllegalArgumentException("Total tax mismatch");
        }
        if (scale(invoice.getTotalAmount()).compareTo(computedTotal) != 0) {
            throw new IllegalArgumentException("Total amount mismatch");
        }
        if (invoice.getAmountPaid().compareTo(invoice.getTotalAmount()) > 0) {
            throw new IllegalArgumentException("Amount paid cannot exceed total amount");
        }
        if (scale(invoice.getBalanceDue()).compareTo(computedBalance) != 0) {
            throw new IllegalArgumentException("Balance due mismatch");
        }
        if (invoice.getStatus() == Invoice.InvoiceStatus.PAID && scale(invoice.getBalanceDue()).compareTo(BigDecimal.ZERO) != 0) {
            throw new IllegalArgumentException("Paid invoices must have zero balance due");
        }
    }

    private void validateUniqueness(Invoice invoice, Long excludeId) {
        if (invoiceRepository.existsInvoiceId(invoice.getInvoiceId(), excludeId)) {
            throw new IllegalArgumentException("Invoice ID must be unique");
        }
        if (invoiceRepository.existsInvoiceNumber(invoice.getInvoiceNumber(), excludeId)) {
            throw new IllegalArgumentException("Invoice number must be unique");
        }
    }

    private void validateCurrency(String currency) {
        if (currency == null || currency.length() != 3) {
            throw new IllegalArgumentException("Currency must be ISO 4217");
        }
    }

    private void validateIban(String iban) {
        if (iban == null) {
            return;
        }
        if (!iban.matches(IBAN_PATTERN) || !isIbanChecksumValid(iban)) {
            throw new IllegalArgumentException("Invalid IBAN");
        }
    }

    private boolean isIbanChecksumValid(String iban) {
        String rearranged = iban.substring(4) + iban.substring(0, 4);
        int remainder = 0;
        for (char c : rearranged.toCharArray()) {
            if (Character.isDigit(c)) {
                remainder = (remainder * 10 + (c - '0')) % 97;
            } else {
                int value = Character.toUpperCase(c) - 'A' + 10;
                remainder = (remainder * 10 + (value / 10)) % 97;
                remainder = (remainder * 10 + (value % 10)) % 97;
            }
        }
        return remainder == 1;
    }

    private String normalizeIdentifier(String value) {
        String trimmed = trimToNull(value);
        if (trimmed == null) {
            return null;
        }
        return trimmed.replaceAll("\\s+", "").toUpperCase(Locale.ROOT);
    }

    private String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private void requireText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
    }

    private void requireAmount(BigDecimal value, String message) {
        if (value == null) {
            throw new IllegalArgumentException(message);
        }
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
    }

    private BigDecimal scale(BigDecimal value) {
        return Objects.requireNonNullElse(value, BigDecimal.ZERO).setScale(4, RoundingMode.HALF_UP);
    }
}

