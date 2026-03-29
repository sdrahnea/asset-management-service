package com.sdr.ams.controller;

import com.sdr.ams.model.intangible.Copyright;
import com.sdr.ams.service.CopyrightService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/copyrights")
public class CopyrightController extends CoreEntityCrudController<Copyright> {

    public CopyrightController(CopyrightService service) {
        super(service, "Copyright", "Copyrights", "/copyrights");
    }

    @Override
    protected Copyright createEntity() {
        return new Copyright();
    }
}

