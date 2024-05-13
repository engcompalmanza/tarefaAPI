package com.cjhsoft.tarefa.api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDate;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.cjhsoft.tarefa.api.mapper.TarefaMapper;
import com.cjhsoft.tarefa.api.model.TarefaInput;
import com.cjhsoft.tarefa.api.model.TarefaOutput;
import com.cjhsoft.tarefa.domain.model.Projeto;
import com.cjhsoft.tarefa.domain.model.Status;
import com.cjhsoft.tarefa.domain.model.Tarefa;
import com.cjhsoft.tarefa.domain.repository.TarefaRepository;
import com.cjhsoft.tarefa.domain.service.TarefaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@WebMvcTest(TarefaController.class)
public class TarefaControllerTest {
	
	@Autowired
	TarefaController tarefaController;
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	TarefaMapper tarefaMapper;
	
	@MockBean
	TarefaService tarefaService;
	
	@MockBean
	TarefaRepository tarefaRepository;
	
	ObjectMapper objectMapper;
	
	TarefaInput tarefaInput;
	TarefaInput tarefaInputComErroDeValidacao;
	Tarefa tarefa;
	TarefaOutput tarefaOutput;
	Projeto projeto;
	
	@BeforeEach
	void setUp() {
		objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		
		tarefaInput = new TarefaInput("tarefa", "tarefa-teste", LocalDate.of(2024, 5, 14), true, Status.PENDENTE, 1L);
		tarefaInputComErroDeValidacao = new TarefaInput("tarefa", "tarefa-teste", null, true, Status.PENDENTE, 1L);
		
		projeto = new Projeto(1L, "projeto", "projeto-teste");
		tarefa = new Tarefa(null, "tarefa", "tarefa-teste", LocalDate.of(2024, 5, 14), true, Status.PENDENTE, projeto);
		tarefaOutput = new TarefaOutput("tarefa", "tarefa-teste", null, true, Status.PENDENTE, projeto.getNome());
	}

	@Test
	@DisplayName("Deve resultar em codigo 400-bad-request pq tem erro de validação")
	void cadastroComErroDeValidacao() throws Exception{
		when(tarefaMapper.toEntity(tarefaInput)).thenReturn(tarefa);
		
		mockMvc.perform(post("/tarefas")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(tarefaInputComErroDeValidacao)))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	@DisplayName("Deve resultar em codigo 400-bad-request pq não tem corpo na requisição")
	void cadastroSemCorpo() throws Exception{
		when(tarefaMapper.toEntity(tarefaInput)).thenReturn(tarefa);
		
		mockMvc.perform(post("/tarefas")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString("")))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	@DisplayName("Deve cadastrar uma tarefa com sucesso")
	void cadastroComSucesso() throws Exception{
		when(tarefaMapper.toEntity(tarefaInput)).thenReturn(tarefa);
		
		mockMvc.perform(post("/tarefas")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(tarefaInput)))
		.andExpect(MockMvcResultMatchers.status().isCreated());
	}
	
	@Test
	@DisplayName("Recupera Lista de tarefa com sucesso")
	void recuperaTodosComSucesso() throws Exception{
		when(tarefaMapper.toCollectionOutput(anyList())).thenReturn(Collections.singletonList(tarefaOutput));
		
		mockMvc.perform(get("/tarefas")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(Collections.singletonList(tarefaOutput))));
	}
	
}








