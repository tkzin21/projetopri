package com.agendai.controller;



import com.agendai.model.Cliente;
import com.agendai.repository.ClienteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ClienteController {
    @Autowired
    private  ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @GetMapping("/clientes")
    public String listarClientes(Model model) {
        List<Cliente> clientes = clienteRepository.findAll();
        System.out.println(clientes.size());
        model.addAttribute("clientes",clientes);
        model.addAttribute("novoCliente", new Cliente()); 
        return"clientes";
    }
    @GetMapping("/clientes/novo")
public String novoCliente(Model model) {
    model.addAttribute("cliente", new Cliente());
    return "cliente-form"; // HTML do formulário
}
    @PostMapping("/clientes")
    public String salvarCliente(@ModelAttribute Cliente cliente) {
        clienteRepository.save(cliente);
        return "redirect:/clientes"; // volta para a lista depois de salvar
    }
}
