package br.com.poker.controle.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.lenient;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.poker.controle.exceptions.NegocioException;
import br.com.poker.controle.models.Conta;
import br.com.poker.controle.models.Limite;
import br.com.poker.controle.models.Partida;
import br.com.poker.controle.models.Usuario;
import br.com.poker.controle.repository.PartidaRepository;

import static br.com.poker.controle.utils.validadores.alertas.AlertasPartida.*;

@ExtendWith(MockitoExtension.class)
public class PartidaServiceImplTest {
	static PartidaServiceImpl service;
	static PartidaRepository repository;

	static Conta conta;
	static Limite limite;

	static Partida partida1;
	static Partida partida2;
	static List<Partida> partidas;

	@BeforeAll
	static void setup() {
		repository = Mockito.mock(PartidaRepository.class);

		service = new PartidaServiceImpl();
		service.setRepository(repository);

		conta = retornarConta();
		limite = retornarLimite();

		partida1 = retornarPartida1();
		partida2 = retornarPartida2();
		partidas = retornarPartidas();

		lenient().when(service.buscar()).thenReturn(partidas);
		lenient().when(repository.findById(partida1.getId())).thenReturn(Optional.of(partida1));
		lenient().when(repository.findById(partida2.getId())).thenReturn(Optional.of(partida2));
		lenient().when(repository.findById(3)).thenReturn(Optional.ofNullable(null));
	}

	@Test
	void deveRetornarPartidas() {
		List<Partida> partidasRetornadas = service.buscar();

		assertEquals(partidas, partidasRetornadas);
	}

	@Test
	void deveRetornarErroPorValorNulo() {
		Partida partida = retornarPartida1();
		partida.setValor(null);

		NegocioException e = assertThrows(NegocioException.class, () -> {
			service.cadastrar(partida);
		});

		assertTrue(e.getMensagemErros().contains(alertaValorNulo()));
	}

	@Test
	void deveRetornarErroPorQuantidadeMaosNulo() {
		Partida partida = retornarPartida1();
		partida.setQuantidadeMaos(null);

		NegocioException e = assertThrows(NegocioException.class, () -> {
			service.cadastrar(partida);
		});

		assertTrue(e.getMensagemErros().contains(alertaQuantidadeMaosNuloOuMenorQueZero()));
	}

	@Test
	void deveRetornarErroPorQuantidadeMaosMenorQueZero() {
		Partida partida = retornarPartida1();
		partida.setQuantidadeMaos(-1);

		NegocioException e = assertThrows(NegocioException.class, () -> {
			service.cadastrar(partida);
		});

		assertTrue(e.getMensagemErros().contains(alertaQuantidadeMaosNuloOuMenorQueZero()));
	}

	@Test
	void deveRetornarErroPorContaNulo() {
		Partida partida = retornarPartida1();
		partida.setConta(null);

		NegocioException e = assertThrows(NegocioException.class, () -> {
			service.cadastrar(partida);
		});

		assertTrue(e.getMensagemErros().contains(alertaContaNulo()));
	}

	@Test
	void deveRetornarErroPorIdContaNulo() {
		Partida partida = retornarPartida1();
		partida.getConta().setId(null);

		NegocioException e = assertThrows(NegocioException.class, () -> {
			service.cadastrar(partida);
		});

		assertTrue(e.getMensagemErros().contains(alertaContaNulo()));
	}

	@Test
	void deveRetornarErroPorLimiteNulo() {
		Partida partida = retornarPartida1();
		partida.setLimite(null);

		NegocioException e = assertThrows(NegocioException.class, () -> {
			service.cadastrar(partida);
		});

		assertTrue(e.getMensagemErros().contains(alertaLimiteNulo()));
	}

	@Test
	void deveRetornarErroPorIdLimiteNulo() {
		Partida partida = retornarPartida1();
		partida.getLimite().setId(null);

		NegocioException e = assertThrows(NegocioException.class, () -> {
			service.cadastrar(partida);
		});

		assertTrue(e.getMensagemErros().contains(alertaLimiteNulo()));
	}

	@Test
	void deveRetornarErroPorDataCriacaoNulo() {
		Partida partida = retornarPartida1();
		partida.setData(null);

		NegocioException e = assertThrows(NegocioException.class, () -> {
			service.cadastrar(partida);
		});

		assertTrue(e.getMensagemErros().contains(alertaDataCriacaoNulo()));
	}

