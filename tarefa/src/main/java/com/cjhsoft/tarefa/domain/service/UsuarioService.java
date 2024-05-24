package com.cjhsoft.tarefa.domain.service;

import java.util.List;
import java.util.Optional;

import org.hibernate.transform.ToListResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cjhsoft.tarefa.domain.exception.BusinessException;
import com.cjhsoft.tarefa.domain.model.Projeto;
import com.cjhsoft.tarefa.domain.model.Tarefa;
import com.cjhsoft.tarefa.domain.model.Usuario;
import com.cjhsoft.tarefa.domain.repository.ProjetoRepository;
import com.cjhsoft.tarefa.domain.repository.TarefaRepository;
import com.cjhsoft.tarefa.domain.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private TarefaRepository tarefaRepository;
	
	@Autowired
	private ProjetoRepository projetoRepository;
	
	public Usuario cadastrar(Usuario usuario) {
		Boolean emailEmUso = usuarioRepository.findByEmail(usuario.getEmail()).isPresent();
		if(emailEmUso) {
			throw new BusinessException("Email já existente");
		}
		return usuarioRepository.save(usuario);
	}
	
	public Usuario usuarioPorEmail(String email) {
		Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
		if(!usuario.isPresent()) {
			throw new BusinessException("Usuário não cadastrado");
		}
		return usuario.get();
	}
	
	public List<Tarefa> tarefasDoUsuario(Long uId){
		Optional<Usuario> usuario = usuarioRepository.findById(uId);
		if(!usuario.isPresent()) {
			throw new BusinessException("Usuário não cadastrado");
		}
		return usuario.get().getTarefas();
	}
	
	public List<Projeto> projetosDoUsuario(Long uId){
		List<Tarefa> tarefas = tarefasDoUsuario(uId);
		List<Projeto> projetos = tarefas.stream()
			.filter(tarefa -> tarefa.getTitulo().equalsIgnoreCase("tarefa-default"))	
			.map(tarefa->
				projetoRepository.findById(tarefa.getProjeto().getId()).get()
		).toList();
		return projetos;
	}
}




















