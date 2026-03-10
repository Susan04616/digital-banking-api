package com.susan.digitalbanking.digital_banking_api;

import com.susan.digitalbanking.digital_banking_api.entity.Role;
import com.susan.digitalbanking.digital_banking_api.entity.UserEntity;
import com.susan.digitalbanking.digital_banking_api.repository.RoleRepository;
import com.susan.digitalbanking.digital_banking_api.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@SpringBootApplication
public class DigitalBankingApiApplication {

    private static final String DEFAULT_PASSWORD = "password123";
    private static final String DEFAULT_USER = "john";
    private static final String DEFAULT_ROLE = "ROLE_CUSTOMER";

    public static void main(String[] args) {
        SpringApplication.run(DigitalBankingApiApplication.class, args);
    }

    @Bean
    CommandLineRunner createUsers(UserRepository userRepository,
                                  RoleRepository roleRepository,
                                  PasswordEncoder encoder,
                                  PasswordEncoder passwordEncoder) {
        return args -> {
            // Ensure the default role exists
            Role customerRole = roleRepository.findByName(DEFAULT_ROLE)
                    .orElseGet(() -> {
                        Role newRole = new Role();
                        newRole.setName(DEFAULT_ROLE);
                        roleRepository.save(newRole);
                        System.out.println("Role '" + DEFAULT_ROLE + "' created!");
                        return newRole;
                    });

            // Ensure the default user exists
            userRepository.findByUsername(DEFAULT_USER)
                    .orElseGet(() -> {
                        UserEntity user = new UserEntity();
                        user.setUsername(DEFAULT_USER);
                        user.setPassword(encoder.encode(DEFAULT_PASSWORD));
                        user.setRoles(Set.of(customerRole));
                        userRepository.save(user);
                        System.out.println("User '" + DEFAULT_USER + "' created!");
                        return user;
                    });

            // Create ADMIN role if it doesn't exist
            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                    .orElseGet(() -> roleRepository.save(new Role(null, "ROLE_ADMIN")));

            // Create admin user if not exists
            if (userRepository.findByUsername("admin").isEmpty()) {

                UserEntity admin = new UserEntity();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRoles(Set.of(adminRole));

                userRepository.save(admin);

                System.out.println("Admin user created!");
            }
        };

    }

}
