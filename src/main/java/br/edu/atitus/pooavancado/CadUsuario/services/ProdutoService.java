package br.edu.atitus.pooavancado.CadUsuario.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.edu.atitus.pooavancado.CadUsuario.Entities.Produto;
import br.edu.atitus.pooavancado.CadUsuario.repositories.ProdutoRepository;

@Service
public interface ProdutoService extends GenericService<Produto, Long, ProdutoRepository> {

	Page<Produto> findByNome(Pageable pageable, String nome);
	
}
