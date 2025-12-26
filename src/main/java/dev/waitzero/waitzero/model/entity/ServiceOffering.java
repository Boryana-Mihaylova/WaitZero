package dev.waitzero.waitzero.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "services")
public class ServiceOffering {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String name;


    @Column(name = "avg_service_minutes")
    private Integer avgServiceMinutes;

    @ManyToOne(optional = false)
    @JoinColumn(name = "location_id")
    private Location location;

    @Column(nullable = false)
    private boolean active = true;

    public ServiceOffering() {
    }

    public Long getId() {
        return id;
    }

    public ServiceOffering setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ServiceOffering setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getAvgServiceMinutes() {
        return avgServiceMinutes;
    }

    public ServiceOffering setAvgServiceMinutes(Integer avgServiceMinutes) {
        this.avgServiceMinutes = avgServiceMinutes;
        return this;
    }

    public Location getLocation() {
        return location;
    }

    public ServiceOffering setLocation(Location location) {
        this.location = location;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public ServiceOffering setActive(boolean active) {
        this.active = active;
        return this;
    }
}
