package com.yatrago.backend.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yatrago.backend.entity.UsersEntity;

@Repository
public interface UserRepository extends JpaRepository<UsersEntity, UUID> {

    public UsersEntity findByEmail(String email);
    
}
