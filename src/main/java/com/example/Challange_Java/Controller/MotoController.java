package com.example.Challange_Java.Controller;

import com.example.Challange_Java.DTO.MotoDTO;
import com.example.Challange_Java.Entities.Moto;
import com.example.Challange_Java.Repository.MotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/motos")
public class MotoController {

    @Autowired
    private MotoRepository motoRepository;

    // Listar motos (USER e ADMIN)
    @GetMapping("/listagem")
    public String listMotos(Model model) {
        model.addAttribute("motos", motoRepository.findAll());
        return "motos/list"; // Thymeleaf template: motos/list.html
    }

    // Formulário para criar nova moto
    @GetMapping("/new")
    @PreAuthorize("hasRole('ADMIN')")
    public String newMotoForm(Model model) {
        model.addAttribute("motoDTO", new MotoDTO());
        return "motos/form"; // motos/form.html
    }

    // Salvar moto (novo ou editado)
    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public String saveMoto(@Valid @ModelAttribute("motoDTO") MotoDTO motoDTO,
                           BindingResult result) {
        if (result.hasErrors()) {
            return "motos/form";
        }

        Moto moto = new Moto();
        moto.setId(motoDTO.getId());
        moto.setMarca(motoDTO.getMarca());
        moto.setModelo(motoDTO.getModelo());
        moto.setAno(motoDTO.getAno());
        moto.setCor(motoDTO.getCor());
        moto.setCilindrada(motoDTO.getCilindrada());
        moto.setStatus(motoDTO.getStatus());
        moto.setPosicao(motoDTO.getPosicao());
        moto.setLatitude(motoDTO.getLatitude());
        moto.setLongitude(motoDTO.getLongitude());

        motoRepository.save(moto);
        return "redirect:/motos";
    }

    // Formulário de edição
    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editMotoForm(@PathVariable Long id, Model model) {
        Moto moto = motoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Moto inválida:" + id));

        MotoDTO dto = new MotoDTO();
        dto.setId(moto.getId());
        dto.setMarca(moto.getMarca());
        dto.setModelo(moto.getModelo());
        dto.setAno(moto.getAno());
        dto.setCor(moto.getCor());
        dto.setCilindrada(moto.getCilindrada());
        dto.setStatus(moto.getStatus());
        dto.setPosicao(moto.getPosicao());
        dto.setLatitude(moto.getLatitude());
        dto.setLongitude(moto.getLongitude());

        model.addAttribute("motoDTO", dto);
        return "motos/form";
    }

    // Deletar moto
    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteMoto(@PathVariable Long id) {
        motoRepository.deleteById(id);
        return "redirect:/motos";
    }
}
