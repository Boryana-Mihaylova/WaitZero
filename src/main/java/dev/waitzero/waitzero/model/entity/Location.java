package dev.waitzero.waitzero.model.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "locations")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column
    private String address;

    @Column
    private String timezone;

    @Column(nullable = false)
    private boolean active = true;

    public Location() {
    }

    public Long getId() {
        return id;
    }

    public Location setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Location setName(String name) {
        this.name = name;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Location setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getTimezone() {
        return timezone;
    }

    public Location setTimezone(String timezone) {
        this.timezone = timezone;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public Location setActive(boolean active) {
        this.active = active;
        return this;
    }

}
