package com.oop.web1.repositories;

import com.oop.web1.configs.Role;
import com.oop.web1.entitys.MoviesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface MovieRepository extends  JpaRepository<MoviesEntity, Integer>{
}
