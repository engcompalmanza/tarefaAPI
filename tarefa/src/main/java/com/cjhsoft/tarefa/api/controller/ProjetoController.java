package com.cjhsoft.tarefa.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cjhsoft.tarefa.api.mapper.ProjetoMapper;
import com.cjhsoft.tarefa.api.model.ProjetoInput;
import com.cjhsoft.tarefa.api.model.ProjetoOutput;
import com.cjhsoft.tarefa.domain.model.Projeto;
import com.cjhsoft.tarefa.domain.repository.ProjetoRepository;
import com.cjhsoft.tarefa.domain.service.ProjetoService;

import jakarta.validation.Valid;

//{
//    "nome_do_Projeto":"projeto 1",
//    "descricao":"projeto 1" 
//}

@RestController
@RequestMapping("/projetos")
public class ProjetoController {
	
	@Autowired
	ProjetoMapper projetoMapper;
	
	@Autowired
	ProjetoService projetoService;
	
	@Autowired
	ProjetoRepository projetoRepository;
	
	@GetMapping
	public List<ProjetoOutput> recuperarTodos(){
		return projetoMapper.toCollectionOutput(projetoRepository.findAll());
	}

	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public ProjetoOutput cadastro(@Valid @RequestBody ProjetoInput projetoInput) {
		Projeto entidadeProjeto = projetoMapper.toEntity(projetoInput);
		Projeto projetoCadastrado = projetoService.cadastrar(entidadeProjeto);
		return projetoMapper.toModelOutput(projetoCadastrado);
	}
	
	@PostMapping("/{uid}")
	@ResponseStatus(value = HttpStatus.CREATED)
	public ProjetoOutput cadastroProjeto(@Valid @RequestBody ProjetoInput projetoInput, @PathVariable Long uid) {
		Projeto entidadeProjeto = projetoMapper.toEntity(projetoInput);
		Projeto projetoCadastrado = projetoService.cadastrarProjeto(entidadeProjeto, uid);
		return projetoMapper.toModelOutput(projetoCadastrado);
	}
	
	
	@GetMapping("/{nomeProjeto}")
	ResponseEntity<ProjetoOutput> projetoPeloNome(@PathVariable String nomeProjeto){
		ProjetoOutput projetoOutput = projetoMapper.toModelOutput(projetoService.projetoPeloNome(nomeProjeto));
		return ResponseEntity.status(HttpStatus.OK).body(projetoOutput);
	}
	
}










