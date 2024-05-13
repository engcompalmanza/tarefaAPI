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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cjhsoft.tarefa.api.mapper.TarefaMapper;
import com.cjhsoft.tarefa.api.model.ProjetoOutput;
import com.cjhsoft.tarefa.api.model.TarefaInput;
import com.cjhsoft.tarefa.api.model.TarefaOutput;
import com.cjhsoft.tarefa.domain.model.Tarefa;
import com.cjhsoft.tarefa.domain.repository.TarefaRepository;
import com.cjhsoft.tarefa.domain.service.TarefaService;

import jakarta.validation.Valid;

//{
//    "titulo":"pintar carro",
//    "descricao":"pintar carro da cor azul",
//    "vencimento":"2024-05-11",
//    "prioridade":"false",
//    "status":"PENDENTE",
//    "projeto":"1"
//}

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

	@Autowired
	TarefaMapper tarefaMapper;
	
	@Autowired
	TarefaService tarefaService;
	
	@Autowired
	TarefaRepository tarefaRepository;
	
	@PostMapping
	public ResponseEntity<Void> cadastro(@Valid @RequestBody TarefaInput tarefaInput) {
		Tarefa tarefa = tarefaMapper.toEntity(tarefaInput);
		tarefaService.cadastrar(tarefa);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@GetMapping
	public ResponseEntity<List<TarefaOutput>> recuperaTodos(){
		List<TarefaOutput> tarefasOutput = tarefaMapper.toCollectionOutput(tarefaRepository.findAll());
		return ResponseEntity.status(HttpStatus.OK).body(tarefasOutput);
	}

}
