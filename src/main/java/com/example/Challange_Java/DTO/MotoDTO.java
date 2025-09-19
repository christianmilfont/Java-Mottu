package com.example.Challange_Java.DTO;

import com.example.Challange_Java.Entities.Posicao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class MotoDTO {
    private Long id;

    private String marca;

    private String modelo;

    private int ano;

    private String cor;

    private int cilindrada;

    private String status;

    //ENUM
    private Posicao posicao;

    private Double latitude;

    private Double longitude;
}
