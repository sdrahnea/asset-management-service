package com.sdr.ams.model.financial;

import com.sdr.ams.model.core.CoreEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "bank_account")
public class BankAccount extends CoreEntity {
}
