package com.sdr.ams.financial;

import com.sdr.ams.core.CoreEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "stock")
public class Stock extends CoreEntity {
}
