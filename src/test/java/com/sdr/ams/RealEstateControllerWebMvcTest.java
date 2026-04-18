package com.sdr.ams;

import com.sdr.ams.controller.RealEstateController;
import com.sdr.ams.model.tangible.RealEstate;
import com.sdr.ams.service.DetailPdfReportService;
import com.sdr.ams.service.ExportService;
import com.sdr.ams.service.RealEstateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(RealEstateController.class)
class RealEstateControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RealEstateService realEstateService;

    @MockitoBean
    private ExportService exportService;

    @MockitoBean
    private DetailPdfReportService detailPdfReportService;

    @Test
    void listReturnsRealEstateViewWithModel() throws Exception {
        RealEstate realEstate = new RealEstate();
        realEstate.setTitle("HQ Building");
        given(realEstateService.findAll(null, null, null, null, null)).willReturn(List.of(realEstate));

        mockMvc.perform(get("/real-estates"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("realEstates"))
            .andExpect(model().attributeExists("propertyTypes"))
            .andExpect(model().attributeExists("ownershipTypes"))
            .andExpect(view().name("real-estates/list"));
    }

    @Test
    void deleteRedirectsBackToRealEstatesList() throws Exception {
        mockMvc.perform(post("/real-estates/{id}/delete", 7L))
            .andExpect(status().is3xxRedirection())
            .andExpect(header().string("Location", "/real-estates"));

        verify(realEstateService).delete(7L);
    }

    @Test
    void invalidCreateReturnsFormWithValidationErrors() throws Exception {
        mockMvc.perform(post("/real-estates")
                .param("title", "")
                .param("address", "")
                .param("ownerName", "")
                .param("cadastralNumber", "")
                .param("landRegistryNumber", "")
                .param("dataSource", ""))
            .andExpect(status().isOk())
            .andExpect(model().attributeHasErrors("realEstate"))
            .andExpect(model().attributeHasFieldErrors(
                "realEstate",
                "title",
                "address",
                "ownerName",
                "cadastralNumber",
                "landRegistryNumber",
                "propertyType",
                "ownershipType",
                "landArea",
                "currentMarketValue",
                "valuationDate",
                "dataSource"
            ))
            .andExpect(view().name("real-estates/form"));

        verify(realEstateService, never()).create(any());
    }

    @Test
    void invalidUpdateReturnsFormWithValidationErrors() throws Exception {
        mockMvc.perform(post("/real-estates/{id}", 11L)
                .param("title", "")
                .param("address", "")
                .param("ownerName", "")
                .param("cadastralNumber", "")
                .param("landRegistryNumber", "")
                .param("dataSource", ""))
            .andExpect(status().isOk())
            .andExpect(model().attributeHasErrors("realEstate"))
            .andExpect(model().attributeHasFieldErrors(
                "realEstate",
                "title",
                "address",
                "ownerName",
                "cadastralNumber",
                "landRegistryNumber",
                "propertyType",
                "ownershipType",
                "landArea",
                "currentMarketValue",
                "valuationDate",
                "dataSource"
            ))
            .andExpect(view().name("real-estates/form"));

        verify(realEstateService, never()).update(eq(11L), any());
    }

    @Test
    void exportReturnsCsvForFilteredResultSet() throws Exception {
        RealEstate item = new RealEstate();
        item.setTitle("Warehouse");
        byte[] csv = "title\nWarehouse\n".getBytes(StandardCharsets.UTF_8);

        given(realEstateService.findAll(
            eq(RealEstate.PropertyType.HOUSE),
            eq(RealEstate.OwnershipType.INDIVIDUAL),
            eq(RealEstate.NeighborhoodType.URBAN),
            eq(LocalDate.of(2026, 4, 1)),
            eq(RealEstate.MaintenanceStatus.UP_TO_DATE)
        )).willReturn(List.of(item));
        given(exportService.toCsv(List.of(item), "RealEstate")).willReturn(csv);

        mockMvc.perform(get("/real-estates/export")
                .param("format", "csv")
                .param("propertyType", "HOUSE")
                .param("ownershipType", "INDIVIDUAL")
                .param("neighborhoodType", "URBAN")
                .param("valuationDate", "2026-04-01")
                .param("maintenanceStatus", "UP_TO_DATE"))
            .andExpect(status().isOk())
            .andExpect(header().string("Content-Disposition", "attachment; filename=\"real-estates.csv\""))
            .andExpect(content().contentType("text/csv;charset=UTF-8"))
            .andExpect(content().bytes(csv));
    }
}
