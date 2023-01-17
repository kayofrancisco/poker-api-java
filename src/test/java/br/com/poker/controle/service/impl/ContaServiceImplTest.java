package br.com.poker.controle.service.impl;

import static br.com.poker.controle.utils.validadores.alertas.AlertasConta.alertaNickExistente;
import static br.com.poker.controle.utils.validadores.alertas.AlertasConta.alertaNickNuloOuVazio;
import static br.com.poker.controle.utils.validadores.alertas.AlertasConta.alertaUsuarioNulo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.poker.controle.exceptions.NegocioException;
import br.com.poker.controle.models.Conta;
import br.com.poker.controle.models.Usuario;
import br.com.poker.controle.repository.ContaRepository;
import br.com.poker.controle.service.UsuarioService;

@ExtendWith(MockitoExtension.class)
public class ContaServiceImplTest {
	static ContaServiceImpl service;
	static UsuarioService usuarioService;
	
	static ContaRepository repository;
	
	static Usuario usuario;
	static Conta conta1;
	static Conta conta2;
	static List<Conta> contas;
	
	@BeforeAll
	static void setup() {
		repository = Mockito.mock(ContaRepository.class);
		usuarioService = Mockito.mock(UsuarioService.class);

		service = new ContaServiceImpl();
		service.setRepository(repository);
		service.setUsuarioService(usuarioService);
		
		usuario = retornarUsuario();
		
		conta1 = retornarConta1();
		conta2 = retornarConta2();
		contas = retonarListaContas();
		
		lenient().when(usuarioService.recuperaUsuarioLogado()).thenReturn(usuario);
		lenient().when(service.buscar()).thenReturn(contas);
		lenient().when(repository.findByNick(conta1.getNick())).thenReturn(Optional.of(conta1));
		lenient().when(service.buscarPorId(conta1.getId())).thenReturn(Optional.of(conta1));
		lenient().when(service.buscarPorId(conta2.getId())).thenReturn(Optional.of(conta2));
		lenient().when(service.buscarPorId(3)).thenReturn(Optional.ofNullable(null));
	}
	
	@Test
	void deveRetornarContas() {
		List<Conta> contasRetornadas = service.buscar();
		
		assertEquals(contas, contasRetornadas);
	}
	
	@Test
	void deveRetornarErroPorNickNulo() {
		Conta contaValida = retornarConta1();
		
		contaValida.setNick(null);
		
		NegocioException e = assertThrows(NegocioException.class, () -> {
			service.cadastrar(contaValida);
		});

		assertTrue(e.getMensagemErros().contains(alertaNickNuloOuVazio()));
	}
	
//	@Test
	void deveRetornarErroPorUsuarioNulo() {
		Conta contaValida = retornarConta1();
		
		contaValida.setUsuario(null);
		
		NegocioException e = assertThrows(NegocioException.class, () -> {
			service.cadastrar(contaValida);
		});

		assertTrue(e.getMensagemErros().contains(alertaUsuarioNulo()));
	}
	
//	@Test
	void deveRetornarErroPorUsuarioIdNulo() {
		Conta contaValida = retornarConta1();
		
		Usuario usuarioValido = retornarUsuario();
		usuarioValido.setId(null);
		
		contaValida.setUsuario(usuarioValido);
		contaValida.setNick("NIck não utilizado");
		
		NegocioException e = assertThrows(NegocioException.class, () -> {
			service.cadastrar(contaValida);
		});

		assertTrue(e.getMensagemErros().contains(alertaUsuarioNulo()));
	}
	
	@Test
	void deveRetornarErroPorNickExistente() {
		Conta contaValida = retornarConta1();
		
		NegocioException e = assertThrows(NegocioException.class, () -> {
			service.cadastrar(contaValida);
		});

		assertTrue(e.getMensagemErros().contains(alertaNickExistente()));
	}
	
	@Test
	void deveCadastrarConta() {
		Conta contaNova = new Conta();
		contaNova.setNick("Nova Conta 1");
		contaNova.setUsuario(usuario);
		
		try {
			service.cadastrar(contaNova);
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	void deveRetornarErroPorIdNaoEncontrado() {
		Conta contaValida = retornarConta1();
		
		NegocioException e = assertThrows(NegocioException.class, () -> {
			service.editar(contaValida, 3);
		});

		assertTrue(e.getMensagemErros().contains("Conta não encontrada para o id informado"));
	}
	
	@Test
	void deveRetornarErroPorNickNuloEmEditar() {
		Conta contaValida = retornarConta1();
		
		contaValida.setNick(null);
		
		NegocioException e = assertThrows(NegocioException.class, () -> {
			service.editar(contaValida, conta1.getId());
		});

		assertTrue(e.getMensagemErros().contains(alertaNickNuloOuVazio()));
	}
	
	@Test
	void deveRetornarErroPorUsuarioNuloEmEditar() {
		Conta contaValida = retornarConta1();
		
		contaValida.setUsuario(null);
		
		NegocioException e = assertThrows(NegocioException.class, () -> {
			service.editar(contaValida, contaValida.getId());
		});

		assertTrue(e.getMensagemErros().contains(alertaUsuarioNulo()));
	}
	
	@Test
	void deveRetornarErroPorUsuarioIdNuloEmEditar() {
		Conta contaValida = retornarConta1();
		
		Usuario usuarioValido = retornarUsuario();
		usuarioValido.setId(null);
		
		contaValida.setUsuario(usuarioValido);
		
		NegocioException e = assertThrows(NegocioException.class, () -> {
			service.editar(contaValida, contaValida.getId());
		});

		assertTrue(e.getMensagemErros().contains(alertaUsuarioNulo()));
	}
	
	@Test
	void deveEditarClube() {
		Conta contaValida = retornarConta2();
		
		contaValida.setNick("Novo nick 2");
		
		try {
			service.editar(contaValida, 2);
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	void deveDeletarConta() {
		try {
			service.deletar(conta2.getId());
		} catch (Exception e) {
			fail();
		}
	}
	
	private static Usuario retornarUsuario() {
		Usuario usuario1 = new Usuario();
		usuario1.setId(1);
		
		return usuario1;
	}
	
	private static Conta retornarConta1() {
		return new Conta(1, "Conta 1", usuario);
	}

	private static Conta retornarConta2() {
		return new Conta(2, "Conta 2", usuario);
	}
	
	private static List<Conta> retonarListaContas() {
		List<Conta> contasLista = new ArrayList<Conta>();
		contasLista.add(conta1);
		contasLista.add(conta2);
		
		return contasLista;
	}
}
