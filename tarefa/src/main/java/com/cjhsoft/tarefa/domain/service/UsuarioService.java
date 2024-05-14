package com.cjhsoft.tarefa.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cjhsoft.tarefa.domain.exception.BusinessException;
import com.cjhsoft.tarefa.domain.model.Usuario;
import com.cjhsoft.tarefa.domain.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public void cadastrar(Usuario usuario) {
		Boolean emailEmUso = usuarioRepository.findByEmail(usuario.getEmail()).isPresent();
		if(emailEmUso) {
			throw new BusinessException("Email já existente");
		}
		usuarioRepository.save(usuario);
	}
	
}
