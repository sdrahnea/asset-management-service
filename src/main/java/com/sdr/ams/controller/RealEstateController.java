package com.sdr.ams.controller;

import com.sdr.ams.model.tangible.RealEstate;
import com.sdr.ams.service.RealEstateService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/real-estates")
public class RealEstateController extends CoreEntityCrudController<RealEstate> {

    public RealEstateController(RealEstateService service) {
        super(service, "Real Estate", "Real Estates", "/real-estates");
    }

    @Override
    protected RealEstate createEntity() {
        return new RealEstate();
    }
}

