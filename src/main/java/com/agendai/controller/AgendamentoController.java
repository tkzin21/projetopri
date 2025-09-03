package com.agendai.controller;

import com.agendai.model.Agendamento;
import com.agendai.repository.AgendamentoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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

    @GetMapping("/agendamentos")
    public String listarAgendamentos(Model model) {
        List<Agendamento> agendamentos = repository.findAll();
        model.addAttribute("agendamentos", agendamentos);
        return "agendamentos.html";
    }
    // Salvar agendamento
    @PostMapping("/salvar")
    public String salvarAgendamento(@ModelAttribute Agendamento agendamento) {
        repository.save(agendamento);
        return "redirect:/";
    }

    // 🔹 NOVO: Lista datas ocupadas
    @GetMapping("/agendamentos/datas-ocupadas")
    @ResponseBody
    public List<LocalDate> getDatasOcupadas() {
        return repository.findAll()
                .stream()
                .map(Agendamento::getData)
                .distinct()
                .toList();
    }

    // 🔹 NOVO: Página do calendário
    @GetMapping("/calendario")
    public String calendario() {
        return "calendario.html";
    }
}
