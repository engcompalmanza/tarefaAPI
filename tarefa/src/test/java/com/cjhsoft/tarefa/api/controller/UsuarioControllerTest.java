package com.cjhsoft.tarefa.api.controller;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.cjhsoft.tarefa.api.mapper.UsuarioMapper;
import com.cjhsoft.tarefa.api.model.UsuarioInput;
import com.cjhsoft.tarefa.api.model.UsuarioOutput;
import com.cjhsoft.tarefa.domain.model.Usuario;
import com.cjhsoft.tarefa.domain.repository.UsuarioRepository;
import com.cjhsoft.tarefa.domain.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

	@MockBean
	UsuarioMapper usuarioMapper;
	
	@MockBean
	UsuarioService usuarioService;
	
	@MockBean
	UsuarioRepository usuarioRepository;
	
	@Autowired
	UsuarioController usuarioController;
	
	@Autowired
	MockMvc mockMvc;
	
	ObjectMapper om;
	
	Usuario usuario;
	UsuarioInput usuarioInput;
	UsuarioInput usuarioInputComErroDeValidacao;
	UsuarioOutput usuarioOutput;
	
	List<UsuarioOutput> usuarioOutputs;
	
	@BeforeEach
	void setUp() {
		om = new ObjectMapper();
	}
	
	@Test
	@DisplayName("Cadastro nao sucedido pq nao tem erro de validacao - deve retornar codigo 400-bad-request")
	void cadastroNaoSucedidoCaso2() throws Exception{
//		when(usuarioMapper.toEntity(usuarioInputComErroDeValidacao)).thenReturn(usuario);
		this.usuarioInputComErroDeValidacao = new UsuarioInput(null, null, null, null);
		
		mockMvc.perform(post("/usuarios")
			.contentType(MediaType.APPLICATION_JSON)
			.content(om.writeValueAsString(usuarioInputComErroDeValidacao)))
			.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	@DisplayName("Cadastro nao sucedido pq nao tem corpo na requisicao - deve retornar codigo 400-bad-request")
	void cadastroNaoSucedidoCaso1() throws Exception{
		mockMvc.perform(post("/usuarios")
			.contentType(MediaType.APPLICATION_JSON)
			.content(om.writeValueAsString("")))
			.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	@DisplayName("Cadastro com sucesso deve retornar codigo 201-created")
	void cadastroComSucesso() throws Exception{
//		when(usuarioMapper.toEntity(usuarioInput)).thenReturn(usuario);
		this.usuarioInput = new UsuarioInput("jose", "jose@gmail.com", "12345", null);
		
		mockMvc.perform(post("/usuarios")
			.contentType(MediaType.APPLICATION_JSON)
			.content(om.writeValueAsString(usuarioInput)))
			.andExpect(MockMvcResultMatchers.status().isCreated());
	}
	
	@Test
	@DisplayName("Recupera com sucesso lista vazia de usuarios")
	void recuperaTodosComListaVazia() throws Exception{
		when(usuarioMapper.toCollectionOutput(anyList())).thenReturn(Collections.emptyList());
		
		mockMvc.perform(get("/usuarios")
			.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().json(om.writeValueAsString(Collections.emptyList())))
		.andExpect(jsonPath("$").isArray())
		.andExpect(jsonPath("$.length()").value(0));
	}
	
	@Test
	@DisplayName("Recupera lista de usuarios com sucesso")
	void recuperaTodosComSucesso() throws Exception{
		usuarioOutputs = Collections.singletonList(usuarioOutput);
		when(usuarioMapper.toCollectionOutput(anyList())).thenReturn(usuarioOutputs);
		
		mockMvc.perform(get("/usuarios")
			.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().json(om.writeValueAsString(usuarioOutputs)))
		.andExpect(jsonPath("$").isArray())
		.andExpect(jsonPath("$.length()").value(1));
	}
	
}













