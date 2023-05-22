package br.edu.atitus.pooavancado.CadUsuario.controllers;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.atitus.pooavancado.CadUsuario.Entities.Produto;
import br.edu.atitus.pooavancado.CadUsuario.services.ProdutoService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/produtos")
public class ProdutoController {

	final ProdutoService produtoService;
	
	public ProdutoController(ProdutoService produtoService) {
		super();
		this.produtoService = produtoService;
	}
	
	@PostMapping
	public ResponseEntity<Object> postProduto(@RequestBody Produto produto) {
		return salvar(produto);		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Object> putProduto(@PathVariable long id, @RequestBody Produto produto) {
		produto.setId(id);
		return salvar(produto);
	}

	@GetMapping()
	public ResponseEntity<Object> getProduto(@PageableDefault(page = 0, size = 5, sort = "id", direction = Direction.ASC) Pageable pageable, @RequestParam String nome) {
		Page<Produto> produtos = produtoService.findByNome(pageable, nome);
		return ResponseEntity.status(HttpStatus.OK).body(produtos);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getProdutobyId(@PathVariable long id) {
		Optional<Produto> produto = produtoService.findById(id);
		if (produto.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado com o Id " + id);
		else
			return ResponseEntity.status(HttpStatus.OK).body(produto);
	}

	

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteProduto(@PathVariable long id) {
		produtoService.deleteById(id);
		return ResponseEntity.status(HttpStatus.OK).body("Produto com Id " + id + " deletado com sucesso!");
	}
	
	
	private ResponseEntity<Object> salvar(Produto produto) {
		try {
			produtoService.save(produto);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(produto);
	}
	
	
}
