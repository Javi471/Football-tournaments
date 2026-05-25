package com.football.tournaments.service;

import com.football.tournaments.model.Commento;
import com.football.tournaments.model.Partita;
import com.football.tournaments.model.User;
import com.football.tournaments.repository.CommentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CommentoService {

    @Autowired
    private CommentoRepository commentoRepository;

    @Transactional(readOnly = true)
    public List<Commento> findByPartita(Partita partita) {
        return commentoRepository.findByPartita(partita);
    }

    @Transactional(readOnly = true)
    public Optional<Commento> findByIdAndUtente(Long id, User utente) {
        return commentoRepository.findByIdAndUtente(id, utente);
    }

    @Transactional(readOnly = true)
    public Optional<Commento> findById(Long id) {
        return commentoRepository.findById(id);
    }

    @Transactional
    public Commento save(Commento commento) {
        return commentoRepository.save(commento);
    }

    @Transactional
    public void deleteById(Long id) {
        commentoRepository.deleteById(id);
    }
}
