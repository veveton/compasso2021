package br.com.teste.compasso.repositorio;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.teste.compasso.model.Produto;

public interface ProdutoRepository extends PagingAndSortingRepository<Produto, String> {
	List<Produto> findByPriceBetweenAndNameAndDescription(BigDecimal priceMin, BigDecimal priceMax, String name,
			String description);
}