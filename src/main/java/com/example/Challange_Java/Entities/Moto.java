package com.example.Challange_Java.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data 
@Table(name = "moto") // Nome da tabela no banco de dados
@AllArgsConstructor
@NoArgsConstructor

public class Moto {
       @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String marca;

    @Column(nullable = false)
    private String modelo;

    @Column(nullable = false)
    private int ano;

    private String cor;

    private int cilindrada; // ex: 150

    private String status; // ex: "DISPONIVEL", "MANUTENCAO"

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Posicao posicao;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

}
