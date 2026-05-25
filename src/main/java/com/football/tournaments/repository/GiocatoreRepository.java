package com.football.tournaments.repository;

import com.football.tournaments.model.Giocatore;
import com.football.tournaments.model.Squadra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GiocatoreRepository extends JpaRepository<Giocatore, Long> {
    List<Giocatore> findBySquadra(Squadra squadra);
    List<Giocatore> findByRuolo(String ruolo);
    List<Giocatore> findByNomeContainingIgnoreCaseOrCognomeContainingIgnoreCase(String nome, String cognome);
}
