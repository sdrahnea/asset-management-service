package com.sdr.ams.service;

import com.sdr.ams.model.tangible.Vehicle;
import com.sdr.ams.repository.VehicleRepository;
import org.springframework.stereotype.Service;

@Service
public class VehicleService extends CoreEntityCrudService<Vehicle> {

    public VehicleService(VehicleRepository repository) {
        super(repository, "Vehicle");
    }
}

