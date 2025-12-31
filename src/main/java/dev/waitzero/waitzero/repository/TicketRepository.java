package dev.waitzero.waitzero.repository;

import dev.waitzero.waitzero.model.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query("""
        select coalesce(max(t.ticketNumber), 0)
        from Ticket t
        where t.location = :location
          and t.service = :service
        """)
    int findMaxTicketNumber(Location location, ServiceOffering service);


    long countByLocationAndServiceAndStatus(
            Location location,
            ServiceOffering service,
            TicketStatus status
    );


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


    List<Ticket> findByLocationAndServiceAndStatusOrderByCreatedAtAsc(
            Location location,
            ServiceOffering service,
            TicketStatus status
    );

    List<Ticket> findByStatusOrderByCreatedAtAsc(TicketStatus status);

    Optional<Ticket> findFirstByStatusOrderByCreatedAtAsc(TicketStatus status);

    void deleteByUserAndStatus(User user, TicketStatus status);

    Optional<Ticket> findFirstByUserAndStatusOrderByCreatedAtDesc(
            User user,
            TicketStatus status
    );


}
