package com.agendai.repository;

import com.agendai.model.Agendamento;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    Optional<Agendamento> findByDataHora(LocalDateTime dataHora);

    List<Agendamento> findByProfissionalId(Long profissionalId);
    boolean existsByProfissionalId(Long profissionalId);

    long countByProfissionalId(Long profissionalId);

}
