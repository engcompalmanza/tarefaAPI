package com.cjhsoft.tarefa.api.model;

import java.time.LocalDate;
import java.util.Date;

import com.cjhsoft.tarefa.domain.model.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TarefaOutput {
	
	private String titulo;
	
	private String descricao;
	
	private LocalDate vencimento;
	
	private Boolean prioridade;
	
	private Status status;
	
	private String nomeProjeto;

}
