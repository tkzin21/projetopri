package com.agendai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.agendai.model.Profissional;

public interface ProfissionalRepository extends JpaRepository<Profissional, Long> {    
}
