package com.cjhsoft.tarefa.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cjhsoft.tarefa.api.mapper.UsuarioMapper;
import com.cjhsoft.tarefa.api.model.UsuarioInput;
import com.cjhsoft.tarefa.api.model.UsuarioOutput;
import com.cjhsoft.tarefa.domain.model.Usuario;
import com.cjhsoft.tarefa.domain.repository.UsuarioRepository;
import com.cjhsoft.tarefa.domain.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	UsuarioMapper usuarioMapper;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@PostMapping
	public ResponseEntity<Void> cadastrar(@Valid @RequestBody UsuarioInput usuarioInput){
		Usuario usuario = usuarioMapper.toEntity(usuarioInput);
		usuarioService.cadastrar(usuario);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@GetMapping
	public ResponseEntity<List<UsuarioOutput>> recuperarTodos(){
		List<UsuarioOutput> usuariosOutputs = usuarioMapper.toCollectionOutput(usuarioRepository.findAll());
		return ResponseEntity.status(HttpStatus.OK).body(usuariosOutputs);
	}
	
}
