package com.oop.web1.repositories;

import com.oop.web1.entitys.AuthenticationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthenticationRepository extends JpaRepository<AuthenticationEntity, Integer> {
    AuthenticationEntity findByApiKey(String apiKey);
    AuthenticationEntity deleteByApiKey(String apiKey);
}
