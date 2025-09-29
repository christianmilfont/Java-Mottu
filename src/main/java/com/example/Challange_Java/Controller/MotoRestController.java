package com.example.Challange_Java.Controller;

import com.example.Challange_Java.Entities.Moto;
import com.example.Challange_Java.Repository.MotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/motos") // <-- rota separada para o app
public class MotoRestController {

    @Autowired
    private MotoRepository motoRepository;

    // Listar todas as motos (USER e ADMIN)
    @GetMapping
    public List<Moto> listarMotos() {
        return motoRepository.findAll();
    }

    // Buscar moto por id (USER e ADMIN)
    @GetMapping("/{id}")
    public Moto buscarMoto(@PathVariable Long id) {
        return motoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Moto não encontrada: " + id));
    }

    // Criar nova moto (ADMIN)
    @PostMapping
    //@PreAuthorize("hasRole('ADMIN')")
    public Moto criarMoto(@RequestBody Moto moto) {
        return motoRepository.save(moto);
    }

    // Atualizar moto existente (ADMIN)
    @PutMapping("/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public Moto atualizarMoto(@PathVariable Long id, @RequestBody Moto motoAtualizada) {
        Moto moto = motoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Moto não encontrada: " + id));

        moto.setMarca(motoAtualizada.getMarca());
        moto.setModelo(motoAtualizada.getModelo());
        moto.setAno(motoAtualizada.getAno());
        moto.setCor(motoAtualizada.getCor());
        moto.setCilindrada(motoAtualizada.getCilindrada());
        moto.setStatus(motoAtualizada.getStatus());
        moto.setPosicao(motoAtualizada.getPosicao());
        moto.setLatitude(motoAtualizada.getLatitude());
        moto.setLongitude(motoAtualizada.getLongitude());

        return motoRepository.save(moto);
    }

    // Deletar moto (ADMIN)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deletarMoto(@PathVariable Long id) {
        motoRepository.deleteById(id);
    }
}
