package dev.waitzero.waitzero.service;

import dev.waitzero.waitzero.model.entity.Location;
import dev.waitzero.waitzero.model.entity.ServiceOffering;
import dev.waitzero.waitzero.model.entity.Ticket;
import dev.waitzero.waitzero.model.entity.User;

import java.util.Optional;

public interface TicketService {

    Ticket createTicket(Location location,
                        ServiceOffering service,
                        String customerName,
                        String customerEmail,
                        User user);

    Optional<Ticket> callNextTicket(Location location, ServiceOffering service);
}
