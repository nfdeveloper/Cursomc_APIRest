package br.com.nfdeveloper.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.nfdeveloper.cursomc.domain.Cidade;
import br.com.nfdeveloper.cursomc.domain.Cliente;
import br.com.nfdeveloper.cursomc.domain.Endereco;
import br.com.nfdeveloper.cursomc.domain.enums.TipoCliente;
import br.com.nfdeveloper.cursomc.dto.ClienteDTO;
import br.com.nfdeveloper.cursomc.dto.ClienteNewDTO;
import br.com.nfdeveloper.cursomc.repositories.CidadeRepository;
import br.com.nfdeveloper.cursomc.repositories.ClienteRepository;
import br.com.nfdeveloper.cursomc.repositories.EnderecoRepository;
import br.com.nfdeveloper.cursomc.services.exceptions.DataIntegrityException;
import br.com.nfdeveloper.cursomc.services.exceptions.ObjectNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public Cliente buscar(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: "+ id
				+ ", Tipo: "+ Cliente.class.getName()));
	}
	
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}
	
	public Cliente update(Cliente obj) {
		Cliente newObj = buscar(obj.getId()); 
		updateData(newObj, obj);
		return repo.save(newObj); 
	}
	
	public void delete(Integer id) {
		
		try {
			repo.deleteById(id);			
		}
		catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há entidades relacionadas.");
		}	
	}
	
	public List<Cliente> findAll(){
		return repo.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO objDTO) {
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null);
	}
	
	public Cliente fromDTO(ClienteNewDTO objDTO) {
		Cliente cli = new Cliente(null, objDTO.getNome(), objDTO.getEmail(), objDTO.getCpfOuCnpj(), TipoCliente.toEnum(objDTO.getTipo()));
		Cidade cid = cidadeRepository.findById(objDTO.getCidadeId()).get();
		Endereco end = new Endereco(null, objDTO.getLogradouro(), objDTO.getNumero(), objDTO.getComplemento(), objDTO.getBairro(), objDTO.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDTO.getTelefone1());
		if(objDTO.getTelefone2() != null) {	
			cli.getTelefones().add(objDTO.getTelefone2());
		}
		if(objDTO.getTelefone3() != null) {	
			cli.getTelefones().add(objDTO.getTelefone3());
		}
		return cli;
	}
	
	// Para permanecer os dados que não foram informados no DTO.
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
}
