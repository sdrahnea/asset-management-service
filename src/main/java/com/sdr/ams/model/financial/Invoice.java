package com.sdr.ams.model.financial;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sdr.ams.model.core.CoreEntity;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@AttributeOverride(name = "name", column = @Column(name = "invoice_id", nullable = false, length = 36))
@Table(
    name = "invoice",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_invoice_invoice_id", columnNames = "invoice_id"),
        @UniqueConstraint(name = "uk_invoice_invoice_number", columnNames = "invoice_number")
    }
)
public class Invoice extends CoreEntity {

    public enum InvoiceStatus { ISSUED, PARTIALLY_PAID, PAID, OVERDUE, CANCELLED }

    public enum PaymentMethod { BANK_TRANSFER, CARD, CASH, OTHER }

    @Override
    @JsonIgnore
    public String getName() {
        return super.getName();
    }

    @Override
    @JsonIgnore
    public void setName(String name) {
        super.setName(name);
    }

    @NotBlank(message = "Invoice ID is required")
    @Size(max = 36, message = "Invoice ID must be at most 36 characters")
    public String getInvoiceId() {
        return getName();
    }

    public void setInvoiceId(String invoiceId) {
        setName(invoiceId);
    }

    @NotBlank(message = "Invoice number is required")
    @Size(max = 50, message = "Invoice number must be at most 50 characters")
    @Column(name = "invoice_number", nullable = false, length = 50)
    private String invoiceNumber;

    @NotNull(message = "Issue date is required")
    @Column(name = "issue_date", nullable = false)
    private LocalDate issueDate;

    @NotNull(message = "Due date is required")
    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @NotBlank(message = "Currency is required")
    @Size(min = 3, max = 3, message = "Currency must be a 3-letter ISO code")
    @Column(name = "currency", nullable = false, length = 3)
    private String currency;

    @NotNull(message = "Status is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private InvoiceStatus status;

    @Valid
    @NotNull(message = "Seller information is required")
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "partyId", column = @Column(name = "seller_id", nullable = false, length = 100)),
        @AttributeOverride(name = "name", column = @Column(name = "seller_name", nullable = false, length = 255)),
        @AttributeOverride(name = "taxId", column = @Column(name = "seller_tax_id", nullable = false, length = 100)),
        @AttributeOverride(name = "address", column = @Column(name = "seller_address", nullable = false, length = 500)),
        @AttributeOverride(name = "contactInfo", column = @Column(name = "seller_contact_info", length = 255))
    })
    private InvoiceParty seller = new InvoiceParty();

    @Valid
    @NotNull(message = "Buyer information is required")
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "partyId", column = @Column(name = "buyer_id", nullable = false, length = 100)),
        @AttributeOverride(name = "name", column = @Column(name = "buyer_name", nullable = false, length = 255)),
        @AttributeOverride(name = "taxId", column = @Column(name = "buyer_tax_id", nullable = false, length = 100)),
        @AttributeOverride(name = "address", column = @Column(name = "buyer_address", nullable = false, length = 500)),
        @AttributeOverride(name = "contactInfo", column = @Column(name = "buyer_contact_info", length = 255))
    })
    private InvoiceParty buyer = new InvoiceParty();

    @Valid
    @NotEmpty(message = "At least one invoice item is required")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "invoice_item",
        joinColumns = @JoinColumn(name = "invoice_fk"),
        uniqueConstraints = {
            @UniqueConstraint(name = "uk_invoice_item_id_per_invoice", columnNames = {"invoice_fk", "item_id"})
        }
    )
    @OrderColumn(name = "line_no")
    private List<InvoiceItem> items = new ArrayList<>();

    @NotNull(message = "Subtotal is required")
    @DecimalMin(value = "0.0", message = "Subtotal must be greater than or equal to 0")
    @Digits(integer = 14, fraction = 4)
    @Column(name = "subtotal", precision = 18, scale = 4, nullable = false)
    private BigDecimal subtotal;

    @NotNull(message = "Total tax is required")
    @DecimalMin(value = "0.0", message = "Total tax must be greater than or equal to 0")
    @Digits(integer = 14, fraction = 4)
    @Column(name = "total_tax", precision = 18, scale = 4, nullable = false)
    private BigDecimal totalTax;

    @NotNull(message = "Total amount is required")
    @DecimalMin(value = "0.0", message = "Total amount must be greater than or equal to 0")
    @Digits(integer = 14, fraction = 4)
    @Column(name = "total_amount", precision = 18, scale = 4, nullable = false)
    private BigDecimal totalAmount;

    @NotNull(message = "Amount paid is required")
    @DecimalMin(value = "0.0", message = "Amount paid must be greater than or equal to 0")
    @Digits(integer = 14, fraction = 4)
    @Column(name = "amount_paid", precision = 18, scale = 4, nullable = false)
    private BigDecimal amountPaid;

    @NotNull(message = "Balance due is required")
    @DecimalMin(value = "0.0", message = "Balance due must be greater than or equal to 0")
    @Digits(integer = 14, fraction = 4)
    @Column(name = "balance_due", precision = 18, scale = 4, nullable = false)
    private BigDecimal balanceDue;

    @Size(max = 100, message = "Payment terms must be at most 100 characters")
    @Column(name = "payment_terms", length = 100)
    private String paymentTerms;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", length = 20)
    private PaymentMethod paymentMethod;

    @Size(max = 34, message = "IBAN must be at most 34 characters")
    @Column(name = "bank_account_iban", length = 34)
    private String bankAccountIban;

    @AssertTrue(message = "Due date must be on or after issue date")
    public boolean isDueDateValid() {
        return issueDate == null || dueDate == null || !dueDate.isBefore(issueDate);
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }

    public InvoiceParty getSeller() {
        return seller;
    }

    public void setSeller(InvoiceParty seller) {
        this.seller = seller;
    }

    public InvoiceParty getBuyer() {
        return buyer;
    }

    public void setBuyer(InvoiceParty buyer) {
        this.buyer = buyer;
    }

    public List<InvoiceItem> getItems() {
        return items;
    }

    public void setItems(List<InvoiceItem> items) {
        this.items = items;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(BigDecimal totalTax) {
        this.totalTax = totalTax;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    public BigDecimal getBalanceDue() {
        return balanceDue;
    }

    public void setBalanceDue(BigDecimal balanceDue) {
        this.balanceDue = balanceDue;
    }

    public String getPaymentTerms() {
        return paymentTerms;
    }

    public void setPaymentTerms(String paymentTerms) {
        this.paymentTerms = paymentTerms;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getBankAccountIban() {
        return bankAccountIban;
    }

    public void setBankAccountIban(String bankAccountIban) {
        this.bankAccountIban = bankAccountIban;
    }
}

