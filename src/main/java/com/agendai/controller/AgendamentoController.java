package com.agendai.controller;

import com.agendai.model.Agendamento;
import com.agendai.repository.AgendamentoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class AgendamentoController {
    private final AgendamentoRepository repository;

    public AgendamentoController(AgendamentoRepository repository) {
        this.repository = repository;
    }

    // Formulário de novo agendamento
    @GetMapping("/novo")
    public String novoAgendamento(Model model) {
        model.addAttribute("agendamento", new Agendamento());
        return "novo-agendamento";
    }

    // Salvar agendamento
    @PostMapping("/salvar")
    public String salvarAgendamento(@ModelAttribute Agendamento agendamento) {
        System.out.println(">>> Data recebida: " + agendamento.getDataHora());
        repository.save(agendamento);
        return "redirect:/";
    }

    // Listar agendamentos
    @GetMapping("/")
    public String listarAgendamentos(Model model) {
        model.addAttribute("agendamentos", repository.findAll());
        return "agendamentos";
    }

    @GetMapping("/api/agendamentos")
    @ResponseBody
    public List<Agendamento> listarAgendamentosApi() {
        return repository.findAll();
    }
}