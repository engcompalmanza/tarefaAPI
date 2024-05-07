package com.cjhsoft.tarefa.domain.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.util.List;
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
import com.cjhsoft.tarefa.domain.model.Usuario;

@DataJpaTest
@ActiveProfiles("test")
public class UsuarioRepositoryTest {
	
	@Autowired
	UsuarioRepository usuarioRepository;

	@Autowired
	TarefaRepository tarefaRepository;
	
	@Autowired
	ProjetoRepository projetoRepository;
	
	Projeto projeto;
	Tarefa tarefa;
	Usuario usuario;

	@BeforeEach
	void setUp() {
		this.projeto = new Projeto(null, "carro voador", "projeto de um carro voador");
		projetoRepository.save(projeto);
		
		this.tarefa = new Tarefa(null, "porta", "porta do carro voador", 
				new Date(), true, Status.EM_ANDAMENTO, projeto);
		tarefaRepository.save(tarefa);
		
		this.usuario = new Usuario(null, "jose", "jose@gmail.com", "123456", List.of(tarefa));
	}
	
	@Test
	@DisplayName("Deve cadastrar um usuario com sucesso")
	void cadastroDeUmUsuarioComSucesso() {
		assertEquals(usuarioRepository.count(), 0);
		
		usuarioRepository.save(usuario);
		
		assertEquals(usuarioRepository.count(), 1);
		
		assertEquals(usuarioRepository.findById(1L), Optional.of(usuario));
		
		System.out.println(usuario.toString());
	}
	
}
