package com.sdr.ams.controller;

import com.sdr.ams.model.intangible.Patent;
import com.sdr.ams.service.PatentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/patents")
public class PatentController extends CoreEntityCrudController<Patent> {

    public PatentController(PatentService service) {
        super(service, "Patent", "Patents", "/patents");
    }

    @Override
    protected Patent createEntity() {
        return new Patent();
    }
}

