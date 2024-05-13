package com.cjhsoft.tarefa.api.model;

import java.time.LocalDate;
import java.util.Date;

import com.cjhsoft.tarefa.domain.model.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
//@JsonIgnoreProperties(ignoreUnknown = true)
public class TarefaInput {
	
	@NotBlank
	private String titulo;
	
	private String descricao;
	
	@NotNull
	private LocalDate vencimento;
	
	@NotNull
	private Boolean prioridade;
	
	@NotNull
	private Status status;
	
	@NotNull
	private Long projeto;
	
}
