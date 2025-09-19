package com.example.Challange_Java.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Challange_Java.Entities.Moto;

public interface MotoRepository extends JpaRepository<Moto, Long> {
    
}
