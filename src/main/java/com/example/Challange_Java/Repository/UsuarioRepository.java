package com.example.Challange_Java.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Challange_Java.Entities.User;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}