package com.cjhsoft.tarefa.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cjhsoft.tarefa.domain.model.Projeto;

@Repository
public interface ProjetoRepository extends JpaRepository<Projeto, Long>{

}
