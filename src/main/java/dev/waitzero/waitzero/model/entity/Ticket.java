package dev.waitzero.waitzero.model.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "ticket_number", nullable = false)
    private Integer ticketNumber;

    @ManyToOne(optional = false)
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne(optional = false)
    @JoinColumn(name = "service_id")
    private ServiceOffering service;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "customer_email")
    private String customerEmail;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketStatus status = TicketStatus.WAITING;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "called_at")
    private LocalDateTime calledAt;

    @Column(name = "served_at")
    private LocalDateTime servedAt;




    public Ticket() {
    }

    public Long getId() {
        return id;
    }

    public Ticket setId(Long id) {
        this.id = id;
        return this;
    }

    public Integer getTicketNumber() {
        return ticketNumber;
    }

    public Ticket setTicketNumber(Integer ticketNumber) {
        this.ticketNumber = ticketNumber;
        return this;
    }

    public Location getLocation() {
        return location;
    }

    public Ticket setLocation(Location location) {
        this.location = location;
        return this;
    }

    public ServiceOffering getService() {
        return service;
    }

    public Ticket setService(ServiceOffering service) {
        this.service = service;
        return this;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Ticket setCustomerName(String customerName) {
        this.customerName = customerName;
        return this;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public Ticket setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
        return this;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public Ticket setStatus(TicketStatus status) {
        this.status = status;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Ticket setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public LocalDateTime getCalledAt() {
        return calledAt;
    }

    public Ticket setCalledAt(LocalDateTime calledAt) {
        this.calledAt = calledAt;
        return this;
    }

    public LocalDateTime getServedAt() {
        return servedAt;
    }

    public Ticket setServedAt(LocalDateTime servedAt) {
        this.servedAt = servedAt;
        return this;
    }
}
