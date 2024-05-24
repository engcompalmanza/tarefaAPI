package com.cjhsoft.tarefa.domain.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cjhsoft.tarefa.domain.exception.BusinessException;
import com.cjhsoft.tarefa.domain.model.Projeto;
import com.cjhsoft.tarefa.domain.model.Status;
import com.cjhsoft.tarefa.domain.model.Tarefa;
import com.cjhsoft.tarefa.domain.model.Usuario;
import com.cjhsoft.tarefa.domain.repository.ProjetoRepository;
import com.cjhsoft.tarefa.domain.repository.TarefaRepository;
import com.cjhsoft.tarefa.domain.repository.UsuarioRepository;

@Service
public class ProjetoService {
	
	@Autowired
	ProjetoRepository projetoRepository;
	
	@Autowired
	TarefaRepository tarefaRepository;
	
	@Autowired
	UsuarioRepository usuarioRepository;

	public Projeto cadastrar(Projeto projeto) {
		return projetoRepository.save(projeto);
	}
	
	public Projeto cadastrarProjeto(Projeto projeto, Long uid) {
		Projeto projetoCadastrado = projetoRepository.save(projeto);
		
		Tarefa tarefaDefault = new Tarefa();
		tarefaDefault.setTitulo("tarefa-default");
		tarefaDefault.setVencimento(LocalDate.of(2100, 1, 1));
		tarefaDefault.setStatus(Status.PENDENTE);
		tarefaDefault.setPrioridade(false);
		tarefaDefault.setProjeto(projetoCadastrado);
		
		Tarefa tarefaCadastrada = tarefaRepository.save(tarefaDefault);
		
		Usuario usuario = usuarioRepository.findById(uid).get();
		usuario.getTarefas().add(tarefaCadastrada);
		usuarioRepository.save(usuario);
		
		return projetoCadastrado;
	}
	
	public Projeto projetoPeloNome(String nomeProjeto) {
		Optional<Projeto> projeto = projetoRepository.findByNome(nomeProjeto);
		if(!projeto.isPresent()) {
			throw new BusinessException("Projeto não cadastrado");
		}
		return projeto.get();
	}
	
}
