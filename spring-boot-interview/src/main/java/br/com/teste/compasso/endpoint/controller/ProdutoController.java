package br.com.teste.compasso.endpoint.controller;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.teste.compasso.model.Produto;
import br.com.teste.compasso.service.ProdutoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping("/products")
@Api(value = "Controller produto")
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;

	@PostMapping
	@Transactional(rollbackFor = Exception.class)
	@ApiOperation(value = "Criação de um produto", response = Produto.class)
	public ResponseEntity<?> productsPost(@Valid @RequestBody Produto item) {
		log.info("Save Produto");
		return produtoService.save(item);
	}

	@PutMapping(path = "/{id}")
	@ApiOperation(value = "Atualização de um produto", response = Produto.class)
	public ResponseEntity<?> productsPut(@PathVariable("id") String id,@Valid @RequestBody Produto item) {
		log.info("Update produto by id");
		return produtoService.updateById(id, item);
	}

	@GetMapping(path = "/{id}")
	@ApiOperation(value = "Busca de um produto por ID", response = Produto.class)
	public ResponseEntity<?> getProdutoPorId(@PathVariable("id") String id) {
		log.info("Get produto by id");
		return produtoService.findById(id);
	}

	@GetMapping
	@ApiOperation(value = "Lista de produtos", response = Produto.class)
	public ResponseEntity<?> getProdutos() {
		log.info("Get produtos");
		return produtoService.findAll();
	}

	@GetMapping("/search")
	@ApiOperation(value = "Lista de produtos filtrados", response = Produto.class)
	public ResponseEntity<?> getProdutosFiltrados(@RequestParam("q") String q,
			@RequestParam("min_price") BigDecimal minPrice, @RequestParam("max_price") BigDecimal maxPrice) {
		log.info("Get produto by filtros");
		return produtoService.findByFiltros(q, minPrice, maxPrice);
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Deleção de um produto", response = Produto.class)
	public ResponseEntity<?> deleteProduto(@NotBlank @PathVariable("id") String id) {
		log.info("Deleção de um produto");
		return produtoService.delete(id);
	}

}
