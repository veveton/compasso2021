package br.com.teste.compasso.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.teste.compasso.model.Produto;
import br.com.teste.compasso.repositorio.ProdutoRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	public ResponseEntity<?> save(Produto item) {
		return new ResponseEntity<>(produtoRepository.save(item), HttpStatus.CREATED);
	}

	public ResponseEntity<?> updateById(String id, Produto item) {
		item.setId(id);
		return new ResponseEntity<>(produtoRepository.save(item), HttpStatus.OK);
	}

	public ResponseEntity<?> findById(String id) {
		ResponseEntity<?> retorno = null;
		retorno = new ResponseEntity<>(produtoRepository.findById(id), HttpStatus.OK);
		if (retorno.getBody().equals(Optional.empty())) {
			retorno = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return retorno;
	}

	public ResponseEntity<?> findAll() {
		return new ResponseEntity<>(produtoRepository.findAll(), HttpStatus.OK);
	}

	public ResponseEntity<?> delete(String id) {
		ResponseEntity<?> retorno = new ResponseEntity<>(HttpStatus.OK);
		try {
			produtoRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			retorno = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return retorno;
	}

	public ResponseEntity<?> findByFiltros(String q, BigDecimal minPrice, BigDecimal maxPrice) {
		return new ResponseEntity<>(produtoRepository.findByPriceBetweenAndNameAndDescription(minPrice, maxPrice, q, q), HttpStatus.OK);
	}

}
