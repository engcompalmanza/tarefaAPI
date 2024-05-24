package com.cjhsoft.tarefa.api.mapper;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cjhsoft.tarefa.api.model.TarefaInput;
import com.cjhsoft.tarefa.api.model.TarefaOutput;
import com.cjhsoft.tarefa.domain.model.Projeto;
import com.cjhsoft.tarefa.domain.model.Tarefa;
import com.cjhsoft.tarefa.domain.repository.ProjetoRepository;

@Component
public class TarefaMapper {
	
	@Autowired
	ProjetoRepository projetoRepository;

	public Tarefa toEntity(TarefaInput tarefaInput) {
		Tarefa tarefa = new Tarefa();
		tarefa.setTitulo(tarefaInput.getTitulo());
		tarefa.setDescricao(tarefaInput.getDescricao());
		tarefa.setStatus(tarefaInput.getStatus());
		tarefa.setPrioridade(tarefaInput.getPrioridade());
		tarefa.setVencimento(tarefaInput.getVencimento());
		
		Projeto projeto = projetoRepository.findById(tarefaInput.getProjeto()).get();
		tarefa.setProjeto(projeto);
		return tarefa;
	}
	
	public TarefaOutput toModelOutput(Tarefa tarefa) {
		TarefaOutput tarefaOutput = new TarefaOutput();
		tarefaOutput.setTitulo(tarefa.getTitulo());
		tarefaOutput.setDescricao(tarefa.getDescricao());
		tarefaOutput.setStatus(tarefa.getStatus());
		tarefaOutput.setPrioridade(tarefa.getPrioridade());
		tarefaOutput.setVencimento(tarefa.getVencimento());
		tarefaOutput.setNomeProjeto(tarefa.getProjeto().getNome());
		return tarefaOutput;
	}
	
	public List<TarefaOutput> toCollectionOutput(List<Tarefa> tarefas){
		return tarefas.stream().map(tarefa -> 
			toModelOutput(tarefa))
				.toList();
	}
	
}
