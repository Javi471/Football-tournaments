package com.football.tournaments.repository;

import com.football.tournaments.model.Partita;
import com.football.tournaments.model.StatoPartita;
import com.football.tournaments.model.Torneo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PartitaRepository extends JpaRepository<Partita, Long> {

    List<Partita> findByTorneoOrderByDataOraAsc(Torneo torneo);

    List<Partita> findByTorneoAndStato(Torneo torneo, StatoPartita stato);

    @Query("SELECT p FROM Partita p JOIN FETCH p.squadraHome JOIN FETCH p.squadraAway JOIN FETCH p.arbitro WHERE p.torneo = :torneo ORDER BY p.dataOra ASC")
    List<Partita> findByTorneoWithTeamsAndReferee(Torneo torneo);

    @Query("SELECT p FROM Partita p JOIN FETCH p.squadraHome JOIN FETCH p.squadraAway JOIN FETCH p.arbitro JOIN FETCH p.torneo WHERE p.id = :id")
    Optional<Partita> findByIdWithDetails(Long id);

    @Query("SELECT p FROM Partita p JOIN FETCH p.commenti c JOIN FETCH c.utente WHERE p.id = :id")
    Optional<Partita> findByIdWithCommenti(Long id);
}
