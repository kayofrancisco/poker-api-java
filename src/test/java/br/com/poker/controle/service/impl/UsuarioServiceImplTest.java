package br.com.poker.controle.service.impl;

import static br.com.poker.controle.utils.validadores.alertas.AlertasUsuario.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.lenient;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.poker.controle.exceptions.NegocioException;
import br.com.poker.controle.models.Perfil;
import br.com.poker.controle.models.Usuario;
import br.com.poker.controle.repository.UsuarioRepository;
import br.com.poker.controle.service.UsuarioJwtService;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceImplTest {
	static UsuarioRepository repository;
	static UsuarioJwtService usuarioJwtService;
	
	static UsuarioServiceImpl usuarioService;
	
	static Usuario usuarioAdm;
	static Usuario usuarioComum;

	@BeforeAll
	static void setup() {
		repository = Mockito.mock(UsuarioRepository.class);
		usuarioJwtService = Mockito.mock(UsuarioJwtService.class);
		
		usuarioService = new UsuarioServiceImpl();
		usuarioService.setRepository(repository);
		usuarioService.setUsuarioJwtService(usuarioJwtService);
		
		usuarioAdm = retornaUsuarioAdm();
		usuarioComum = retornaUsuarioComum();
		
		lenient().when(usuarioJwtService.usuarioLogadoisAdm()).thenReturn(Boolean.FALSE);
		lenient().when(repository.findByEmail(usuarioAdm.getEmail())).thenReturn(Optional.of(usuarioAdm));
		lenient().when(repository.findByEmail(usuarioComum.getEmail())).thenReturn(Optional.ofNullable(null));
		lenient().when(repository.findById(usuarioComum.getId())).thenReturn(Optional.of(usuarioComum));
		lenient().when(repository.findById(usuarioAdm.getId())).thenReturn(Optional.of(usuarioAdm));
		lenient().when(repository.findById(-1)).thenReturn(Optional.ofNullable(null));
//		lenient().when(usuarioService.recuperaUsuarioLogado()).thenReturn(usuarioAdm);
	}

	@Test
	void deveRetornarFalhaPorUsuarioNaoAdm() {
		NegocioException e = assertThrows(NegocioException.class, () -> {
			usuarioService.buscar(0, 10);
		});

		assertTrue(e.getMensagemErros().contains("Você não tem permissão para esta ação"));
	}
	
	@Test
	void deveRetornarFalhaPorUsuarioSemNome() {
		Usuario semNome = retornaUsuarioComOsDados(usuarioComum);
		semNome.setNome(null);
		
		NegocioException e = assertThrows(NegocioException.class, () -> {
			usuarioService.cadastrar(semNome);
		});

		assertTrue(e.getMensagemErros().contains(alertaNomeNuloOuVazio()));
	}
	
	@Test
	void deveRetornarFalhaPorUsuarioSemEmail() {
		Usuario semEmail = retornaUsuarioComOsDados(usuarioComum);
		semEmail.setEmail(null);
		
		NegocioException e = assertThrows(NegocioException.class, () -> {
			usuarioService.cadastrar(semEmail);
		});

		assertTrue(e.getMensagemErros().contains(alertaEmailNuloOuVazio()));
	}
	
	@Test
	void deveRetornarFalhaPorUsuarioSemSenha() {
		Usuario semSenha = retornaUsuarioComOsDados(usuarioComum);
		semSenha.setSenha(null);
		
		NegocioException e = assertThrows(NegocioException.class, () -> {
			usuarioService.cadastrar(semSenha);
		});

		assertTrue(e.getMensagemErros().contains(alertaSenhaNuloOuVazio()));
	}
	
	@Test
	void deveRetornarFalhaPorUsuarioSemConfirmacaoSenha() {
		Usuario semConfimracaoSenha = retornaUsuarioComOsDados(usuarioComum);
		semConfimracaoSenha.setConfirmaSenha(null);
		
		NegocioException e = assertThrows(NegocioException.class, () -> {
			usuarioService.cadastrar(semConfimracaoSenha);
		});

		assertTrue(e.getMensagemErros().contains(alertaConfirmacaoSenhaNuloOuVazio()));
	}
	
//	@Test
	void deveRetornarFalhaPorUsuarioSemPerfil() {
		Usuario semPerfil = retornaUsuarioComOsDados(usuarioComum);
		semPerfil.setPerfil(null);
		
		NegocioException e = assertThrows(NegocioException.class, () -> {
			usuarioService.cadastrar(semPerfil);
		});

		assertTrue(e.getMensagemErros().contains(alertaUsuarioSemPerfil()));
	}
	
//	@Test
	void deveRetornarFalhaPorUsuarioSemIdPerfil() {
		Usuario semIdPerfil = retornaUsuarioComOsDados(usuarioComum);
		
		Perfil perfil = semIdPerfil.getPerfil();
		perfil.setId(null);
		
		semIdPerfil.setPerfil(perfil);
		
		NegocioException e = assertThrows(NegocioException.class, () -> {
			usuarioService.cadastrar(semIdPerfil);
		});

		assertTrue(e.getMensagemErros().contains(alertaUsuarioSemPerfil()));
	}
	
	@Test
	void deveRetornarFalhaPorEmailInvalido() {
		Usuario emailInvalido = retornaUsuarioComOsDados(usuarioComum);
		emailInvalido.setPerfil(Perfil.perfilComum());
		emailInvalido.setEmail("usuarioInvalido");
		
		NegocioException e = assertThrows(NegocioException.class, () -> {
			usuarioService.cadastrar(emailInvalido);
		});

		assertTrue(e.getMensagemErros().contains(alertaEmailInvalido()));
	}
	
	@Test
	void deveRetornarFalhaPorEmailExistente() {
		NegocioException e = assertThrows(NegocioException.class, () -> {
			usuarioService.cadastrar(usuarioAdm);
		});

		assertTrue(e.getMensagemErros().contains(alertaUsuarioExistenteComEmail()));
	}
	
	@Test
	void deveRetornarFalhaPorSenhasDiferentes() {
		Usuario usuarioInvalido = retornaUsuarioComOsDados(usuarioComum);
		usuarioInvalido.setConfirmaSenha("123325");
		usuarioInvalido.setPerfil(Perfil.perfilComum());
		
		NegocioException e = assertThrows(NegocioException.class, () -> {
			usuarioService.cadastrar(usuarioInvalido);
		});

		assertTrue(e.getMensagemErros().contains(alertaUsuarioComSenhasDiferentes()));
	}
	
	@Test
	void deveSalvarUsuario() {
		Usuario usuario = retornaUsuarioComOsDados(usuarioComum);
		usuario.setPerfil(Perfil.perfilComum());
		
		usuario.setEmail("emailNovo@hotmail.com");

		try {
			usuarioService.cadastrar(usuario);
		} catch (Exception e) {
			fail();
		}
		
	}
	
	@Test
	void deveRetornarFalhaPorIdNaoEncontradoNoEditar() {
		NegocioException e = assertThrows(NegocioException.class, () -> {
			usuarioService.editar(usuarioComum, -1);
		});

		assertTrue(e.getMensagemErros().contains("Usuário não encontrado para o id informado"));
	}
	
	@Test
	void deveRetornarFalhaPorSemNome() {
		Usuario usuario = retornaUsuarioComOsDados(usuarioAdm);
		
		usuario.setNome(null);
		
		NegocioException e = assertThrows(NegocioException.class, () -> {
			usuarioService.editar(usuario, usuarioAdm.getId());
		});

		assertTrue(e.getMensagemErros().contains(alertaNomeNuloOuVazio()));
	}
	
	@Test
	void deveEditarUsuario() {
		Usuario usuario = retornaUsuarioComOsDados(usuarioAdm);
		usuario.setNome("nome123");

		try {
			usuarioService.editar(usuario, usuario.getId());
		} catch (Exception e) {
			fail();
		}
		
	}
	
	@Test
	void deveRetornarFalhaPorIdNaoEncontradoNoRemover() {
		NegocioException e = assertThrows(NegocioException.class, () -> {
			usuarioService.deletar(-1);
		});

		assertTrue(e.getMensagemErros().contains("Usuário não encontrado para o id informado"));
	}
	
	@Test
	void deveRemoverUsuario() {
		try {
			usuarioService.deletar(usuarioAdm.getId());
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	void deveBuscarPorEmail() {
		Usuario usuario = usuarioService.buscarPorEmail(usuarioAdm.getEmail());
		
		assertTrue(usuario.getId().equals(usuarioAdm.getId()));
	}
	
//	@Test
//	void deveRetornarUsuarioLogado() {
//		Usuario usuario = usuarioService.recuperaUsuarioLogado();
//		
//		assertTrue(usuario.getId().equals(usuarioAdm.getId()));
//	}
	
	private static Usuario retornaUsuarioAdm() {
		Usuario usuario = new Usuario();
		usuario.setId(1);
		usuario.setPerfil(Perfil.perfilAdm());
		usuario.setConfirmaSenha("123");
		usuario.setCriadoEm(LocalDateTime.now());
		usuario.setEmail("emailTeste1@teste.com");
		usuario.setNome("usuario teste 1");
		usuario.setSenha("123");
		usuario.setUltimaAtualizacao(LocalDateTime.now());
		
		return usuario;
	}
	
	private static Usuario retornaUsuarioComum() {
		Usuario usuario = new Usuario();
		usuario.setId(2);
		usuario.setPerfil(Perfil.perfilComum());
		usuario.setConfirmaSenha("123");
		usuario.setCriadoEm(LocalDateTime.now());
		usuario.setEmail("emailTeste2@teste.com");
		usuario.setNome("usuario teste 2");
		usuario.setSenha("123");
		usuario.setUltimaAtualizacao(LocalDateTime.now());
		
		return usuario;
	}
	
	private static Usuario retornaUsuarioComOsDados(Usuario usuarioClone) {
		Usuario usuarioNovo = new Usuario();
		
		usuarioNovo.setConfirmaSenha(usuarioClone.getConfirmaSenha());
		usuarioNovo.setCriadoEm(usuarioClone.getCriadoEm());
		usuarioNovo.setEmail(usuarioClone.getEmail());
		usuarioNovo.setId(usuarioClone.getId());
		usuarioNovo.setNome(usuarioClone.getNome());
		usuarioNovo.setPerfil(usuarioClone.getPerfil());
		usuarioNovo.setSenha(usuarioClone.getSenha());
		usuarioNovo.setUltimaAtualizacao(usuarioClone.getUltimaAtualizacao());
		
		return usuarioNovo;
	}


//	@Test
//	void deveRetornarCadastrarClube() {
//		Clube clubeValido = retornarClube1();
//
//		try {
//			clubeService.cadastrar(clubeValido);
//		} catch (Exception e) {
//			fail();
//		}
//	}
}
