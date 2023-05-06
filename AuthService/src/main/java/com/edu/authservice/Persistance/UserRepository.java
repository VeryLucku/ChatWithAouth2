package com.edu.authservice.Persistance;

import com.edu.authservice.Model.UserData;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserRepository extends CrudRepository<UserData, UUID> {

    UserData findByUsername(String username);
}
