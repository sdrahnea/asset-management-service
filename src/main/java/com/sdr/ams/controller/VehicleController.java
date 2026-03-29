package com.sdr.ams.controller;

import com.sdr.ams.model.tangible.Vehicle;
import com.sdr.ams.service.VehicleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vehicles")
public class VehicleController extends CoreEntityCrudController<Vehicle> {

    public VehicleController(VehicleService service) {
        super(service, "Vehicle", "Vehicles", "/vehicles");
    }

    @Override
    protected Vehicle createEntity() {
        return new Vehicle();
    }
}

