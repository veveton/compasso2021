package br.com.teste.compasso;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URL;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.teste.compasso.model.Produto;
import br.com.teste.compasso.repositorio.ProdutoRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ProdutoControllerTest {
	@Autowired
	private TestRestTemplate restTemplate;
	@MockBean
	private ProdutoRepository produtoRepository;
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void postProdutoRestTest201() {
		Produto item = new Produto(null, "name", "Descricao", BigDecimal.TEN);
		Produto itemRetorno = new Produto("3121", "name", "Descricao", BigDecimal.TEN);
		BDDMockito.when(produtoRepository.save(item)).thenReturn(itemRetorno);
		ResponseEntity<Produto> response = restTemplate.postForEntity("/products", item, Produto.class);
		Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(HttpStatus.CREATED.value());
		Assertions.assertThat(response.getBody().getId()).isNotNull();
	}

	@Test
	public void postProdutoRestTest400() {
		Produto item = new Produto("123", null, "Descricao", BigDecimal.TEN);
		BDDMockito.when(produtoRepository.save(item)).thenReturn(item);
		ResponseEntity<Produto> response = restTemplate.postForEntity("/products", item, Produto.class);
		Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	public void putTest400() throws Exception {
		Produto item = new Produto("123", null, "Descricao", BigDecimal.TEN);
		mockMvc.perform(put("/products/{id}", item).contentType("application/json").accept("application/json"))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void findAllTest200() throws Exception {
		Produto item = new Produto("135", "name", "Descricao", BigDecimal.TEN);
		BDDMockito.when(produtoRepository.save(item)).thenReturn(item);

		mockMvc.perform(
				MockMvcRequestBuilders.get("/products").contentType("application/json").accept("application/json"))
				.andExpect(status().isOk());

	}

}
