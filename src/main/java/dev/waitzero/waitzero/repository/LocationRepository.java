package dev.waitzero.waitzero.repository;

import dev.waitzero.waitzero.model.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    Location findByName(String name);
}
