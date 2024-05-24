package com.cjhsoft.tarefa.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cjhsoft.tarefa.api.mapper.ProjetoMapper;
import com.cjhsoft.tarefa.api.mapper.TarefaMapper;
import com.cjhsoft.tarefa.api.mapper.UsuarioMapper;
import com.cjhsoft.tarefa.api.model.ProjetoOutput;
import com.cjhsoft.tarefa.api.model.TarefaOutput;
import com.cjhsoft.tarefa.api.model.UsuarioInput;
import com.cjhsoft.tarefa.api.model.UsuarioOutput;
import com.cjhsoft.tarefa.domain.model.Projeto;
import com.cjhsoft.tarefa.domain.model.Tarefa;
import com.cjhsoft.tarefa.domain.model.Usuario;
import com.cjhsoft.tarefa.domain.repository.UsuarioRepository;
import com.cjhsoft.tarefa.domain.service.UsuarioService;

import jakarta.validation.Valid;

//{
//    "nome":"jose carlos",
//    "email":"jose@gmail.com",
//    "senha":"12345",
//    "tarefasIds":[1, 2]
//}

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	UsuarioMapper usuarioMapper;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	TarefaMapper tarefaMapper;
	
	@Autowired
	ProjetoMapper projetoMapper;
	
	@PostMapping
	public ResponseEntity<UsuarioOutput> cadastrar(@Valid @RequestBody UsuarioInput usuarioInput){
		Usuario usuario = usuarioMapper.toEntity(usuarioInput);
		Usuario usuarioCadastrado = usuarioService.cadastrar(usuario);
		UsuarioOutput usuarioOutput = usuarioMapper.toModelOutput(usuarioCadastrado);
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioOutput);
	}
	
	@GetMapping("/{email}")
	public ResponseEntity<UsuarioOutput> usuarioPorEmail(@PathVariable String email){
		UsuarioOutput usuarioOutput = usuarioMapper.toModelOutput(usuarioService.usuarioPorEmail(email));
		return ResponseEntity.status(HttpStatus.OK).body(usuarioOutput);
	}
	
	@GetMapping
	public ResponseEntity<List<UsuarioOutput>> recuperarTodos(){
		List<UsuarioOutput> usuariosOutputs = usuarioMapper.toCollectionOutput(usuarioRepository.findAll());
		return ResponseEntity.status(HttpStatus.OK).body(usuariosOutputs);
	}
	
	@GetMapping("/tarefas/{uId}")
	public ResponseEntity<List<TarefaOutput>> tarefasDoUsuario(@PathVariable Long uId){
		List<Tarefa> tarefas = usuarioService.tarefasDoUsuario(uId);
		return ResponseEntity.status(HttpStatus.OK).body(tarefaMapper.toCollectionOutput(tarefas));
	}
	
	@GetMapping("/projetos/{uId}")
	public ResponseEntity<List<ProjetoOutput>> projetosDoUsuario(@PathVariable Long uId){
		List<Projeto> projetos = usuarioService.projetosDoUsuario(uId);
		return ResponseEntity.status(HttpStatus.OK).body(projetoMapper.toCollectionOutput(projetos));
	}	
	
}
