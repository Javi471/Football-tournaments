package com.football.tournaments.controller;

import com.football.tournaments.model.Torneo;
import com.football.tournaments.service.PartitaService;
import com.football.tournaments.service.SquadraService;
import com.football.tournaments.service.TorneoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tornei")
public class TorneoController {

    @Autowired private TorneoService torneoService;
    @Autowired private SquadraService squadraService;
    @Autowired private PartitaService partitaService;

    @GetMapping
    public String lista(Model model) {
        model.addAttribute("tornei", torneoService.findAll());
        return "torneo/lista";
    }

    @GetMapping("/{id}")
    public String dettaglio(@PathVariable Long id, Model model) {
        Torneo torneo = torneoService.findByIdWithSquadre(id)
            .orElseThrow(() -> new RuntimeException("Torneo non trovato"));
        model.addAttribute("torneo", torneo);
        model.addAttribute("partite", partitaService.findByTorneo(torneo));
        return "torneo/dettaglio";
    }

    @GetMapping("/{id}/classifica")
    public String classifica(@PathVariable Long id, Model model) {
        Torneo torneo = torneoService.findByIdWithSquadre(id)
            .orElseThrow(() -> new RuntimeException("Torneo non trovato"));
        model.addAttribute("torneo", torneo);
        return "torneo/classifica";
    }

    // --- Admin ---

    @GetMapping("/nuovo")
    @PreAuthorize("hasRole('ADMIN')")
    public String nuovoForm(Model model) {
        model.addAttribute("torneo", new Torneo());
        model.addAttribute("tutteLeSquadre", squadraService.findAll());
        return "torneo/form";
    }

    @PostMapping("/nuovo")
    @PreAuthorize("hasRole('ADMIN')")
    public String nuovoSalva(@Valid @ModelAttribute Torneo torneo,
                              BindingResult result,
                              @RequestParam(required = false) List<Long> squadraIds,
                              Model model) {
        if (result.hasErrors()) {
            model.addAttribute("tutteLeSquadre", squadraService.findAll());
            return "torneo/form";
        }
        Torneo salvato = torneoService.save(torneo);
        if (squadraIds != null) {
            torneoService.aggiornaSquadre(salvato.getId(), squadraIds);
        }
        return "redirect:/tornei/" + salvato.getId();
    }

    @GetMapping("/{id}/modifica")
    @PreAuthorize("hasRole('ADMIN')")
    public String modificaForm(@PathVariable Long id, Model model) {
        Torneo torneo = torneoService.findByIdWithSquadre(id)
            .orElseThrow(() -> new RuntimeException("Torneo non trovato"));
        model.addAttribute("torneo", torneo);
        model.addAttribute("tutteLeSquadre", squadraService.findAll());
        return "torneo/form";
    }

    @PostMapping("/{id}/modifica")
    @PreAuthorize("hasRole('ADMIN')")
    public String modificaSalva(@PathVariable Long id,
                                 @Valid @ModelAttribute Torneo torneo,
                                 BindingResult result,
                                 @RequestParam(required = false) List<Long> squadraIds,
                                 Model model) {
        if (result.hasErrors()) {
            model.addAttribute("tutteLeSquadre", squadraService.findAll());
            return "torneo/form";
        }
        torneo.setId(id);
        torneoService.save(torneo);
        if (squadraIds != null) {
            torneoService.aggiornaSquadre(id, squadraIds);
        }
        return "redirect:/tornei/" + id;
    }

    @PostMapping("/{id}/elimina")
    @PreAuthorize("hasRole('ADMIN')")
    public String elimina(@PathVariable Long id) {
        torneoService.deleteById(id);
        return "redirect:/tornei";
    }
}
