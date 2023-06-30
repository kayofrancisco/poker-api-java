package br.com.poker.controle.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.lenient;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import br.com.poker.controle.exceptions.NegocioException;
import br.com.poker.controle.models.Conta;
import br.com.poker.controle.models.Limite;
import br.com.poker.controle.models.Partida;
import br.com.poker.controle.models.Usuario;
import br.com.poker.controle.repository.PartidaRepository;
import br.com.poker.controle.service.UsuarioService;

import static br.com.poker.controle.utils.validadores.alertas.AlertasPartida.*;

@ExtendWith(MockitoExtension.class)
public class PartidaServiceImplTest {
	static PartidaServiceImpl service;
	static PartidaRepository repository;
	static UsuarioServiceImpl usuarioService;

	static Limite limite;
	static Usuario usuarioLogado;

	static Partida partida1;
	static Partida partida2;
	static List<Partida> partidas;

	@BeforeAll
	static void setup() {
		repository = Mockito.mock(PartidaRepository.class);
		usuarioService = Mockito.mock(UsuarioServiceImpl.class);

		service = new PartidaServiceImpl();
		service.setRepository(repository);
		service.setUsuarioService(usuarioService);

		limite = retornarLimite();
		usuarioLogado = retornarUsuario();

		partida1 = retornarPartida1();
		partida2 = retornarPartida2();
		partidas = retornarPartidas();

		lenient().when(usuarioService.recuperaUsuarioLogado()).thenReturn(usuarioLogado);
//		lenient().when(repository.findById(partida1.getId())).thenReturn(Optional.of(partida1));
//		lenient().when(repository.findById(partida2.getId())).thenReturn(Optional.of(partida2));
//		lenient().when(repository.findById(3)).thenReturn(Optional.ofNullable(null));
	}

	@Test
	void deveRetornarErroPorQuantidadeMaosInicioNulo() {
		Partida partida = retornarPartida1();
		partida.setQuantidadeMaosInicio(null);

		NegocioException e = assertThrows(NegocioException.class, () -> {
			service.cadastrar(partida);
		});

		assertTrue(e.getMensagemErros().contains(alertaQuantidadeMaosNuloOuMenorQueZero()));
	}
	
	@Test
	void deveRetornarErroPorQuantidadeMaosInicioMenorQueZero() {
		Partida partida = retornarPartida1();
		partida.setQuantidadeMaosInicio(-1);

		NegocioException e = assertThrows(NegocioException.class, () -> {
			service.cadastrar(partida);
		});

		assertTrue(e.getMensagemErros().contains(alertaQuantidadeMaosNuloOuMenorQueZero()));
	}
	
	@Test
	void deveRetornarErroPorFichasIniciasNulo() {
		Partida partida = retornarPartida1();
		partida.setFichasIniciais(null);

		NegocioException e = assertThrows(NegocioException.class, () -> {
			service.cadastrar(partida);
		});

		assertTrue(e.getMensagemErros().contains(alertaFichasIniciaisNulo()));
	}
	
