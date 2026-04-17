package com.sdr.ams.controller;

import com.sdr.ams.model.core.CoreEntity;
import com.sdr.ams.service.CoreEntityCrudService;
import com.sdr.ams.service.ExportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
public abstract class CoreEntityCrudController<T extends CoreEntity> {

    private final CoreEntityCrudService<T> service;
    private final ExportService exportService;
    private final String singularTitle;
    private final String pluralTitle;
    private final String basePath;
    private final String viewBasePath;

    protected CoreEntityCrudController(
        CoreEntityCrudService<T> service,
        ExportService exportService,
        String singularTitle,
        String pluralTitle,
        String basePath
    ) {
        this.service = service;
        this.exportService = exportService;
        this.singularTitle = singularTitle;
        this.pluralTitle = pluralTitle;
        this.basePath = basePath;
        this.viewBasePath = basePath.startsWith("/") ? basePath.substring(1) : basePath;
    }

    protected abstract T createEntity();

    @GetMapping
    public String list(Model model) {
        model.addAttribute("items", service.findAll());
        model.addAttribute("singularTitle", singularTitle);
        model.addAttribute("pluralTitle", pluralTitle);
        model.addAttribute("basePath", basePath);
        return viewBasePath + "/list";
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> export(@RequestParam(defaultValue = "csv") String format) throws Exception {
        var items = service.findAll();
        String filenameBase = pluralTitle.toLowerCase().replace(" ", "-");

        if ("excel".equalsIgnoreCase(format) || "xlsx".equalsIgnoreCase(format)) {
            byte[] data = exportService.toExcel(items, singularTitle);
            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filenameBase + ".xlsx\"")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(data);
        }

        byte[] data = exportService.toCsv(items, singularTitle);
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filenameBase + ".csv\"")
            .contentType(MediaType.parseMediaType("text/csv; charset=UTF-8"))
            .body(data);
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        T entity = createEntity();
        entity.setCreatedBy("system");
        entity.setUpdatedBy("system");
        model.addAttribute("item", entity);
        model.addAttribute("isEdit", false);
        model.addAttribute("singularTitle", singularTitle);
        model.addAttribute("pluralTitle", pluralTitle);
        model.addAttribute("basePath", basePath);
        return viewBasePath + "/form";
    }

    @PostMapping
    public String create(@ModelAttribute("item") T entity) {
        service.create(entity);
        return "redirect:" + basePath;
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("item", getOr404(id));
        model.addAttribute("isEdit", true);
        model.addAttribute("singularTitle", singularTitle);
        model.addAttribute("pluralTitle", pluralTitle);
        model.addAttribute("basePath", basePath);
        return viewBasePath + "/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute("item") T entity) {
        service.update(id, entity);
        return "redirect:" + basePath;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:" + basePath;
    }

    private T getOr404(Long id) {
        try {
            return service.findById(id);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(NOT_FOUND, ex.getMessage(), ex);
        }
    }
}

