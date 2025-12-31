package dev.waitzero.waitzero.service;

import dev.waitzero.waitzero.model.entity.*;
import dev.waitzero.waitzero.repository.TicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService{

    private final TicketRepository ticketRepository;

    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Transactional
    @Override
    public Ticket createTicket(Location location,
                               ServiceOffering service,
                               String customerName,
                               String customerEmail,
                               User user) {

        ticketRepository.deleteByUserAndStatus(user, TicketStatus.WAITING);

        int maxNumber = ticketRepository.findMaxTicketNumber(location, service);
        int nextNumber = maxNumber + 1;

        Ticket ticket = new Ticket()
                .setLocation(location)
                .setService(service)
                .setTicketNumber(nextNumber)
                .setStatus(TicketStatus.WAITING)
                .setCreatedAt(java.time.LocalDateTime.now())
                .setUser(user);

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
