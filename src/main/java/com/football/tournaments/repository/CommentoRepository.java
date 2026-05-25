package com.football.tournaments.repository;

import com.football.tournaments.model.Commento;
import com.football.tournaments.model.Partita;
import com.football.tournaments.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentoRepository extends JpaRepository<Commento, Long> {
    List<Commento> findByPartita(Partita partita);
    List<Commento> findByUtenteOrderByDataCreazioneDesc(User utente);
    Optional<Commento> findByIdAndUtente(Long id, User utente);
}
