package dev.waitzero.waitzero.web;


import dev.waitzero.waitzero.model.entity.Ticket;
import dev.waitzero.waitzero.model.entity.TicketStatus;
import dev.waitzero.waitzero.repository.TicketRepository;
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

    private final TicketRepository ticketRepository;

    public StaffController(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @GetMapping("/panel")
    public String staffPanel(Model model) {

        List<Ticket> waitingTickets =
                ticketRepository.findByStatusOrderByCreatedAtAsc(TicketStatus.WAITING);

        model.addAttribute("waitingTickets", waitingTickets);

        return "staff-panel";
    }

    @PostMapping("/call-next")
    public String callNext(Model model) {

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
