package com.sdr.ams.model.financial;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;

@Embeddable
public class InvoiceItem {

    @Size(max = 100, message = "Item ID must be at most 100 characters")
    @Column(name = "item_id", length = 100)
    private String itemId;

    @Size(max = 500, message = "Item description must be at most 500 characters")
    @Column(name = "description", length = 500)
    private String description;

    @DecimalMin(value = "0.0", message = "Quantity must be greater than or equal to 0")
    @Digits(integer = 14, fraction = 4)
    @Column(name = "quantity", precision = 18, scale = 4)
    private BigDecimal quantity;

    @DecimalMin(value = "0.0", message = "Unit price must be greater than or equal to 0")
    @Digits(integer = 14, fraction = 4)
    @Column(name = "unit_price", precision = 18, scale = 4)
    private BigDecimal unitPrice;

    @Size(max = 50, message = "Unit of measure must be at most 50 characters")
    @Column(name = "unit_of_measure", length = 50)
    private String unitOfMeasure;

    @DecimalMin(value = "0.0", message = "Tax rate must be at least 0")
    @DecimalMax(value = "100.0", message = "Tax rate must be at most 100")
    @Digits(integer = 3, fraction = 4)
    @Column(name = "tax_rate", precision = 7, scale = 4)
    private BigDecimal taxRate;

    @DecimalMin(value = "0.0", message = "Tax amount must be greater than or equal to 0")
    @Digits(integer = 14, fraction = 4)
    @Column(name = "tax_amount", precision = 18, scale = 4)
    private BigDecimal taxAmount;

    @DecimalMin(value = "0.0", message = "Line total must be greater than or equal to 0")
    @Digits(integer = 14, fraction = 4)
    @Column(name = "line_total", precision = 18, scale = 4)
    private BigDecimal lineTotal;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getLineTotal() {
        return lineTotal;
    }

    public void setLineTotal(BigDecimal lineTotal) {
        this.lineTotal = lineTotal;
    }
}

