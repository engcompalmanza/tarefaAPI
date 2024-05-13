package com.cjhsoft.tarefa.api.model;

import java.util.List;

import com.cjhsoft.tarefa.domain.model.Tarefa;

import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
public class UsuarioOutput {
	
	private String nome;
	
	private String email;
	
	private List<String> nomestarefas;

}
