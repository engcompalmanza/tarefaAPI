package com.cjhsoft.tarefa.domain.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cjhsoft.tarefa.domain.exception.BusinessException;
import com.cjhsoft.tarefa.domain.model.Tarefa;
import com.cjhsoft.tarefa.domain.repository.TarefaRepository;

@Service
public class TarefaService {
	
	@Autowired
	TarefaRepository tarefaRepository;
	
	public void cadastrar(Tarefa tarefa) {
		if(tarefa.getVencimento().isBefore(LocalDate.now())) {
			throw new BusinessException("Data de Vencimento invalida.");
		}
		tarefaRepository.save(tarefa);
	}
}
