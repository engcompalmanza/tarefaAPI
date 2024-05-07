package com.cjhsoft.tarefa.domain.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.cjhsoft.tarefa.domain.model.Projeto;

@DataJpaTest
@ActiveProfiles("test")
public class ProjetoRepositoryTest {

	@Autowired
	ProjetoRepository projetoRepository;
	
	Projeto projeto;
	
	@BeforeEach
	void setUp() {
		this.projeto = new Projeto(null, "carro voador", "projeto de um carro voador");
	}
	
	@Test
	@DisplayName("Deve cadastrar um projeto com sucesso")
	void cadastroDeUmProjetoComSucesso() {
		assertEquals(projetoRepository.count(), 0);
		
		projetoRepository.save(projeto);
		
		assertEquals(projetoRepository.count(), 1);
		
		assertEquals(projetoRepository.findById(1L), Optional.of(projeto));
	}
	
}







