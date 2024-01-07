package br.com.nfdeveloper.cursomc.dto;

import java.io.Serializable;

import br.com.nfdeveloper.cursomc.domain.Categoria;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public class CategoriaDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	@NotEmpty
	@Min(value = 5, message = "O tamanho deve ser maior que 4 caracteres.")
	@Max(value = 80, message = "O tamanho deve ter no máximo 80 caracteres.")
	private String nome;
	
	public CategoriaDTO() {
	
	}
	
	public CategoriaDTO(Categoria obj) {
		this.id = obj.getId();
		this.nome = obj.getNome();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
}
