package br.com.nfdeveloper.cursomc.dto;

import java.io.Serializable;

import br.com.nfdeveloper.cursomc.domain.Cliente;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public class ClienteDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	@NotEmpty(message = "Preenchimento obrigatório")
	@Min(value = 5, message = "O tamanho deve ser maior que 4 caracteres.")
	@Max(value = 120, message = "O tamanho deve ter no máximo 120 caracteres.")
	private String nome;
	
	@NotEmpty(message = "Preenchimento obrigatório")
	private String email;
	
	public ClienteDTO() {
		
	}

	public ClienteDTO(Cliente obj) {
		id = obj.getId();
		nome = obj.getNome();
		email = obj.getEmail();
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
}
