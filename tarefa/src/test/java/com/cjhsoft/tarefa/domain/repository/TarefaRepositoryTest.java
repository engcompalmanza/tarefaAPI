package com.cjhsoft.tarefa.domain.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.cjhsoft.tarefa.domain.model.Projeto;
import com.cjhsoft.tarefa.domain.model.Status;
import com.cjhsoft.tarefa.domain.model.Tarefa;

@DataJpaTest
@ActiveProfiles("test")
public class TarefaRepositoryTest {
	
	@Autowired
	TarefaRepository tarefaRepository;
	
	@Autowired
	ProjetoRepository projetoRepository;
	
	Projeto projeto;
	Tarefa tarefa;

	@BeforeEach
	void setUp() {
		this.projeto = new Projeto(null, "carro voador", "projeto de um carro voador");
		projetoRepository.save(projeto);
		
		this.tarefa = new Tarefa(null, "porta", "porta do carro voador", 
				LocalDate.now(), true, Status.EM_ANDAMENTO, projeto);
	}
	
	@Test
	@DisplayName("Deve cadastrar uma tarefa com sucesso")
	void cadastroDeUmaTarefaComSucesso() {
		assertEquals(tarefaRepository.count(), 0);
		
		tarefaRepository.save(tarefa);
		
		assertEquals(tarefaRepository.count(), 1);
		
		//assertEquals(tarefaRepository.findById(1L), Optional.of(tarefa));
	}
	
}
