package com.example.Challange_Java.Controller;

import com.example.Challange_Java.Entities.User;
import com.example.Challange_Java.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    private final UsuarioRepository userRepository;

    // Injeção do repositório através do construtor
    @Autowired
    public LoginController(UsuarioRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Login (simples, sem autenticação)
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User user) {
        // Usar a instância para chamar o metodo
        Optional<User> foundUser = userRepository.findByUsername(user.getUsername());

        // Verificar se o usuário existe
        if (foundUser.isEmpty()) {
            return ResponseEntity.badRequest().body("Usuário não encontrado!");
        }

        // Verificar a senha
        if (!foundUser.get().getPassword().equals(user.getPassword())) {
            return ResponseEntity.badRequest().body("Senha incorreta!");
        }

        return ResponseEntity.ok("Login bem-sucedido!");
    }
}
