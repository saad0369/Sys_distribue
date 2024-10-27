package com.sysdistribue.auth_api.configs;

import com.sysdistribue.auth_api.entities.Role;
import com.sysdistribue.auth_api.repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner{
    private final RoleRepository roleRepository;


    public DataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public void run(String... args) {
        if (roleRepository.count() == 0) {
            Role adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            roleRepository.save(adminRole);

            Role userRole = new Role();
            userRole.setName("ROLE_USER");
            roleRepository.save(userRole);
        }
    }
}
