package com.agendai.repository;

import com.agendai.model.Agendamento;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    @Override
    List<Agendamento> findAll(); // opcional, mas pode deixar

    Optional<Agendamento> findByDataHora(LocalDateTime dataHora);

    // Adicione este método
    List<Agendamento> findByProfissionalId(Long profissionalId);
}
