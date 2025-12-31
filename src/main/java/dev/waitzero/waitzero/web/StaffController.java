package dev.waitzero.waitzero.web;


import dev.waitzero.waitzero.model.entity.Ticket;
import dev.waitzero.waitzero.model.entity.TicketStatus;
import dev.waitzero.waitzero.model.entity.UserRole;
import dev.waitzero.waitzero.repository.LocationRepository;
import dev.waitzero.waitzero.repository.ServiceOfferingRepository;
import dev.waitzero.waitzero.repository.TicketRepository;
import dev.waitzero.waitzero.service.TicketService;
import dev.waitzero.waitzero.util.CurrentUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/staff")
public class StaffController {

    private final LocationRepository locationRepository;
    private final ServiceOfferingRepository serviceOfferingRepository;
    private final TicketRepository ticketRepository;
    private final TicketService ticketService;
    private final CurrentUser currentUser;

    public StaffController(LocationRepository locationRepository,
                           ServiceOfferingRepository serviceOfferingRepository,
                           TicketRepository ticketRepository,
                           TicketService ticketService,
                           CurrentUser currentUser) {
        this.locationRepository = locationRepository;
        this.serviceOfferingRepository = serviceOfferingRepository;
        this.ticketRepository = ticketRepository;
        this.ticketService = ticketService;
        this.currentUser = currentUser;
    }

    private boolean isStaff() {
        return currentUser.getId() != null &&
                (currentUser.getRole() == UserRole.STAFF ||
                        currentUser.getRole() == UserRole.ADMIN);
    }

    @GetMapping("/panel")
    public String staffPanel(Model model) {

        if (!isStaff()) {
            return "redirect:/";
        }

        List<Ticket> waitingTickets =
                ticketRepository.findByStatusOrderByCreatedAtAsc(TicketStatus.WAITING);

        model.addAttribute("waitingTickets", waitingTickets);

        return "staff-panel";
    }

    @PostMapping("/call-next")
    public String callNext(Model model) {

        if (!isStaff()) {
            return "redirect:/";
        }

        Optional<Ticket> nextOpt =
                ticketRepository.findFirstByStatusOrderByCreatedAtAsc(TicketStatus.WAITING);

        if (nextOpt.isEmpty()) {
            model.addAttribute("info", "No waiting tickets.");
        } else {
            Ticket called = nextOpt.get();
            called.setStatus(TicketStatus.CALLED);
            ticketRepository.save(called);

            model.addAttribute("calledTicket", called);
        }

        List<Ticket> waitingTickets =
                ticketRepository.findByStatusOrderByCreatedAtAsc(TicketStatus.WAITING);

        model.addAttribute("waitingTickets", waitingTickets);

        return "staff-panel";
    }
}
