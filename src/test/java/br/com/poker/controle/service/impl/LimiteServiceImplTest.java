package br.com.poker.controle.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.poker.controle.models.Limite;
import br.com.poker.controle.repository.LimiteRepository;

@ExtendWith(MockitoExtension.class)
public class LimiteServiceImplTest {
	static LimiteServiceImpl service;
	static LimiteRepository repository;

	static Limite limite1;
	static Limite limite2;
	static List<Limite> limites;

	@BeforeAll
	static void setup() {
		repository = Mockito.mock(LimiteRepository.class);

		service = new LimiteServiceImpl();
		service.setRepository(repository);

		limite1 = retornarLimite1();
		limite2 = retornarLimite2();
		limites = retornarListaPerfis();

		lenient().when(repository.findAll()).thenReturn(limites);
	}

	@Test
	void deveretornarLimites() {
		List<Limite> perfisRetornados = service.buscar();

		assertEquals(limites, perfisRetornados);
	}

	private static Limite retornarLimite1() {
		return new Limite(1, new BigDecimal(0.1),  new BigDecimal(0.2));
	}

	private static Limite retornarLimite2() {
		return new Limite(1, new BigDecimal(0.2),  new BigDecimal(0.4));
	}

	private static List<Limite> retornarListaPerfis() {
		List<Limite> limitesLista = new ArrayList<Limite>();
		limitesLista.add(limite1);
		limitesLista.add(limite2);
		
		return limitesLista;
	}
}
