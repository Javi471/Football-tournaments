package com.football.tournaments.service;

import com.football.tournaments.model.Giocatore;
import com.football.tournaments.model.Squadra;
import com.football.tournaments.repository.GiocatoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GiocatoreService {

    @Autowired
    private GiocatoreRepository giocatoreRepository;

    @Transactional(readOnly = true)
    public List<Giocatore> findAll() {
        return giocatoreRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Giocatore> findById(Long id) {
        return giocatoreRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Giocatore> findBySquadra(Squadra squadra) {
        return giocatoreRepository.findBySquadra(squadra);
    }

    @Transactional(readOnly = true)
    public List<Giocatore> cerca(String query) {
        return giocatoreRepository.findByNomeContainingIgnoreCaseOrCognomeContainingIgnoreCase(query, query);
    }

    @Transactional
    public Giocatore save(Giocatore giocatore) {
        return giocatoreRepository.save(giocatore);
    }

    @Transactional
    public void deleteById(Long id) {
        giocatoreRepository.deleteById(id);
    }
}
