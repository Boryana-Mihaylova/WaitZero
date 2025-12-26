package dev.waitzero.waitzero.repository;

import dev.waitzero.waitzero.model.entity.Location;
import dev.waitzero.waitzero.model.entity.ServiceOffering;
import dev.waitzero.waitzero.model.entity.Ticket;
import dev.waitzero.waitzero.model.entity.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {


    Optional<Ticket> findFirstByLocationAndServiceAndStatusOrderByCreatedAtAsc(
            Location location,
            ServiceOffering service,
            TicketStatus status
    );


    List<Ticket> findByLocationAndStatusOrderByCreatedAtAsc(
            Location location,
            TicketStatus status
    );


    Long countByLocationAndServiceAndStatusAndCreatedAtBefore(
            Location location,
            ServiceOffering service,
            TicketStatus status,
            java.time.LocalDateTime createdAt
    );
}
