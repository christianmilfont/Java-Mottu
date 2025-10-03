package com.example.Challange_Java.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.Challange_Java.DTO.UserDTO;
import com.example.Challange_Java.Entities.User;
import com.example.Challange_Java.Repository.UsuarioRepository;

import jakarta.validation.Valid;


@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UsuarioRepository userRepository;

    //Listagem dos usuarios (Admin)
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String listUsers(Model model){
        model.addAttribute("users", userRepository.findAll());
        return "users/list"; // Thymeleaf template: users/list.html
    }
    // Formulário para criar novo usuário
    @GetMapping("/new")
    @PreAuthorize("hasRole('ADMIN')")
    public String newUserForm(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "users/form"; // users/form.html
    }
    // Salvar usuário (novo ou editado)
    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public String saveUser(@Valid @ModelAttribute("userDTO") UserDTO userDTO,
                           BindingResult result) {
        if (result.hasErrors()) {
            return "motos/list";
        }

        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        //user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());
        userRepository.save(user);

        return "redirect:/users";
    }
    
    // Formulário de edição
    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editUserForm(@PathVariable Long id, Model model) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário inválido:" + id));
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        //dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setRole(user.getRole());
        model.addAttribute("userDTO", dto);
        return "users/form";
    }

    // Deletar usuário
    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "redirect:/users";
    }

}
