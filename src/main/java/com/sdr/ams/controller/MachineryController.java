package com.sdr.ams.controller;

import com.sdr.ams.model.tangible.Machinery;
import com.sdr.ams.service.ExportService;
import com.sdr.ams.service.MachineryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/machineries")
public class MachineryController extends CoreEntityCrudController<Machinery> {

    public MachineryController(MachineryService service, ExportService exportService) {
        super(service, exportService, "Machinery", "Machineries", "/machineries");
    }

    @Override
    protected Machinery createEntity() {
        return new Machinery();
    }
}
