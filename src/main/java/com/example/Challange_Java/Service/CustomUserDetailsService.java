package com.example.Challange_Java.Service;

// Agora os usuários ficam no banco.

// Login via formulário /login.

// Só Usuário ADMIN pode criar/editar/excluir motos, USER só pode acessar páginas seguras mas não editar motos.



import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.Challange_Java.Repository.UsuarioRepository;
@Service
public class CustomUserDetailsService implements UserDetailsService{
    private final UsuarioRepository userRepo;

    public CustomUserDetailsService (UsuarioRepository userRepo){
        this.userRepo = userRepo;
     }
     @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         System.out.println("[INFO] Iniciando busca no banco de dados para o usuário: " + username);
         var usuario = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));
         System.out.println("[SUCCESS] Usuário carregado com sucesso do banco: " + usuario.getUsername());

         // Log da senha e do hash armazenado
         System.out.println("Senha inserida: admin123");
         System.out.println("Hash armazenado no banco: " + usuario.getPassword());
        return new User(
                usuario.getUsername(),
                usuario.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getRole().name()))
        );
    }
}
