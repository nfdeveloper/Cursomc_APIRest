package br.com.nfdeveloper.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nfdeveloper.cursomc.domain.Cliente;
import br.com.nfdeveloper.cursomc.services.ClienteService;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResources {

	@Autowired
	private ClienteService service;
	
	@GetMapping("/{id}")
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {
		Cliente obj = service.buscar(id);
		return ResponseEntity.ok().body(obj);
	}
}