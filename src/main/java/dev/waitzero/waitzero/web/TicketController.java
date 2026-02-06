package dev.waitzero.waitzero.web;

import dev.waitzero.waitzero.model.entity.*;
import dev.waitzero.waitzero.repository.LocationRepository;
import dev.waitzero.waitzero.repository.ServiceOfferingRepository;
import dev.waitzero.waitzero.repository.TicketRepository;
import dev.waitzero.waitzero.repository.UserRepository;
import dev.waitzero.waitzero.service.TicketService;
import dev.waitzero.waitzero.util.CurrentUser;
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
    private final CurrentUser currentUser;
    private final UserRepository userRepository;

    public TicketController(LocationRepository locationRepository,
                            ServiceOfferingRepository serviceOfferingRepository,
                            TicketService ticketService, TicketRepository ticketRepository, CurrentUser currentUser, UserRepository userRepository) {
        this.locationRepository = locationRepository;
        this.serviceOfferingRepository = serviceOfferingRepository;
        this.ticketService = ticketService;
        this.ticketRepository = ticketRepository;
        this.currentUser = currentUser;
        this.userRepository = userRepository;
    }

    @PostMapping("/take")
    public String takeTicket(@RequestParam Long locationId,
                             @RequestParam Long serviceId,
                             Model model, HttpSession session) {

        if (currentUser.getId() == null) {
            return "redirect:/users/login";
        }

        Optional<User> userOpt = userRepository.findById(currentUser.getId());
        if (userOpt.isEmpty()) {
            return "redirect:/users/login";
        }
        User user = userOpt.get();

        Optional<Location> locationOpt = locationRepository.findById(locationId);
        Optional<ServiceOffering> serviceOpt = serviceOfferingRepository.findById(serviceId);

        if (locationOpt.isEmpty() || serviceOpt.isEmpty()) {
            return "redirect:/locations";
        }

        Ticket ticket = ticketService.createTicket(
                locationOpt.get(),
                serviceOpt.get(),
                null,
                null,
                user
        );

        session.setAttribute("CURRENT_TICKET_ID", ticket.getId());

        populateTicketModel(model, ticket);

        return "redirect:/tickets/" + ticket.getId();
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
    public String viewCurrentTicket(Model model) {

        if (currentUser.getId() == null) {
            return "redirect:/users/login";
        }

        var userOpt = userRepository.findById(currentUser.getId());

        if (userOpt.isEmpty()) {
            return "redirect:/users/login";
        }

        var user = userOpt.get();


        var ticketOpt = ticketRepository
                .findFirstByUserAndStatusOrderByCreatedAtDesc(user, TicketStatus.WAITING);

        if (ticketOpt.isEmpty()) {

            model.addAttribute("ticket", null);
            model.addAttribute("peopleAhead", 0);
            model.addAttribute("estimatedWaitMinutes", null);
            return "ticket-created";
        }

        Ticket ticket = ticketOpt.get();
        populateTicketModel(model, ticket);

        return "ticket-created";
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
