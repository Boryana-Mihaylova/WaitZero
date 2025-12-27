package dev.waitzero.waitzero.web;

import dev.waitzero.waitzero.model.entity.Location;
import dev.waitzero.waitzero.model.entity.ServiceOffering;
import dev.waitzero.waitzero.model.entity.Ticket;
import dev.waitzero.waitzero.model.entity.TicketStatus;
import dev.waitzero.waitzero.repository.LocationRepository;
import dev.waitzero.waitzero.repository.ServiceOfferingRepository;
import dev.waitzero.waitzero.repository.TicketRepository;
import dev.waitzero.waitzero.service.TicketService;
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

    public StaffController(LocationRepository locationRepository,
                           ServiceOfferingRepository serviceOfferingRepository,
                           TicketRepository ticketRepository,
                           TicketService ticketService) {
        this.locationRepository = locationRepository;
        this.serviceOfferingRepository = serviceOfferingRepository;
        this.ticketRepository = ticketRepository;
        this.ticketService = ticketService;
    }

    @GetMapping("/panel")
    public String staffPanel(Model model) {

        Long locationId = 1L;
        Long serviceId = 1L;

        Optional<Location> locOpt = locationRepository.findById(locationId);
        Optional<ServiceOffering> servOpt = serviceOfferingRepository.findById(serviceId);

        if (locOpt.isEmpty() || servOpt.isEmpty()) {
            model.addAttribute("error", "Location or service not found (expected id=1).");
            return "staff-panel";
        }

        Location location = locOpt.get();
        ServiceOffering service = servOpt.get();

        List<Ticket> waitingTickets =
                ticketRepository.findByLocationAndServiceAndStatusOrderByCreatedAtAsc(
                        location, service, TicketStatus.WAITING
                );

        model.addAttribute("location", location);
        model.addAttribute("service", service);
        model.addAttribute("waitingTickets", waitingTickets);

        return "staff-panel";
    }

    @PostMapping("/call-next")
    public String callNext(Model model) {
        Long locationId = 1L;
        Long serviceId = 1L;

        Optional<Location> locOpt = locationRepository.findById(locationId);
        Optional<ServiceOffering> servOpt = serviceOfferingRepository.findById(serviceId);

        if (locOpt.isEmpty() || servOpt.isEmpty()) {
            model.addAttribute("error", "Location or service not found (expected id=1).");
            return "staff-panel";
        }

        Optional<Ticket> calledOpt =
                ticketService.callNextTicket(locOpt.get(), servOpt.get());

        if (calledOpt.isEmpty()) {
            model.addAttribute("info", "No waiting tickets.");
        } else {
            model.addAttribute("calledTicket", calledOpt.get());
        }


        List<Ticket> waitingTickets =
                ticketRepository.findByLocationAndServiceAndStatusOrderByCreatedAtAsc(
                        locOpt.get(), servOpt.get(), TicketStatus.WAITING
                );
        model.addAttribute("waitingTickets", waitingTickets);
        model.addAttribute("location", locOpt.get());
        model.addAttribute("service", servOpt.get());

        return "staff-panel";
    }
}
