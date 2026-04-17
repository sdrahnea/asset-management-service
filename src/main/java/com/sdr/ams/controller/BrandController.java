package com.sdr.ams.controller;

import com.sdr.ams.model.intangible.Brand;
import com.sdr.ams.service.BrandService;
import com.sdr.ams.service.ExportService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/brands")
public class BrandController extends CoreEntityCrudController<Brand> {

    public BrandController(BrandService service, ExportService exportService) {
        super(service, exportService, "Brand", "Brands", "/brands");
    }

    @Override
    protected Brand createEntity() {
        return new Brand();
    }
}
