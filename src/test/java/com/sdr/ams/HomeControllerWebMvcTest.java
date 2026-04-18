package com.sdr.ams;

import com.sdr.ams.controller.HomeController;
import com.sdr.ams.service.DashboardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(HomeController.class)
class HomeControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DashboardService dashboardService;

    @Test
    void rootRouteReturnsIndexView() throws Exception {
        DashboardService.DashboardStats stats = new DashboardService.DashboardStats(
            0,
            BigDecimal.ZERO,
            List.of(),
            Map.of(),
            Map.of(),
            List.of(),
            List.of()
        );
        given(dashboardService.buildStats()).willReturn(stats);

        mockMvc.perform(get("/"))
            .andExpect(status().isOk())
            .andExpect(model().attribute("stats", stats))
            .andExpect(view().name("index"));
    }
}

