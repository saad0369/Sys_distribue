package com.sysdistribue.auth_api.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import com.sysdistribue.auth_api.entities.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>{
    Optional<User> findByEmail(String email);

}
