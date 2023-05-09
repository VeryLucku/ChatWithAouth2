package com.edu.authservice.Persistance;

import com.edu.authservice.Model.UserData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface UserRepository extends CrudRepository<UserData, UUID> {

    UserData findByUsername(String username);
}
