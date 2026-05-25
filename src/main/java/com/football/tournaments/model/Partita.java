package com.football.tournaments.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Partita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDateTime dataOra;

    private String luogo;

    private Integer goalsHome = 0;

    private Integer goalsAway = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatoPartita stato = StatoPartita.SCHEDULED;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "torneo_id", nullable = false)
    private Torneo torneo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "squadra_home_id", nullable = false)
    private Squadra squadraHome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "squadra_away_id", nullable = false)
    private Squadra squadraAway;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "arbitro_id", nullable = false)
    private Arbitro arbitro;

    @OneToMany(mappedBy = "partita", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Commento> commenti = new ArrayList<>();

    public Partita(LocalDateTime dataOra, String luogo, Torneo torneo, Squadra squadraHome, Squadra squadraAway, Arbitro arbitro) {
        this.dataOra = dataOra;
        this.luogo = luogo;
        this.torneo = torneo;
        this.squadraHome = squadraHome;
        this.squadraAway = squadraAway;
        this.arbitro = arbitro;
    }
}
