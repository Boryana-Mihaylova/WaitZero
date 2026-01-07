package dev.waitzero.waitzero.web;


import dev.waitzero.waitzero.model.entity.Location;
import dev.waitzero.waitzero.repository.LocationRepository;
import dev.waitzero.waitzero.repository.ServiceOfferingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class LocationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LocationRepository locationRepository;

    @MockBean
    private ServiceOfferingRepository serviceOfferingRepository;



    @Test
    void allLocations_shouldReturnViewWithLocations() throws Exception {
        Location loc = new Location();
        loc.setId(1L);
        loc.setName("Test Location");

        when(locationRepository.findAll()).thenReturn(List.of(loc));

        mockMvc.perform(get("/locations"))
                .andExpect(status().isOk())
                .andExpect(view().name("locations"))
                .andExpect(model().attributeExists("locations"));
    }

    @Test
    void locationDetails_whenLocationMissing_shouldRedirect() throws Exception {

        Long id = 100L;

        when(locationRepository.findById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/locations/{id}", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/locations"));
    }

    @Test
    void locationDetails_whenLocationExists_shouldReturnView() throws Exception {

        Long id = 1L;

        Location loc = new Location();
        loc.setId(id);
        loc.setName("Test Location");

        when(locationRepository.findById(id)).thenReturn(Optional.of(loc));
        when(serviceOfferingRepository.findByLocationAndActiveTrue(loc))
                .thenReturn(List.of());

        mockMvc.perform(get("/locations/{id}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("location-details"))
                .andExpect(model().attributeExists("location"))
                .andExpect(model().attributeExists("services"));
    }
}
