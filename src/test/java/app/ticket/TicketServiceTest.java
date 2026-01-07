package app.ticket;


import dev.waitzero.waitzero.model.entity.*;
import dev.waitzero.waitzero.repository.TicketRepository;
import dev.waitzero.waitzero.service.TicketServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import java.time.LocalDateTime;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;



@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketServiceImpl ticketService;

    @Test
    void givenExistingWaitingTicket_whenCreateTicket_thenOldIsDeletedAndNewNumberIsMaxPlusOne() {

        Location location = new Location().setId(1L).setName("Location A");
        ServiceOffering service = new ServiceOffering().setId(1L).setName("Blood tests");

        User user = new User();
        user.setId(10L);
        user.setUsername("bory");


        when(ticketRepository.findMaxTicketNumber(location, service)).thenReturn(5);


        when(ticketRepository.save(any(Ticket.class))).thenAnswer(invocation -> invocation.getArgument(0));


        Ticket result = ticketService.createTicket(location, service, null, null, user);



        verify(ticketRepository, times(1))
                .deleteByUserAndStatus(user, TicketStatus.WAITING);


        assertEquals(6, result.getTicketNumber());


        assertSame(location, result.getLocation());
        assertSame(service, result.getService());
        assertSame(user, result.getUser());
        assertEquals(TicketStatus.WAITING, result.getStatus());
        assertNotNull(result.getCreatedAt());
    }

    @Test
    void givenNoPreviousTickets_whenCreateTicket_thenFirstTicketNumberIsOne() {

        Location location = new Location().setId(2L).setName("Location B");
        ServiceOffering service = new ServiceOffering().setId(3L).setName("X-ray");

        User user = new User();
        user.setId(20L);
        user.setUsername("user2");

        when(ticketRepository.findMaxTicketNumber(location, service)).thenReturn(0);
        when(ticketRepository.save(any(Ticket.class))).thenAnswer(invocation -> invocation.getArgument(0));


        Ticket result = ticketService.createTicket(location, service, null, null, user);


        assertEquals(1, result.getTicketNumber());
        assertEquals(TicketStatus.WAITING, result.getStatus());
    }

    @Test
    void givenNoWaitingTickets_whenCallNextTicket_thenReturnEmptyOptional() {

        Location location = new Location().setId(1L).setName("Location A");
        ServiceOffering service = new ServiceOffering().setId(1L).setName("Blood tests");

        when(ticketRepository.findFirstByLocationAndServiceAndStatusOrderByCreatedAtAsc(
                location, service, TicketStatus.WAITING
        )).thenReturn(Optional.empty());


        Optional<Ticket> result = ticketService.callNextTicket(location, service);


        assertTrue(result.isEmpty());
        verify(ticketRepository, never()).save(any(Ticket.class));
    }

    @Test
    void givenWaitingTicket_whenCallNextTicket_thenStatusBecomesCalledAndCalledAtIsSet() {

        Location location = new Location().setId(1L).setName("Location A");
        ServiceOffering service = new ServiceOffering().setId(1L).setName("Blood tests");

        Ticket waitingTicket = new Ticket()
                .setId(100L)
                .setLocation(location)
                .setService(service)
                .setTicketNumber(7)
                .setStatus(TicketStatus.WAITING)
                .setCreatedAt(LocalDateTime.now().minusMinutes(5));

        when(ticketRepository.findFirstByLocationAndServiceAndStatusOrderByCreatedAtAsc(
                location, service, TicketStatus.WAITING
        )).thenReturn(Optional.of(waitingTicket));

        when(ticketRepository.save(any(Ticket.class))).thenAnswer(invocation -> invocation.getArgument(0));


        Optional<Ticket> resultOpt = ticketService.callNextTicket(location, service);


        assertTrue(resultOpt.isPresent());
        Ticket result = resultOpt.get();

        assertEquals(TicketStatus.CALLED, result.getStatus());
        assertNotNull(result.getCalledAt());


        verify(ticketRepository, times(1)).save(waitingTicket);
    }
    @Test
    void callNextTicket_whenNoWaitingTickets_returnsEmpty() {

        Location location = new Location();
        location.setId(1L);

        ServiceOffering service = new ServiceOffering();
        service.setId(2L);

        when(ticketRepository
                .findFirstByLocationAndServiceAndStatusOrderByCreatedAtAsc(
                        location, service, dev.waitzero.waitzero.model.entity.TicketStatus.WAITING
                ))
                .thenReturn(Optional.empty());

        Optional<?> result = ticketService.callNextTicket(location, service);

        assertTrue(result.isEmpty());

        verify(ticketRepository, never()).save(any());
    }

    @Test
    void callNextTicket_whenTicketAlreadyCalled_doesNotChangeIt() {

        Location location = new Location();
        location.setId(1L);

        ServiceOffering service = new ServiceOffering();
        service.setId(2L);

        var ticket = new dev.waitzero.waitzero.model.entity.Ticket();
        ticket.setStatus(dev.waitzero.waitzero.model.entity.TicketStatus.CALLED);

        when(ticketRepository
                .findFirstByLocationAndServiceAndStatusOrderByCreatedAtAsc(
                        location, service, dev.waitzero.waitzero.model.entity.TicketStatus.WAITING
                ))
                .thenReturn(Optional.of(ticket));

        Optional<?> result = ticketService.callNextTicket(location, service);

        assertTrue(result.isPresent());
        assertEquals(dev.waitzero.waitzero.model.entity.TicketStatus.CALLED, ticket.getStatus());

        verify(ticketRepository).save(ticket);
    }

}
