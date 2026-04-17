package com.sdr.ams.controller;

import com.sdr.ams.model.intangible.Copyright;
import com.sdr.ams.service.CopyrightService;
import com.sdr.ams.service.ExportService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/copyrights")
public class CopyrightController extends CoreEntityCrudController<Copyright> {

    public CopyrightController(CopyrightService service, ExportService exportService) {
        super(service, exportService, "Copyright", "Copyrights", "/copyrights");
    }

    @Override
    protected Copyright createEntity() {
        return new Copyright();
    }
}
