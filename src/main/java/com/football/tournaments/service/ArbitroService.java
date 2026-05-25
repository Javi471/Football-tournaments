package com.football.tournaments.service;

import com.football.tournaments.model.Arbitro;
import com.football.tournaments.repository.ArbitroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ArbitroService {

    @Autowired
    private ArbitroRepository arbitroRepository;

    @Transactional(readOnly = true)
    public List<Arbitro> findAll() {
        return arbitroRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Arbitro> findById(Long id) {
        return arbitroRepository.findById(id);
    }

    @Transactional
    public Arbitro save(Arbitro arbitro) {
        return arbitroRepository.save(arbitro);
    }

    @Transactional
    public void deleteById(Long id) {
        arbitroRepository.deleteById(id);
    }
}
