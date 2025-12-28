package dev.waitzero.waitzero.web;

import dev.waitzero.waitzero.model.entity.Location;
import dev.waitzero.waitzero.model.entity.ServiceOffering;
import dev.waitzero.waitzero.model.entity.Ticket;
import dev.waitzero.waitzero.model.entity.TicketStatus;
import dev.waitzero.waitzero.repository.LocationRepository;
import dev.waitzero.waitzero.repository.ServiceOfferingRepository;
import dev.waitzero.waitzero.repository.TicketRepository;
import dev.waitzero.waitzero.service.TicketService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    private final LocationRepository locationRepository;
    private final ServiceOfferingRepository serviceOfferingRepository;
    private final TicketService ticketService;
    private final TicketRepository ticketRepository;

    public TicketController(LocationRepository locationRepository,
                            ServiceOfferingRepository serviceOfferingRepository,
                            TicketService ticketService, TicketRepository ticketRepository) {
        this.locationRepository = locationRepository;
        this.serviceOfferingRepository = serviceOfferingRepository;
        this.ticketService = ticketService;
        this.ticketRepository = ticketRepository;
    }

    @PostMapping("/take")
    public String takeTicket(@RequestParam Long locationId,
                             @RequestParam Long serviceId,
                             Model model, HttpSession session) {

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

        session.setAttribute("CURRENT_TICKET_ID", ticket.getId());

        populateTicketModel(model, ticket);

        return "ticket-created";
    }

    @GetMapping("/{id}")
    public String viewTicket(@PathVariable Long id, Model model) {

        Optional<Ticket> ticketOpt = ticketRepository.findById(id);

        if (ticketOpt.isEmpty()) {
            return "redirect:/locations";
        }

        Ticket ticket = ticketOpt.get();

        populateTicketModel(model, ticket);

        return "ticket-created";
    }

    @GetMapping("/current")
    public String viewCurrentTicket(HttpSession session) {

        Object idObj = session.getAttribute("CURRENT_TICKET_ID");

        if (idObj == null) {
            return "redirect:/locations";
        }

        Long id = (Long) idObj;

        return "redirect:/tickets/" + id;
    }

    private void populateTicketModel(Model model, Ticket ticket) {

        long peopleAhead = ticketRepository
                .countByLocationAndServiceAndStatusAndCreatedAtBefore(
                        ticket.getLocation(),
                        ticket.getService(),
                        TicketStatus.WAITING,
                        ticket.getCreatedAt()
                );

        Integer avgServiceMinutes = ticket.getService().getAvgServiceMinutes();

        if (avgServiceMinutes == null || avgServiceMinutes <= 0) {
            avgServiceMinutes = 5;
        }

        long estimatedWaitMinutes = peopleAhead * avgServiceMinutes;

        model.addAttribute("ticket", ticket);
        model.addAttribute("peopleAhead", peopleAhead);
        model.addAttribute("estimatedWaitMinutes", estimatedWaitMinutes);
    }


}
