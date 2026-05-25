package com.football.tournaments.controller;

import com.football.tournaments.model.Squadra;
import com.football.tournaments.service.SquadraService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/squadre")
public class SquadraController {

    @Autowired
    private SquadraService squadraService;

    @GetMapping
    public String lista(Model model) {
        model.addAttribute("squadre", squadraService.findAll());
        return "squadra/lista";
    }

    @GetMapping("/{id}")
    public String dettaglio(@PathVariable Long id, Model model) {
        Squadra squadra = squadraService.findByIdWithGiocatori(id)
            .orElseThrow(() -> new RuntimeException("Squadra non trovata"));
        model.addAttribute("squadra", squadra);
        return "squadra/dettaglio";
    }

    @GetMapping("/nuova")
    @PreAuthorize("hasRole('ADMIN')")
    public String nuovaForm(Model model) {
        model.addAttribute("squadra", new Squadra());
        return "squadra/form";
    }

    @PostMapping("/nuova")
    @PreAuthorize("hasRole('ADMIN')")
    public String nuovaSalva(@Valid @ModelAttribute Squadra squadra, BindingResult result) {
        if (result.hasErrors()) return "squadra/form";
        Squadra salvata = squadraService.save(squadra);
        return "redirect:/squadre/" + salvata.getId();
    }

    @GetMapping("/{id}/modifica")
    @PreAuthorize("hasRole('ADMIN')")
    public String modificaForm(@PathVariable Long id, Model model) {
        model.addAttribute("squadra", squadraService.findById(id)
            .orElseThrow(() -> new RuntimeException("Squadra non trovata")));
        return "squadra/form";
    }

    @PostMapping("/{id}/modifica")
    @PreAuthorize("hasRole('ADMIN')")
    public String modificaSalva(@PathVariable Long id,
                                 @Valid @ModelAttribute Squadra squadra,
                                 BindingResult result) {
        if (result.hasErrors()) return "squadra/form";
        squadra.setId(id);
        squadraService.save(squadra);
        return "redirect:/squadre/" + id;
    }

    @PostMapping("/{id}/elimina")
    @PreAuthorize("hasRole('ADMIN')")
    public String elimina(@PathVariable Long id) {
        squadraService.deleteById(id);
        return "redirect:/squadre";
    }
}
