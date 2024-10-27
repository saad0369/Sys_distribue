package com.sysdistribue.auth_api.repositories;

import com.sysdistribue.auth_api.entities.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RoleRepository  extends CrudRepository<Role, Integer> {
    Role findByName(String name);
}
