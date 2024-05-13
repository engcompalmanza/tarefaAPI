package com.cjhsoft.tarefa.api.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.cjhsoft.tarefa.api.model.ProjetoInput;
import com.cjhsoft.tarefa.api.model.ProjetoOutput;
import com.cjhsoft.tarefa.domain.model.Projeto;

@Component
public class ProjetoMapper {

	public Projeto toEntity(ProjetoInput projetoInput) {
		Projeto projeto = new Projeto(null, projetoInput.getNome_do_Projeto(), projetoInput.getDescricao());
		return projeto;
	}
	
	public ProjetoOutput toModelOutput(Projeto projeto) {
		ProjetoOutput projetoOutput = new ProjetoOutput(projeto.getId() ,projeto.getNome(), projeto.getDescricao());
		return projetoOutput;
	}
	
	public List<ProjetoOutput> toCollectionOutput(List<Projeto> projetos){
		return projetos.stream().map(projeto -> 
			toModelOutput(projeto))
				.toList();
	}
		
}
