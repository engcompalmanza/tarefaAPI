package com.cjhsoft.tarefa.domain.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cjhsoft.tarefa.domain.exception.BusinessException;
import com.cjhsoft.tarefa.domain.model.Tarefa;
import com.cjhsoft.tarefa.domain.model.Usuario;
import com.cjhsoft.tarefa.domain.repository.TarefaRepository;
import com.cjhsoft.tarefa.domain.repository.UsuarioRepository;

@Service
public class TarefaService {
	
	@Autowired
	TarefaRepository tarefaRepository;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	public void cadastrar(Tarefa tarefa) {
		if(tarefa.getVencimento().isBefore(LocalDate.now())) {
			throw new BusinessException("Data de Vencimento invalida.");
		}
		tarefaRepository.save(tarefa);
	}
	
	public Tarefa cadastrarTarefa(Tarefa tarefa, Long uid) {
		if(tarefa.getVencimento().isBefore(LocalDate.now())) {
			throw new BusinessException("Data de Vencimento invalida.");
		}
		
		Tarefa tarefaCadastrada = tarefaRepository.save(tarefa);
		
		Usuario usuario = usuarioRepository.findById(uid).get();
		usuario.getTarefas().add(tarefaCadastrada);
		usuarioRepository.save(usuario);
		return tarefaCadastrada;
	}
}
