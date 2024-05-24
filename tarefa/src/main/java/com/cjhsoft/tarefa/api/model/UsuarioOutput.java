package com.cjhsoft.tarefa.api.model;

import java.util.List;

import com.cjhsoft.tarefa.domain.model.Tarefa;

import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioOutput {
	
	private Long id;
	
	private String nome;
	
	private String email;
	
	private List<String> nomestarefas;

}
