package dev.waitzero.waitzero.repository;

import dev.waitzero.waitzero.model.entity.Location;
import dev.waitzero.waitzero.model.entity.ServiceOffering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceOfferingRepository extends JpaRepository<ServiceOffering, Long> {

    List<ServiceOffering> findByLocationAndActiveTrue(Location location);

}
