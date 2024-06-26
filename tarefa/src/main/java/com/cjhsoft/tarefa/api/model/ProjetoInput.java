package com.cjhsoft.tarefa.api.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProjetoInput {
	
	@NotBlank
	@Size(max = 90)
	private String nome_do_Projeto;
	
	@Size(max = 255)
	private String descricao;

}
