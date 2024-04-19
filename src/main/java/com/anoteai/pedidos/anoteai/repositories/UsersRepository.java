package com.anoteai.pedidos.anoteai.repositories;

import com.anoteai.pedidos.anoteai.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository  extends MongoRepository<Users, String> {
    UserDetails findByLogin(String login);
}
