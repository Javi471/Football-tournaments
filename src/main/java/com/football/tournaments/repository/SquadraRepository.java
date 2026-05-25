package com.football.tournaments.repository;

import com.football.tournaments.model.Squadra;
import com.football.tournaments.model.Torneo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SquadraRepository extends JpaRepository<Squadra, Long> {

    List<Squadra> findByCitta(String citta);

    @Query("SELECT s FROM Squadra s LEFT JOIN FETCH s.giocatori WHERE s.id = :id")
    Optional<Squadra> findByIdWithGiocatori(Long id);

    @Query("SELECT s FROM Squadra s WHERE :torneo MEMBER OF s.tornei")
    List<Squadra> findByTorneo(Torneo torneo);
}
