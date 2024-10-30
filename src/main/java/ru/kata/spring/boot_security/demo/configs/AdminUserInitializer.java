package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleRepository;
import ru.kata.spring.boot_security.demo.dao.UserRepository;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.Set;

@Configuration
public class AdminUserInitializer {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Bean
    public ApplicationRunner initializeAdminUser() {
        return args -> createUsersAndRoles();
    }

    @Transactional
    public void createUsersAndRoles() {
        if (userRepository.findUserByUserName("admin").isEmpty()) {
            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                    .orElseGet(() -> roleRepository.save(new Role(1L, "ROLE_ADMIN")));
            roleRepository.findByName("ROLE_USER")
                    .orElseGet(() -> roleRepository.save(new Role(2L, "ROLE_USER")));

            User admin = new User();
            admin.setName("Admin");
            admin.setLastName("User");
            admin.setAge((byte) 30);
            admin.setEmail("admin@example.com");
            admin.setUserName("admin");
            admin.setPassword("admin");
            admin.setRoleSet(Set.of(adminRole));

            userRepository.save(admin);
        }
    }
}
