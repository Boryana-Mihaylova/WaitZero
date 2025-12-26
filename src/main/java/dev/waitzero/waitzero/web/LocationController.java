package dev.waitzero.waitzero.web;



import dev.waitzero.waitzero.repository.LocationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/locations")
public class LocationController {

    private final LocationRepository locationRepository;

    public LocationController(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @GetMapping
    public String allLocations(Model model) {
        model.addAttribute("locations", locationRepository.findAll());
        return "locations"; // -> templates/locations.html
    }

}
