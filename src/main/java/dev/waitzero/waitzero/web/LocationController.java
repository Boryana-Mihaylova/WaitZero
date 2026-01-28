package dev.waitzero.waitzero.web;



import dev.waitzero.waitzero.model.entity.Location;
import dev.waitzero.waitzero.model.entity.ServiceOffering;
import dev.waitzero.waitzero.repository.LocationRepository;
import dev.waitzero.waitzero.repository.ServiceOfferingRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/locations")
public class LocationController {

    private final LocationRepository locationRepository;
    private final ServiceOfferingRepository serviceOfferingRepository;

    public LocationController(LocationRepository locationRepository, ServiceOfferingRepository serviceOfferingRepository) {
        this.locationRepository = locationRepository;
        this.serviceOfferingRepository = serviceOfferingRepository;
    }

    @GetMapping
    public String allLocations(Model model) {
        model.addAttribute("locations", locationRepository.findAll());
        return "locations";
    }

    @GetMapping("/{id}")
    public String locationDetails(@PathVariable Long id, Model model) {
        Optional<Location> locationOpt = locationRepository.findById(id);

        if (locationOpt.isEmpty()) {
            return "redirect:/locations";
        }

        Location location = locationOpt.get();
        List<ServiceOffering> services =
                serviceOfferingRepository.findByLocationAndActiveTrue(location);

        model.addAttribute("location", location);
        model.addAttribute("services", services);

        return "location-details";
    }

}
