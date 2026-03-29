package com.sdr.ams.model.financial;

import com.sdr.ams.model.core.CoreEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "bond")
public class Bond extends CoreEntity {
}
