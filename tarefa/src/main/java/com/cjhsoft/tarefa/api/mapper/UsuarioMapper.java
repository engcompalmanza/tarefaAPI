package com.cjhsoft.tarefa.api.mapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cjhsoft.tarefa.api.model.TarefaOutput;
import com.cjhsoft.tarefa.api.model.UsuarioInput;
import com.cjhsoft.tarefa.api.model.UsuarioOutput;
import com.cjhsoft.tarefa.domain.model.Tarefa;
import com.cjhsoft.tarefa.domain.model.Usuario;
import com.cjhsoft.tarefa.domain.repository.TarefaRepository;
import com.cjhsoft.tarefa.domain.repository.UsuarioRepository;

@Component
public class UsuarioMapper {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private TarefaRepository tarefaRepository;
	
	public Usuario toEntity(UsuarioInput usuarioInput) {
		Usuario usuario = new Usuario();
		usuario.setNome(usuarioInput.getNome());
		usuario.setEmail(usuarioInput.getEmail());
		usuario.setSenha(usuarioInput.getSenha());
		usuario.setTarefas(usuarioInput.getTarefasIds().stream().map(tarefa_id ->
			tarefaRepository.findById(tarefa_id).get())
			.toList());
		return usuario;
	}
	
	public UsuarioOutput toModelOutput(Usuario usuario) {
		UsuarioOutput usuarioOutput = new UsuarioOutput();
		usuarioOutput.setNome(usuario.getNome());
		usuarioOutput.setEmail(usuario.getEmail());
		usuarioOutput.setNomestarefas(
			usuario.getTarefas().stream().map(tarefa ->
				tarefa.getTitulo())
				.toList());
		return usuarioOutput;
	}
	
	public List<UsuarioOutput> toCollectionOutput(List<Usuario> usuarios){
		return usuarios.stream().map(usuario -> 
			toModelOutput(usuario))
				.toList();
	}
	

}
