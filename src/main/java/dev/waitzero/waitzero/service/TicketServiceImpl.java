package dev.waitzero.waitzero.service;

import dev.waitzero.waitzero.model.entity.Location;
import dev.waitzero.waitzero.model.entity.ServiceOffering;
import dev.waitzero.waitzero.model.entity.Ticket;
import dev.waitzero.waitzero.model.entity.TicketStatus;
import dev.waitzero.waitzero.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService{

    private final TicketRepository ticketRepository;

    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Ticket createTicket(Location location,
                               ServiceOffering service,
                               String customerName,
                               String customerEmail) {


        int lastNumber = ticketRepository.findMaxTicketNumber(location, service);
        int nextNumber = lastNumber + 1;

        Ticket ticket = new Ticket()
                .setLocation(location)
                .setService(service)
                .setCustomerName(customerName)
                .setCustomerEmail(customerEmail)
                .setStatus(TicketStatus.WAITING)
                .setCreatedAt(LocalDateTime.now())
                .setTicketNumber(nextNumber);

        return ticketRepository.save(ticket);
    }

    @Override
    public Optional<Ticket> callNextTicket(Location location, ServiceOffering service) {

        Optional<Ticket> nextWaitingOpt =
                ticketRepository.findFirstByLocationAndServiceAndStatusOrderByCreatedAtAsc(
                        location,
                        service,
                        TicketStatus.WAITING
                );

        if (nextWaitingOpt.isEmpty()) {
            return Optional.empty();
        }

        Ticket ticket = nextWaitingOpt.get();
        ticket.setStatus(TicketStatus.CALLED);
        ticket.setCalledAt(LocalDateTime.now());

        ticketRepository.save(ticket);

        return Optional.of(ticket);
    }
}
