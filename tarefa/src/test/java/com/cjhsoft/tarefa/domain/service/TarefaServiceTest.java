package com.cjhsoft.tarefa.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cjhsoft.tarefa.domain.exception.BusinessException;
import com.cjhsoft.tarefa.domain.model.Status;
import com.cjhsoft.tarefa.domain.model.Tarefa;
import com.cjhsoft.tarefa.domain.repository.TarefaRepository;

@ExtendWith(MockitoExtension.class)
public class TarefaServiceTest {

	@Mock
	TarefaRepository tarefaRepository;
	
	@InjectMocks
	TarefaService tarefaService;
	
	Tarefa tarefa;
	
	@Test
	@DisplayName("Deve lançar excessão NullPointerException porque parametro de entrada é Nulo")
	void cadastrarLançaExcessaoCaso1() {
		assertThrows(NullPointerException.class, () -> tarefaService.cadastrar(null));
		
		verifyNoInteractions(tarefaRepository);
	}
	
	@Test
	@DisplayName("Deve lançar excessão BusinessException porque a data é anterior a atual ")
	void cadastrarLançaExcessaoCaso2() {
		this.tarefa = new Tarefa(null, "tarefa-test", "descricao-teste", LocalDate.of(1990, 1, 1), true, Status.PENDENTE, null);
		
		final BusinessException e = assertThrows(BusinessException.class, ()-> tarefaService.cadastrar(tarefa));
		
		assertEquals("Data de Vencimento invalida.", e.getMessage());
		verifyNoInteractions(tarefaRepository);
	}
	
	@Test
	@DisplayName("Deve cadastrar com sucesso porque data esta no futuro")
	void cadastrarComSucesso() {
		this.tarefa = new Tarefa(null, "tarefa-test", "descricao-teste", LocalDate.of(2100, 1, 1), true, Status.PENDENTE, null);
	
		tarefaService.cadastrar(tarefa);
		
		verify(tarefaRepository, times(1)).save(tarefa);
		verifyNoMoreInteractions(tarefaRepository);
	}
	
}

















