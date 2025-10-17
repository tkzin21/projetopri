package com.agendai.controller;

import com.agendai.model.Agendamento;
import com.agendai.model.Cliente;
import com.agendai.model.Profissional;
import com.agendai.repository.AgendamentoRepository;
import com.agendai.repository.ClienteRepository;
import com.agendai.repository.ProfissionalRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class AgendamentoController {

    private final AgendamentoRepository repository;
    private final ClienteRepository clienteRepository;
    private final ProfissionalRepository profissionalRepository;

    public AgendamentoController(AgendamentoRepository repository,
                                 ClienteRepository clienteRepository,
                                 ProfissionalRepository profissionalRepository) {
        this.repository = repository;
        this.clienteRepository = clienteRepository;
        this.profissionalRepository = profissionalRepository;
    }

    // Mapeamento principal do formulário
    @GetMapping("/")
    public String mostrarFormulario(Model model) {
        model.addAttribute("agendamentos", repository.findAll());
        model.addAttribute("novoAgendamento", new Agendamento());
        model.addAttribute("todosClientes", clienteRepository.findAll());
        model.addAttribute("todosProfissionais", profissionalRepository.findAll());
        return "agendamentos";
    }

    // Salvar agendamento via formulário com Cliente e Profissional
    @PostMapping("/agendar")
    public String salvarAgendamento(@ModelAttribute Agendamento agendamento,
                                    @RequestParam("cliente") Long clienteId,
                                    @RequestParam("profissional") Long profissionalId,
                                    Model model) {

        Cliente cliente = clienteRepository.findById(clienteId).orElse(null);
        Profissional profissional = profissionalRepository.findById(profissionalId).orElse(null);

        if (cliente == null || profissional == null) {
            model.addAttribute("erro", "Cliente ou profissional não encontrado!");
            model.addAttribute("todosClientes", clienteRepository.findAll());
            model.addAttribute("todosProfissionais", profissionalRepository.findAll());
            model.addAttribute("novoAgendamento", new Agendamento());
            model.addAttribute("agendamentos", repository.findAll());
            return "agendamentos";
        }

        agendamento.setCliente(cliente);
        agendamento.setProfissional(profissional);
        agendamento.setContato(cliente.getTelefone());

        Optional<Agendamento> existente = repository.findByDataHora(agendamento.getDataHora());
        if (existente.isPresent()) {
            model.addAttribute("erro", "Esse horário já está ocupado! Escolha outro.");
        } else {
            repository.save(agendamento);
            model.addAttribute("sucesso", "Agendamento salvo com sucesso!");
        }

        model.addAttribute("novoAgendamento", new Agendamento());
        model.addAttribute("todosClientes", clienteRepository.findAll());
        model.addAttribute("todosProfissionais", profissionalRepository.findAll());
        model.addAttribute("agendamentos", repository.findAll());

        return "agendamentos";
    }


    // Página do jogo
    @GetMapping("/jogo")
    public String jogo() {
        return "jogo";
    }
}
    // Página
