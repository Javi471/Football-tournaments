package com.football.tournaments.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Arbitro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @NotBlank
    private String cognome;

    @NotBlank
    @Column(unique = true, nullable = false)
    private String codiceArbitrale;

    @OneToMany(mappedBy = "arbitro", fetch = FetchType.LAZY)
    private List<Partita> partite;

    public Arbitro(String nome, String cognome, String codiceArbitrale) {
        this.nome = nome;
        this.cognome = cognome;
        this.codiceArbitrale = codiceArbitrale;
    }
}
