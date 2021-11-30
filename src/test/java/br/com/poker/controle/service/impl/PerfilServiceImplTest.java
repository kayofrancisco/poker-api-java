package br.com.poker.controle.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.poker.controle.models.Perfil;
import br.com.poker.controle.repository.PerfilRepository;

@ExtendWith(MockitoExtension.class)
public class PerfilServiceImplTest {
	static PerfilServiceImpl service;
	static PerfilRepository repository;

	static Perfil perfil1;
	static Perfil perfil2;
	static List<Perfil> perfis;

	@BeforeAll
	static void setup() {
		repository = Mockito.mock(PerfilRepository.class);

		service = new PerfilServiceImpl();
		service.setRepository(repository);

		perfil1 = retornarPerfil1();
		perfil2 = retornarPerfil2();
		perfis = retornarListaPerfis();

		lenient().when(repository.findAll()).thenReturn(perfis);
	}

	@Test
	void deveretornarPerfils() {
		List<Perfil> perfisRetornados = service.buscar();

		assertEquals(perfis, perfisRetornados);
	}

	private static Perfil retornarPerfil1() {
		return new Perfil(1, "Administrador",  "ADM");
	}

	private static Perfil retornarPerfil2() {
		return new Perfil(2, "Comum", "COM");
	}

	private static List<Perfil> retornarListaPerfis() {
		return List.of(perfil1, perfil2);
	}
}
