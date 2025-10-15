package com.agendai.controller;

import com.agendai.model.Agendamento;
import com.agendai.model.Cliente;
import com.agendai.repository.AgendamentoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.agendai.repository.ClienteRepository; // Import ClienteRepository
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
    private final ClienteRepository clienteRepository; // Add ClienteRepository

    public AgendamentoController(AgendamentoRepository repository, ClienteRepository clienteRepository) {
        this.repository = repository;
        this.clienteRepository = clienteRepository; // Initialize ClienteRepository
    }

    
    @GetMapping({"/"})
    public String mostrarFormulario(Model model) {
        model.addAttribute("agendamentos", repository.findAll());
        model.addAttribute("novoAgendamento", new Agendamento());
        model.addAttribute("todosClientes", clienteRepository.findAll());
        return "index"; // nome do template
    }
    // Salvar agendamento
    @PostMapping("/salvar")
    public String salvarAgendamento(@ModelAttribute Agendamento agendamento) {
        System.out.println(">>> Data recebida: " + agendamento.getDataHora());
        repository.save(agendamento);
        return "redirect:/";
    }

    // Listar agendamentos
    // @GetMapping("/")
    // public String listarAgendamentos(Model model) {
    // model.addAttribute("agendamentos", repository.findAll());
    // return "agendamentos";
    // }

    @GetMapping("/api/agendamentos")
    @ResponseBody
    public List<Map<String, Object>> listarEventos() {
        return repository.findAll().stream().map(ag -> {
            Map<String, Object> evento = new HashMap<>();
            evento.put("id", ag.getId());
            evento.put("title", ag.getNomeCliente()); // 👈 aqui vai o nome
            evento.put("start", ag.getDataHora().toString());
            evento.put("servico", ag.getObservacao()); // ou o campo que você quiser
            return evento;
        }).toList();
    }

    @GetMapping("/jogo")
    public String jogo() {
        return "jogo";
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

    @GetMapping("/agendamentos")
    public String listarAgendamentos(Model model) {
        model.addAttribute("agendamentos", repository.findAll());
        model.addAttribute("novoAgendamento", new Agendamento());
        return "agendamentos";
    }



    @PostMapping("/agendar")
    public String salvarAgendamento(@ModelAttribute Agendamento agendamento,
            @RequestParam("cliente") Long clienteId,
            Model model) {
    
        // Busca o cliente pelo ID
        Cliente cliente = clienteRepository.findById(clienteId).orElse(null);
        if (cliente == null) {
            model.addAttribute("erro", "Cliente não encontrado!");
            model.addAttribute("todosClientes", clienteRepository.findAll());
            return "agendamentos";
        }
    
        // Associa o cliente e o contato ao agendamento
        agendamento.setCliente(cliente);
        agendamento.setContato(cliente.getTelefone()); // <- preenche o contato automaticamente
    
        // Verifica se já existe um agendamento no mesmo horário
        Optional<Agendamento> existente = repository.findByDataHora(agendamento.getDataHora());
        if (existente.isPresent()) {
            model.addAttribute("erro", "Esse horário já está ocupado! Escolha outro.");
        } else {
            repository.save(agendamento);
            model.addAttribute("sucesso", "Agendamento salvo com sucesso!");
        }
    
        // Atualiza os dados da tela
        model.addAttribute("novoAgendamento", new Agendamento());
        model.addAttribute("todosClientes", clienteRepository.findAll());
        return "agendamentos";
    }

}