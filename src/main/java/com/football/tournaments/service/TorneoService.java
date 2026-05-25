package com.football.tournaments.service;

import com.football.tournaments.model.Squadra;
import com.football.tournaments.model.Torneo;
import com.football.tournaments.repository.SquadraRepository;
import com.football.tournaments.repository.TorneoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TorneoService {

    @Autowired
    private TorneoRepository torneoRepository;

    @Autowired
    private SquadraRepository squadraRepository;

    @Transactional(readOnly = true)
    public List<Torneo> findAll() {
        return torneoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Torneo> findById(Long id) {
        return torneoRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Torneo> findByIdWithSquadre(Long id) {
        return torneoRepository.findByIdWithSquadre(id);
    }

    @Transactional(readOnly = true)
    public Optional<Torneo> findByIdWithPartite(Long id) {
        return torneoRepository.findByIdWithPartite(id);
    }

    @Transactional
    public Torneo save(Torneo torneo) {
        return torneoRepository.save(torneo);
    }

    @Transactional
    public Torneo aggiornaSquadre(Long torneoId, List<Long> squadraIds) {
        Torneo torneo = torneoRepository.findByIdWithSquadre(torneoId)
            .orElseThrow(() -> new RuntimeException("Torneo non trovato"));
        List<Squadra> squadre = squadraRepository.findAllById(squadraIds);
        torneo.setSquadre(squadre);
        return torneoRepository.save(torneo);
    }

    @Transactional
    public void deleteById(Long id) {
        torneoRepository.deleteById(id);
    }
}
