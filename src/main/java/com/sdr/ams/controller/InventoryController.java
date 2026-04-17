package com.sdr.ams.controller;

import com.sdr.ams.model.tangible.Inventory;
import com.sdr.ams.service.InventoryService;
import com.sdr.ams.service.ExportService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/inventories")
public class InventoryController extends CoreEntityCrudController<Inventory> {

    public InventoryController(InventoryService service, ExportService exportService) {
        super(service, exportService, "Inventory", "Inventories", "/inventories");
    }

    @Override
    protected Inventory createEntity() {
        return new Inventory();
    }
}
