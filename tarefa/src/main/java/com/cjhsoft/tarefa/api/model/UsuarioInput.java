package com.cjhsoft.tarefa.api.model;

import java.util.List;

import com.cjhsoft.tarefa.domain.model.Tarefa;

import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UsuarioInput {
	
	@NotBlank
	private String nome;
	
	@NotNull
	@Email
	private String email;
	
	@NotBlank
	private String senha;
	
	private List<Long> tarefasIds;

}
