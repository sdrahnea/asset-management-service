package com.sdr.ams.model.financial;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Embeddable
public class InvoiceParty {

    @NotBlank(message = "Party ID is required")
    @Size(max = 100, message = "Party ID must be at most 100 characters")
    @Column(length = 100)
    private String partyId;

    @NotBlank(message = "Party name is required")
    @Size(max = 255, message = "Party name must be at most 255 characters")
    @Column(length = 255)
    private String name;

    @NotBlank(message = "Party tax ID is required")
    @Size(max = 100, message = "Party tax ID must be at most 100 characters")
    @Column(length = 100)
    private String taxId;

    @NotBlank(message = "Party address is required")
    @Size(max = 500, message = "Party address must be at most 500 characters")
    @Column(length = 500)
    private String address;

    @Size(max = 255, message = "Party contact info must be at most 255 characters")
    @Column(length = 255)
    private String contactInfo;

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }
}

