package com.oop.web1.repositories;

import com.oop.web1.configs.Role;
import com.oop.web1.entitys.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UsersEntity, Integer> {
    Optional<UsersEntity> findUsersEntityByEmail(String email);
    Optional<UsersEntity> findUsersEntityByUserId(int userId);

    List<UsersEntity> findAllByRole(Role role);
}
