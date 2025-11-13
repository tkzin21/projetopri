package com.agendai.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agendai.repository.AgendamentoRepository;
import com.agendai.repository.ProfissionalRepository;

@Service
public class ProfissionalService {

    @Autowired
    private ProfissionalRepository profissionalRepository;

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    public void delete(Long id) {
        long count = agendamentoRepository.countByProfissionalId(id);

        if (count > 0) {
            throw new IllegalStateException("Não é possível excluir este profissional pois ele possui agendamentos vinculados.");
        }

        profissionalRepository.deleteById(id);
    }


}
