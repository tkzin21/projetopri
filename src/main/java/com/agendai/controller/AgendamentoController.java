package com.agendai.controller;

import com.agendai.model.Agendamento;
import com.agendai.model.Cliente;
import com.agendai.model.Profissional;
import com.agendai.repository.AgendamentoRepository;
import com.agendai.repository.ClienteRepository;
import com.agendai.repository.ProfissionalRepository;
import com.agendai.service.AgendamentoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @Autowired
    private AgendamentoService agendamentoService;

    @PostMapping("/agendamento/limpar")
    public String limparBanco(@RequestParam String senha, RedirectAttributes ra) {

        if (!senha.equals("admin123")) {
            ra.addFlashAttribute("erro", "Senha incorreta!");
            return "redirect:/";
        }

        agendamentoService.deleteAll();
        ra.addFlashAttribute("sucesso", "Todos os agendamentos foram apagados com sucesso!");

        return "redirect:/";
    }

    @PostMapping("agendamento/apagar/{id}")
    public String apagar(@PathVariable Long id) {
        // lógica para deletar
        agendamentoService.deleteById(id);

        return "redirect:/";

    }

    @GetMapping("/agendamentos")
    public String listarAgendamentos(Model model) {
        model.addAttribute("agendamentos", agendamentoService.findAll());
        return "agendamentos"; // nome do arquivo Thymeleaf: agendamentos.html
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