	@Test
	void deveCadastrarPartida() {
		try {
			Partida partida = retornarPartida1();

			Conta contaPersist = retornarConta();
			contaPersist.setId(1);

			Limite limitePersist = retornarLimite();
			limitePersist.setId(1);

			partida.setConta(contaPersist);
			partida.setLimite(limitePersist);

			service.cadastrar(partida);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	void deveRetornarErroPorIdNaoEncontrado() {
		Partida partida = retornarPartida1();

		NegocioException e = assertThrows(NegocioException.class, () -> {
			service.editar(partida, 3);
		});

		assertTrue(e.getMensagemErros().contains("Partida não encontrada para o id informado"));
	}

	@Test
	void deveRetornarErroPorValorNuloEmEditar() {
		Partida partida = retornarPartida1();
		partida.setValor(null);

		NegocioException e = assertThrows(NegocioException.class, () -> {
			service.editar(partida, 1);
		});

		assertTrue(e.getMensagemErros().contains(alertaValorNulo()));
	}

	@Test
	void deveRetornarErroPorQuantidadeMaosNuloEmEditar() {
		Partida partida = retornarPartida1();
		partida.setQuantidadeMaos(null);

		NegocioException e = assertThrows(NegocioException.class, () -> {
			service.editar(partida, 1);
		});

		assertTrue(e.getMensagemErros().contains(alertaQuantidadeMaosNuloOuMenorQueZero()));
	}

	@Test
	void deveRetornarErroPorQuantidadeMaosMenorQueZeroEmEditar() {
		Partida partida = retornarPartida1();
		partida.setQuantidadeMaos(-1);

		NegocioException e = assertThrows(NegocioException.class, () -> {
			service.editar(partida, 1);
		});

		assertTrue(e.getMensagemErros().contains(alertaQuantidadeMaosNuloOuMenorQueZero()));
	}

	@Test
	void deveRetornarErroPorContaNuloEmEditar() {
		Partida partida = retornarPartida1();
		partida.setConta(null);

		NegocioException e = assertThrows(NegocioException.class, () -> {
			service.editar(partida, 1);
		});

		assertTrue(e.getMensagemErros().contains(alertaContaNulo()));
	}

	@Test
	void deveRetornarErroPorIdContaNuloEmEditar() {
		Partida partida = retornarPartida1();
		partida.getConta().setId(null);

		NegocioException e = assertThrows(NegocioException.class, () -> {
			service.editar(partida, 1);
		});

		assertTrue(e.getMensagemErros().contains(alertaContaNulo()));
	}

	@Test
	void deveRetornarErroPorLimiteNuloEmEditar() {
		Partida partida = retornarPartida1();
		partida.setLimite(null);

		NegocioException e = assertThrows(NegocioException.class, () -> {
			service.editar(partida, 1);
		});

		assertTrue(e.getMensagemErros().contains(alertaLimiteNulo()));
	}

	@Test
	void deveRetornarErroPorIdLimiteNuloEmEditar() {
		Partida partida = retornarPartida1();
		partida.getLimite().setId(null);

		NegocioException e = assertThrows(NegocioException.class, () -> {
			service.editar(partida, 1);
		});

		assertTrue(e.getMensagemErros().contains(alertaLimiteNulo()));
	}

	@Test
	void deveRetornarErroPorDataCriacaoNuloEmEditar() {
		Partida partida = retornarPartida1();
		partida.setData(null);

		NegocioException e = assertThrows(NegocioException.class, () -> {
			service.editar(partida, 1);
		});

		assertTrue(e.getMensagemErros().contains(alertaDataCriacaoNulo()));
	}

	@Test
	void deveEditarPartida() throws NegocioException {
		Partida partida = retornarPartida1();
		partida.setData(LocalDateTime.now());

		Conta contaPersist = retornarConta();
		contaPersist.setId(1);

		partida.setConta(contaPersist);

		service.editar(partida, 1);
	}

	private static Conta retornarConta() {
		Usuario usuario = new Usuario();
		usuario.setId(1);
		usuario.setAtivo(Boolean.TRUE);
		usuario.setCriadoEm(LocalDateTime.now());
		usuario.setEmail("teste@hotmail.com");
		usuario.setNome("Usuario teste");

		Conta novaConta = new Conta();

		novaConta.setId(1);
		novaConta.setNick("Nick 1");
		novaConta.setUsuario(usuario);

		return novaConta;
	}

	private static Limite retornarLimite() {
		Limite novoLimite = new Limite();

		novoLimite.setId(1);
		novoLimite.setSmallBlind(new BigDecimal(1));
		novoLimite.setBigBlind(new BigDecimal(2));

		return novoLimite;
	}

	private static Partida retornarPartida1() {
		return new Partida(1, new BigDecimal(100), 100, 140, LocalDateTime.now(), limite, conta);
	}

	private static Partida retornarPartida2() {
		return new Partida(1, new BigDecimal(36), 36, 312, LocalDateTime.now(), limite, conta);
	}

	private static List<Partida> retornarPartidas() {
		return List.of(partida1, partida2);
	}

}
