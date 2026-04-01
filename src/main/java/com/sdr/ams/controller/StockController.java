package com.sdr.ams.controller;

import com.sdr.ams.model.financial.Stock;
import com.sdr.ams.service.StockService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
@RequestMapping("/stocks")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping
    public String list(
        @RequestParam(required = false) String tickerSymbol,
        @RequestParam(required = false) String exchange,
        @RequestParam(required = false) String sector,
        @RequestParam(required = false) Stock.CompanyType companyType,
        Model model
    ) {
        model.addAttribute("stocks", stockService.findAll(tickerSymbol, exchange, sector, companyType));
        model.addAttribute("selectedTickerSymbol", tickerSymbol);
        model.addAttribute("selectedExchange", exchange);
        model.addAttribute("selectedSector", sector);
        model.addAttribute("selectedCompanyType", companyType);
        populateEnums(model);
        return "stocks/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        Stock stock = new Stock();
        stock.setDataSource("manual-entry");
        stock.setCompanyType(Stock.CompanyType.PUBLIC);
        stock.setCompetitivePosition(Stock.CompetitivePosition.CHALLENGER);
        model.addAttribute("stock", stock);
        model.addAttribute("isEdit", false);
        populateEnums(model);
        return "stocks/form";
    }

    @PostMapping
    public String create(
        @Valid @ModelAttribute("stock") Stock stock,
        BindingResult bindingResult,
        Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("isEdit", false);
            populateEnums(model);
            return "stocks/form";
        }
        try {
            stockService.create(stock);
        } catch (RuntimeException ex) {
            bindingResult.reject("stock.error", ex.getMessage());
            model.addAttribute("isEdit", false);
            populateEnums(model);
            return "stocks/form";
        }
        return "redirect:/stocks";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("stock", getOr404(id));
        return "stocks/detail";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("stock", getOr404(id));
        model.addAttribute("isEdit", true);
        populateEnums(model);
        return "stocks/form";
    }

    @PostMapping("/{id}")
    public String update(
        @PathVariable Long id,
        @Valid @ModelAttribute("stock") Stock stock,
        BindingResult bindingResult,
        Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("isEdit", true);
            populateEnums(model);
            return "stocks/form";
        }
        try {
            stockService.update(id, stock);
        } catch (RuntimeException ex) {
            bindingResult.reject("stock.error", ex.getMessage());
            model.addAttribute("isEdit", true);
            populateEnums(model);
            return "stocks/form";
        }
        return "redirect:/stocks";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        stockService.delete(id);
        return "redirect:/stocks";
    }

    private void populateEnums(Model model) {
        model.addAttribute("companyTypes", Stock.CompanyType.values());
        model.addAttribute("competitivePositions", Stock.CompetitivePosition.values());
    }

    private Stock getOr404(Long id) {
        try {
            return stockService.findById(id);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(NOT_FOUND, ex.getMessage(), ex);
        }
    }
}
