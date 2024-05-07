package com.cjhsoft.tarefa.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cjhsoft.tarefa.domain.model.Projeto;
import com.cjhsoft.tarefa.domain.repository.ProjetoRepository;

@Service
public class ProjetoService {
	
	@Autowired
	ProjetoRepository projetoRepository;

	public Projeto cadastrar(Projeto projeto) {
		return projetoRepository.save(projeto);
	}
	
}