	@Test
	void deveRetornarErroPorFichasIniciasMenorQueZero() {
		Partida partida = retornarPartida1();
		partida.setFichasIniciais(new BigDecimal(-1));

		NegocioException e = assertThrows(NegocioException.class, () -> {
			service.cadastrar(partida);
		});

		assertTrue(e.getMensagemErros().contains(alertaFichasIniciaisNulo()));
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
	void deveRetornarErroPorDataCriacaoNulo() {
		Partida partida = retornarPartida1();
		partida.setDataInicio(null);

		NegocioException e = assertThrows(NegocioException.class, () -> {
			service.cadastrar(partida);
		});

		assertTrue(e.getMensagemErros().contains(alertaDataCriacaoNulo()));
	}
	
	@Test
	void deveCadastrarPartida() {
		try {
			Partida partida = retornarPartida1();
			service.cadastrar(partida);
		} catch (Exception e) {
			fail();
		}
	}

//	@Test
//	void deveRetornarErroPorIdNaoEncontrado() {
//		Partida partida = retornarPartida1();
//
//		NegocioException e = assertThrows(NegocioException.class, () -> {
//			service.editar(partida, 3);
//		});
//
//		assertTrue(e.getMensagemErros().contains("Partida não encontrada para o id informado"));
//	}
//
//	@Test
//	void deveRetornarErroPorValorNuloEmEditar() {
//		Partida partida = retornarPartida1();
//		partida.setValor(null);
//
//		NegocioException e = assertThrows(NegocioException.class, () -> {
//			service.editar(partida, 1);
//		});
//
//		assertTrue(e.getMensagemErros().contains(alertaValorNulo()));
//	}
//
//	@Test
//	void deveRetornarErroPorQuantidadeMaosNuloEmEditar() {
//		Partida partida = retornarPartida1();
//		partida.setQuantidadeMaos(null);
//
//		NegocioException e = assertThrows(NegocioException.class, () -> {
//			service.editar(partida, 1);
//		});
//
//		assertTrue(e.getMensagemErros().contains(alertaQuantidadeMaosNuloOuMenorQueZero()));
//	}
//
//	@Test
//	void deveRetornarErroPorQuantidadeMaosMenorQueZeroEmEditar() {
//		Partida partida = retornarPartida1();
//		partida.setQuantidadeMaos(-1);
//
//		NegocioException e = assertThrows(NegocioException.class, () -> {
//			service.editar(partida, 1);
//		});
//
//		assertTrue(e.getMensagemErros().contains(alertaQuantidadeMaosNuloOuMenorQueZero()));
//	}
//
//	@Test
//	void deveRetornarErroPorContaNuloEmEditar() {
//		Partida partida = retornarPartida1();
//		partida.setConta(null);
//
//		NegocioException e = assertThrows(NegocioException.class, () -> {
//			service.editar(partida, 1);
//		});
//
//		assertTrue(e.getMensagemErros().contains(alertaContaNulo()));
//	}
//
//	@Test
//	void deveRetornarErroPorIdContaNuloEmEditar() {
//		Partida partida = retornarPartida1();
//		partida.getConta().setId(null);
//
//		NegocioException e = assertThrows(NegocioException.class, () -> {
//			service.editar(partida, 1);
//		});
//
//		assertTrue(e.getMensagemErros().contains(alertaContaNulo()));
//	}
//
//	@Test
//	void deveRetornarErroPorLimiteNuloEmEditar() {
//		Partida partida = retornarPartida1();
//		partida.setLimite(null);
//
//		NegocioException e = assertThrows(NegocioException.class, () -> {
//			service.editar(partida, 1);
//		});
//
//		assertTrue(e.getMensagemErros().contains(alertaLimiteNulo()));
//	}
//
//	@Test
//	void deveRetornarErroPorIdLimiteNuloEmEditar() {
//		Partida partida = retornarPartida1();
//		partida.getLimite().setId(null);
//
//		NegocioException e = assertThrows(NegocioException.class, () -> {
//			service.editar(partida, 1);
//		});
//
//		assertTrue(e.getMensagemErros().contains(alertaLimiteNulo()));
//	}
//
//	@Test
//	void deveRetornarErroPorDataCriacaoNuloEmEditar() {
//		Partida partida = retornarPartida1();
//		partida.setData(null);
//
//		NegocioException e = assertThrows(NegocioException.class, () -> {
//			service.editar(partida, 1);
//		});
//
//		assertTrue(e.getMensagemErros().contains(alertaDataCriacaoNulo()));
//	}
//
//	@Test
//	void deveEditarPartida() throws NegocioException {
//		Partida partida = retornarPartida1();
//		partida.setData(LocalDateTime.now());
//
//		Conta contaPersist = retornarConta();
//		contaPersist.setId(1);
//
//		partida.setConta(contaPersist);
//
//		service.editar(partida, 1);
//	}
	
	private static Usuario retornarUsuario() {
		Usuario usuario = new Usuario();
		usuario.setId(1);
		usuario.setNome("Usuario Logado");
		
		return usuario;
	}

	private static Limite retornarLimite() {
		Limite novoLimite = new Limite();

		novoLimite.setId(1);
		novoLimite.setSmallBlind(new BigDecimal(0.5));
		novoLimite.setBigBlind(new BigDecimal(1));

		return novoLimite;
	}
	
	private static Partida retornarPartida1() {
		Partida partida = new Partida();
		partida.setId(1);
		partida.setQuantidadeMaosInicio(0);
		partida.setQuantidadeMaosFim(200);
		partida.setFichasIniciais(new BigDecimal(100.0));
		partida.setFichasFinais(new BigDecimal(200.03));
		partida.setDataInicio(LocalDateTime.of(2023, 05, 01, 0, 0));
		partida.setDataFim(LocalDateTime.of(2023, 05, 01, 01, 0));
		partida.setLimite(limite);
		partida.setUsuario(usuarioLogado);
		
		return partida;
	}

	private static Partida retornarPartida2() {
		Partida partida = new Partida();
		partida.setId(2);
		partida.setQuantidadeMaosInicio(200);
		partida.setQuantidadeMaosFim(400);
		partida.setFichasIniciais(new BigDecimal(200.13));
		partida.setFichasFinais(new BigDecimal(300.03));
		partida.setDataInicio(LocalDateTime.of(2023, 05, 01, 04, 0));
		partida.setDataFim(LocalDateTime.of(2023, 05, 01, 05, 0));
		partida.setLimite(limite);
		partida.setUsuario(usuarioLogado);
		
		return partida;
	}

	private static List<Partida> retornarPartidas() {
		List<Partida> partidasLista = new ArrayList<Partida>();
		partidasLista.add(partida1);
		partidasLista.add(partida2);
		
		return partidasLista;
	}
	
//	private Partida clonePartida(Partida partida) {
//		Partida partidaClone = new Partida();
//		partidaClone.setId(partida.getId());
//		partidaClone.setValor(partida.getValor());
//		partidaClone.setQuantidadeBigBlind(partida.getQuantidadeBigBlind());
//		partidaClone.setQuantidadeMaosInicio(partida.getQuantidadeMaosInicio());
//		partidaClone.setQuantidadeMaosFim(partida.getQuantidadeMaosFim());
//		partidaClone.setOnline(partida.getOnline());
//		partidaClone.setFichasIniciais(partida.getFichasIniciais());
//		partidaClone.setFichasFinais(partida.getFichasFinais());
//		partidaClone.setDataFim(partida.getDataFim());
//		partidaClone.setDataFim(partida);
//		partidaClone.setLimite(partida);
//		partidaClone.setUsuario(partida);
//		
//		return partidaClone;
//	}
}
