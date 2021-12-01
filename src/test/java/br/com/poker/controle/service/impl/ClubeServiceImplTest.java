package br.com.poker.controle.service.impl;

import static br.com.poker.controle.utils.validadores.alertas.AlertasClube.alertaNomeNuloOuVazio;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.lenient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.poker.controle.exceptions.NegocioException;
import br.com.poker.controle.models.Clube;
import br.com.poker.controle.repository.ClubeRepository;

@ExtendWith(MockitoExtension.class)
public class ClubeServiceImplTest {
	static ClubeServiceImpl clubeService;
	static ClubeRepository clubeRepositoryMock;

	static Clube clube1;
	static Clube clube2;
	static Clube clube3;
	static List<Clube> clubes;

	@BeforeAll
	static void setup() {
		clubeRepositoryMock = Mockito.mock(ClubeRepository.class);

		clubeService = new ClubeServiceImpl();
		clubeService.setRepository(clubeRepositoryMock);

		clube1 = retornarClube1();
		clube2 = retornarClube2();
		clube3 = retornarClube3();
		clubes = retornarListaClubes();

		lenient().when(clubeService.buscar()).thenReturn(clubes);
		lenient().when(clubeService.buscarPorId(1)).thenReturn(Optional.of(clube1));
		lenient().when(clubeService.buscarPorId(2)).thenReturn(Optional.of(clube2));
		lenient().when(clubeService.buscarPorId(3)).thenReturn(Optional.of(clube3));
		lenient().when(clubeService.buscarPorId(4)).thenReturn(Optional.ofNullable(null));
	}

	@Test
	void deveRetornarClubes() {
		List<Clube> clubesRetornados = clubeService.buscar();

		assertEquals(clubes, clubesRetornados);
	}

	@Test
	void deveRetornarErroPorNomeNulo() {
		Clube clubeValido = retornarClube1();

		clubeValido.setNome(null);

		NegocioException e = assertThrows(NegocioException.class, () -> {
			clubeService.cadastrar(clubeValido);
		});

		assertTrue(e.getMensagemErros().contains(alertaNomeNuloOuVazio()));
	}

	@Test
	void deveRetornarCadastrarClube() {
		Clube clubeValido = retornarClube1();

		try {
			clubeService.cadastrar(clubeValido);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	void deveRetornarErroPorIdNaoEncontrado() {
		Clube clubeValido = retornarClube2();

		NegocioException e = assertThrows(NegocioException.class, () -> {
			clubeService.editar(clubeValido, 4);
		});

		assertTrue(e.getMensagemErros().contains("Clube não encontrado para o id informado"));
	}

	@Test
	void deveRetornarErroPorNomeNaoInformado() {
		Clube clubeValido = retornarClube2();

		clubeValido.setNome(null);

		NegocioException e = assertThrows(NegocioException.class, () -> {
			clubeService.editar(clubeValido, 2);
		});

		assertTrue(e.getMensagemErros().contains(alertaNomeNuloOuVazio()));
	}

	@Test
	void deveEditarClube() {
		Clube clubeValido = retornarClube3();

		try {
			clubeService.editar(clubeValido, 3);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	void deveExcluirClube() {
		try {
			clubeService.deletar(1);
		} catch (Exception e) {
			fail();
		}
	}

	private static Clube retornarClube1() {
		LocalDateTime dataAgora = LocalDateTime.now();

		return new Clube(1, "Clube 1", dataAgora, dataAgora);
	}

	private static Clube retornarClube2() {
		LocalDateTime dataAgora = LocalDateTime.now();

		return new Clube(2, "Clube 2", dataAgora, dataAgora);
	}

	private static Clube retornarClube3() {
		LocalDateTime dataAgora = LocalDateTime.now();

		return new Clube(3, "Clube 3", dataAgora, dataAgora);
	}

	private static List<Clube> retornarListaClubes() {
		return List.of(clube1, clube2, clube3);
	}
}
