package com.agendai.controller;

import com.agendai.model.Agendamento;
import com.agendai.repository.AgendamentoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @GetMapping("/calendario")
    public String calendario(Model model) {
        List<Agendamento> agendamentos = repository.findAll();

        // Mapear por data
        Map<LocalDate, List<Agendamento>> agendamentosPorDia = agendamentos.stream()
                .collect(Collectors.groupingBy(a -> a.getDataHora().toLocalDate()));

        model.addAttribute("agendamentosPorDia", agendamentosPorDia);
        return "calendario";
    }
    
}