package com.oop.web1.repositories;

import com.oop.web1.entitys.QAAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<QAAEntity, Integer> {

}
