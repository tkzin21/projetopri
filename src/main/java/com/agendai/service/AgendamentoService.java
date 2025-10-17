package com.agendai.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agendai.model.Agendamento;
import com.agendai.model.Profissional;
import com.agendai.repository.AgendamentoRepository;
import com.agendai.repository.ProfissionalRepository;

@Service
public class AgendamentoService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private ProfissionalRepository profissionalRepository;

    public Agendamento salvarAgendamento(Agendamento agendamento) {
        LocalDateTime inicioNovo = agendamento.getDataHora();
        LocalDateTime fimNovo = inicioNovo.plusMinutes(agendamento.getDuracao());

        // Confirma que o profissional existe
        Profissional prof = profissionalRepository.findById(agendamento.getProfissional().getId())
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado"));

        // Busca agendamentos do profissional
        List<Agendamento> agsExistentes = agendamentoRepository.findByProfissionalId(prof.getId());

        // Valida conflito
        for (Agendamento a : agsExistentes) {
            LocalDateTime inicioExistente = a.getDataHora();
            LocalDateTime fimExistente = inicioExistente.plusMinutes(a.getDuracao());

            if (inicioNovo.isBefore(fimExistente) && fimNovo.isAfter(inicioExistente)) {
                throw new RuntimeException("O profissional já tem um agendamento nesse horário!");
            }
        }

        return agendamentoRepository.save(agendamento);
    }

    // Retorna eventos pro FullCalendar
    public List<Map<String, Object>> listarAgendamentos() {
        return agendamentoRepository.findAll()
                .stream()
                .<Map<String, Object>>map(a -> {
                    Map<String, Object> evento = new HashMap<>();
                    evento.put("id", a.getId());
                    evento.put("title", a.getCliente().getNome() + " (" + a.getProfissional().getNome() + ")");
                    evento.put("start", a.getDataHora().toString());
                    evento.put("end", a.getDataHora().plusMinutes(a.getDuracao()).toString());
                    evento.put("servico", a.getObservacao());
                    return evento;
                })
                .collect(Collectors.toList());
    }

    // Lista profissionais para dropdown no frontend
    public List<Profissional> listarProfissionais() {
        return profissionalRepository.findAll();
    }
}
