 package com.agendai.controller;

import com.agendai.model.Agendamento;
import com.agendai.model.Profissional;
import com.agendai.service.AgendamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.agendai.repository.AgendamentoRepository;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/api/agendamentos")
public class AgendamentoApiController {

    private final AgendamentoRepository repository;

    public AgendamentoApiController(AgendamentoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Map<String, Object>> listar() {
        return repository.findAll().stream().map(ag -> {
            Map<String, Object> evento = new HashMap<>();
            evento.put("id", ag.getId());
            evento.put("title", ag.getCliente() != null ? ag.getCliente().getNome() : "Sem cliente");
            evento.put("start", ag.getDataHora()); // LocalDateTime funciona com FullCalendar 6+
            evento.put("extendedProps", Map.of(
                "profissional", ag.getProfissional() != null ? ag.getProfissional().getNome() : "",
                "duracao", ag.getDuracao(),
                "observacao", ag.getObservacao()
            ));
            return evento;
        }).collect(Collectors.toList());
    }
}


