package dev.waitzero.waitzero.web;

import dev.waitzero.waitzero.model.entity.Location;
import dev.waitzero.waitzero.model.entity.ServiceOffering;
import dev.waitzero.waitzero.model.entity.Ticket;
import dev.waitzero.waitzero.repository.LocationRepository;
import dev.waitzero.waitzero.repository.ServiceOfferingRepository;
import dev.waitzero.waitzero.service.TicketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    private final LocationRepository locationRepository;
    private final ServiceOfferingRepository serviceOfferingRepository;
    private final TicketService ticketService;

    public TicketController(LocationRepository locationRepository,
                            ServiceOfferingRepository serviceOfferingRepository,
                            TicketService ticketService) {
        this.locationRepository = locationRepository;
        this.serviceOfferingRepository = serviceOfferingRepository;
        this.ticketService = ticketService;
    }

    @PostMapping("/take")
    public String takeTicket(@RequestParam Long locationId,
                             @RequestParam Long serviceId,
                             Model model) {

        Optional<Location> locationOpt = locationRepository.findById(locationId);
        Optional<ServiceOffering> serviceOpt = serviceOfferingRepository.findById(serviceId);

        if (locationOpt.isEmpty() || serviceOpt.isEmpty()) {
            return "redirect:/locations";
        }

        Ticket ticket = ticketService.createTicket(
                locationOpt.get(),
                serviceOpt.get(),
                null,
                null
        );

        model.addAttribute("ticket", ticket);

        return "ticket-created";
    }

}
