package com.agendai.controller;

import com.agendai.model.Agendamento;
import com.agendai.repository.AgendamentoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AgendamentoController {
    private final AgendamentoRepository repository;

    public AgendamentoController(AgendamentoRepository repository) {
        this.repository = repository;
    }

    // Página inicial com lista
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("agendamentos", repository.findAll());
        return "index.html";
    }

    // Formulário de novo agendamento
    @GetMapping("/novo")
    public String novoAgendamentoForm(Model model) {
        model.addAttribute("agendamento", new Agendamento());
        return "novo-agendamento.html";
    }

    // Salvar agendamento
    @PostMapping("/salvar")
    public String salvarAgendamento(@ModelAttribute Agendamento agendamento) {
        repository.save(agendamento);
        return "redirect:/";
    }
}
