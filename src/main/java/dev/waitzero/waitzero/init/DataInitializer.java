package dev.waitzero.waitzero.init;

import dev.waitzero.waitzero.model.entity.Location;
import dev.waitzero.waitzero.model.entity.ServiceOffering;
import dev.waitzero.waitzero.repository.LocationRepository;
import dev.waitzero.waitzero.repository.ServiceOfferingRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final LocationRepository locationRepository;
    private final ServiceOfferingRepository serviceOfferingRepository;

    public DataInitializer(LocationRepository locationRepository,
                           ServiceOfferingRepository serviceOfferingRepository) {
        this.locationRepository = locationRepository;
        this.serviceOfferingRepository = serviceOfferingRepository;
    }

    @Override
    public void run(String... args) {


        if (locationRepository.count() > 0) {
            return;
        }


        Location hospital = new Location()
                .setName("City Hospital Sofia")
                .setAddress("Sofia, Center")
                .setTimezone("Europe/Sofia")
                .setActive(true);
        hospital = locationRepository.save(hospital);


        Location lab = new Location()
                .setName("MedLab Diagnostics")
                .setAddress("Sofia, Mladost")
                .setTimezone("Europe/Sofia")
                .setActive(true);
        lab = locationRepository.save(lab);


        Location gpClinic = new Location()
                .setName("Mladost Polyclinic – GP Area")
                .setAddress("Sofia, Mladost")
                .setTimezone("Europe/Sofia")
                .setActive(true);
        gpClinic = locationRepository.save(gpClinic);


        Location municipality = new Location()
                .setName("Municipal Service Center")
                .setAddress("Sofia, Center")
                .setTimezone("Europe/Sofia")
                .setActive(true);
        municipality = locationRepository.save(municipality);


        Location vti = new Location()
                .setName("Vehicle Technical Inspection")
                .setAddress("Sofia, Center")
                .setTimezone("Europe/Sofia")
                .setActive(true);
        vti = locationRepository.save(vti);


        serviceOfferingRepository.save(
                new ServiceOffering()
                        .setName("Registration desk")
                        .setAvgServiceMinutes(10)
                        .setLocation(hospital)
                        .setActive(true)
        );

        serviceOfferingRepository.save(
                new ServiceOffering()
                        .setName("X-ray")
                        .setAvgServiceMinutes(15)
                        .setLocation(hospital)
                        .setActive(true)
        );


        serviceOfferingRepository.save(
                new ServiceOffering()
                        .setName("Blood tests")
                        .setAvgServiceMinutes(15)
                        .setLocation(lab)
                        .setActive(true)
        );

        serviceOfferingRepository.save(
                new ServiceOffering()
                        .setName("Urine analysis")
                        .setAvgServiceMinutes(10)
                        .setLocation(lab)
                        .setActive(true)
        );

        serviceOfferingRepository.save(
                new ServiceOffering()
                        .setName("Dr. Ivanova")
                        .setAvgServiceMinutes(15)
                        .setLocation(gpClinic)
                        .setActive(true)
        );

        serviceOfferingRepository.save(
                new ServiceOffering()
                        .setName("Dr. Petrov")
                        .setAvgServiceMinutes(15)
                        .setLocation(gpClinic)
                        .setActive(true)
        );

        serviceOfferingRepository.save(
                new ServiceOffering()
                        .setName("Dr. Georgiev")
                        .setAvgServiceMinutes(15)
                        .setLocation(gpClinic)
                        .setActive(true)
        );


        serviceOfferingRepository.save(
                new ServiceOffering()
                        .setName("ID card application")
                        .setAvgServiceMinutes(25)
                        .setLocation(municipality)
                        .setActive(true)
        );

        serviceOfferingRepository.save(
                new ServiceOffering()
                        .setName("Address registration")
                        .setAvgServiceMinutes(20)
                        .setLocation(municipality)
                        .setActive(true)
        );

        serviceOfferingRepository.save(
                new ServiceOffering()
                        .setName("Certificate request")
                        .setAvgServiceMinutes(15)
                        .setLocation(municipality)
                        .setActive(true)
        );


        serviceOfferingRepository.save(
                new ServiceOffering()
                        .setName("Annual inspection")
                        .setAvgServiceMinutes(35)
                        .setLocation(vti)
                        .setActive(true)
        );
    }
}
