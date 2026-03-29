package com.sdr.ams.controller;

import com.sdr.ams.model.intangible.Reputation;
import com.sdr.ams.service.ReputationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reputations")
public class ReputationController extends CoreEntityCrudController<Reputation> {

    public ReputationController(ReputationService service) {
        super(service, "Reputation", "Reputations", "/reputations");
    }

    @Override
    protected Reputation createEntity() {
        return new Reputation();
    }
}

