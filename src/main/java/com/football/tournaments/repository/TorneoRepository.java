package com.football.tournaments.repository;

import com.football.tournaments.model.Torneo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TorneoRepository extends JpaRepository<Torneo, Long> {

    List<Torneo> findByAnnoOrderByNomeAsc(Integer anno);

    @Query("SELECT t FROM Torneo t LEFT JOIN FETCH t.squadre WHERE t.id = :id")
    Optional<Torneo> findByIdWithSquadre(Long id);

    @Query("SELECT t FROM Torneo t LEFT JOIN FETCH t.partite WHERE t.id = :id")
    Optional<Torneo> findByIdWithPartite(Long id);
}
