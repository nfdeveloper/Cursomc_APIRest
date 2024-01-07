package br.com.nfdeveloper.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nfdeveloper.cursomc.domain.Pedido;
import br.com.nfdeveloper.cursomc.repositories.PedidoRepository;
import br.com.nfdeveloper.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;
	
	public Pedido buscar(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: "+ id
				+ ", Tipo: "+ Pedido.class.getName()));
	}
}
