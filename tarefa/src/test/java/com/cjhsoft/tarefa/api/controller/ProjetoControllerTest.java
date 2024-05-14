package com.cjhsoft.tarefa.api.controller;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.cjhsoft.tarefa.api.mapper.ProjetoMapper;
import com.cjhsoft.tarefa.api.model.ProjetoInput;
import com.cjhsoft.tarefa.api.model.ProjetoOutput;
import com.cjhsoft.tarefa.domain.model.Projeto;
import com.cjhsoft.tarefa.domain.repository.ProjetoRepository;
import com.cjhsoft.tarefa.domain.service.ProjetoService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ProjetoController.class)
public class ProjetoControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ProjetoController projetoController;
	
	@MockBean
	ProjetoMapper projetoMapper;
	
	@MockBean
	ProjetoService projetoService;
	
	@MockBean
	ProjetoRepository projetoRepository;
	
	ObjectMapper mapper;
	
	ProjetoInput projeInputComErroDeValidacao;
	ProjetoInput projetoInput;
	Projeto projeto;
	ProjetoOutput projetoOutput;
	
  @BeforeEach
  public void setup() {
	  mapper = new ObjectMapper();
  }
  
	@Test
	@DisplayName("Deve lançar 400-Bad-Request pq não enviamos corpo na requisição http")
	void cadastroFalhoPorqueFaltaCorpoNaRequisicao() throws Exception{
		mockMvc.perform(post("/projetos")
			.contentType(MediaType.APPLICATION_JSON)
			.content(mapper.writeValueAsString("")))
			.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
  @Test
  @DisplayName("Deve lançar codigo 400-Bad-Request pq tem erro de validacao no nome do projeto que deve ser não nulo")
  void cadastroFalhoPorCausaDeErroDeValidacaoNoNomeDoProjeto() throws Exception{
	  projeInputComErroDeValidacao = new ProjetoInput(null, "descricao-teste");
	  
	  mockMvc.perform(post("/projetos")
  		.contentType(MediaType.APPLICATION_JSON)
  		.content(mapper.writeValueAsString(projeInputComErroDeValidacao)))
  		.andExpect(MockMvcResultMatchers.status().isBadRequest());
  }
  
	@Test
	@DisplayName("Faz o cadastro com sucesso de um Projeto")
	void cadastroComSucesso() throws Exception{
	  	projetoInput = new ProjetoInput("nome-teste", "descricao-teste");
	  	projetoOutput = new ProjetoOutput(null , "nome-teste", "descricao-teste");
	  	projeto = new Projeto(1L, "nome-teste", "descricao-teste");
		
		when(projetoMapper.toEntity(projetoInput)).thenReturn(projeto);
		when(projetoService.cadastrar(projeto)).thenReturn(projeto);
		when(projetoMapper.toModelOutput(projeto)).thenReturn(projetoOutput);
		
		mockMvc.perform(post("/projetos")
			.contentType(MediaType.APPLICATION_JSON)
			.content(mapper.writeValueAsString(projetoInput)))
			.andExpect(MockMvcResultMatchers.status().isCreated())
			.andExpect(jsonPath("$.nome_do_Projeto").value(projetoInput.getNome_do_Projeto()))
			.andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(projetoOutput)));
	}
	
	  @Test
	  @DisplayName("Deve retornar uma lista vazia quando nao existem projetos cadastrados")
	  void recuperarTodosDeveRetornarListaVaziaQuandoNaoHouveCadastros() throws Exception{
	  	when(projetoMapper.toCollectionOutput(anyList())).thenReturn(Collections.emptyList());
	  	
	  	mockMvc.perform(get("/projetos")
	  		.contentType(MediaType.APPLICATION_JSON))
	  		.andExpect(MockMvcResultMatchers.status().isOk())
	  		.andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(Collections.emptyList())))
			.andExpect(jsonPath("$").isArray())
			.andExpect(jsonPath("$.length()").value(0));
	}
	  
    @Test
    @DisplayName("Deve retornar uma lista com apenas um projeto cadastrado")
    void recuperarTodosDeveRetornarApenasUmProjetoQuandoHouverUmProjetoCadastrado() throws Exception{
      	projetoOutput = new ProjetoOutput(1L, "nome-teste", "descricao-teste");
    	
    	when(projetoMapper.toCollectionOutput(anyList())).thenReturn(Collections.singletonList(projetoOutput));
    	
    	mockMvc.perform(get("/projetos")
    		.contentType(MediaType.APPLICATION_JSON))
    		.andExpect(MockMvcResultMatchers.status().isOk())
    		.andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(Collections.singletonList(projetoOutput))))
    		.andExpect(jsonPath("$").isArray())
    		.andExpect(jsonPath("$.length()").value(1));
    }
}










