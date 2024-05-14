package com.cjhsoft.tarefa.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cjhsoft.tarefa.domain.exception.BusinessException;
import com.cjhsoft.tarefa.domain.model.Usuario;
import com.cjhsoft.tarefa.domain.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

	@Mock
	UsuarioRepository usuarioRepository;
	
	@InjectMocks
	UsuarioService usuarioService;
	
	Usuario usuario;
	
	@Test
	@DisplayName("Deve lançar excessão pq recebeu Nulo como parametro de entrada")
	void cadastrarDeveLancarExcessaoCaso2() {
		assertThrows(NullPointerException.class, ()->{usuarioService.cadastrar(null);});

		verifyNoInteractions(usuarioRepository);
	}
	
	@Test
	@DisplayName("Metodo cadastrar Deve lançar excessão pq o email do novo cadastro ja existe")
	void cadastrarDeveLançaExcessaoCaso1() {
		usuario = new Usuario(null, "jose", "jose@gmail.com", "12345", null);
		
		when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.of(usuario));
		
		final BusinessException e = assertThrows(BusinessException.class, ()->{
			usuarioService.cadastrar(usuario);
		});
		
		assertEquals("Email já existente", e.getMessage());
		
		verify(usuarioRepository, never()).save(usuario);
		verify(usuarioRepository, times(1)).findByEmail(anyString());
		verifyNoMoreInteractions(usuarioRepository);
	}
	
	@Test
	@DisplayName("Deve cadastrar Com sucesso porque email não não existe no BD")
	void cadastrarComSucesso() {
		usuario = new Usuario(null, "jose", "jose@gmail.com", "12345", null);
		
		when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.empty());
		
		usuarioService.cadastrar(usuario);
		
		verify(usuarioRepository, times(1)).save(usuario);
		verify(usuarioRepository, times(1)).findByEmail(anyString());
		verifyNoMoreInteractions(usuarioRepository);
	}
	
}

















