package dev.waitzero.waitzero.config;

import dev.waitzero.waitzero.model.entity.User;
import dev.waitzero.waitzero.model.entity.UserRole;
import dev.waitzero.waitzero.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DemoAdminSeeder implements CommandLineRunner {

    private final UserRepository userRepository;

    @Value("${DEMO_MODE:false}")
    private boolean demoMode;

    @Value("${DEMO_ADMIN_USERNAME:admin}")
    private String adminUsername;

    @Value("${DEMO_ADMIN_PASSWORD:admin123}")
    private String adminPassword;

    @Value("${DEMO_ADMIN_EMAIL:admin@waitzero.demo}")
    private String adminEmail;

    public DemoAdminSeeder(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        if (!demoMode) {
            return;
        }


        if (userRepository.findByUsername(adminUsername).isPresent()) {
            return;
        }

        User admin = new User();
        admin.setUsername(adminUsername);
        admin.setPassword(adminPassword);
        admin.setRole(UserRole.ADMIN);

        userRepository.save(admin);

        System.out.println("Demo admin seeded: " + adminUsername);
    }
}
