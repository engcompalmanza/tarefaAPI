package com.cjhsoft.tarefa.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cjhsoft.tarefa.domain.model.Usuario;
import com.cjhsoft.tarefa.domain.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public void cadastrar(Usuario usuario) {
		usuarioRepository.save(usuario);
	}
	
}
