package com.cjhsoft.tarefa.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cjhsoft.tarefa.domain.model.Tarefa;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long>{

}
