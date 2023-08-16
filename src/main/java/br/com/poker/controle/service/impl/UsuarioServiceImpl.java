package br.com.poker.controle.service.impl;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.poker.controle.exceptions.NegocioException;
import br.com.poker.controle.models.Usuario;
import br.com.poker.controle.repository.UsuarioRepository;
import br.com.poker.controle.service.UsuarioJwtService;
import br.com.poker.controle.service.UsuarioService;
import br.com.poker.controle.utils.Utils;
import br.com.poker.controle.utils.validadores.teste.ValidadorUsuario;

@Service("UsuarioService")
public class UsuarioServiceImpl implements UsuarioService {

	private UsuarioRepository repository;
	private UsuarioJwtService usuarioJwtService;
	
    private static final String CARACTERES_PERMITIDOS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_-+=<>?";

	@Autowired
	protected void setRepository(UsuarioRepository repository) {
		this.repository = repository;
	}

	@Autowired
	protected void setUsuarioJwtService(UsuarioJwtService usuarioJwtService) {
		this.usuarioJwtService = usuarioJwtService;
	}

	@Override
	public Page<Usuario> buscar(Integer page, Integer size) throws NegocioException {
		if (!usuarioJwtService.usuarioLogadoisAdm()) {
			throw new NegocioException("Você não tem permissão para esta ação");
		}

		Pageable pageable = PageRequest.of(page, size, Sort.by("criadoEm").descending());

		return repository.findAll(pageable);
	}

	@Override
	public Usuario cadastrar(Usuario usuario) throws NegocioException {
		ValidadorUsuario validador = new ValidadorUsuario(usuario, repository, Boolean.TRUE);

		if (!validador.validar()) {
			throw new NegocioException(validador.getErros());
		}

		return repository.save(usuario);
	}

	@Override
	public Usuario editar(Usuario usuario, Integer id) throws NegocioException {
		Usuario usuarioBanco = buscarPorId(id)
				.orElseThrow(() -> new NegocioException("Usuário não encontrado para o id informado"));

		Boolean validaEmailNoBanco = !usuarioBanco.getEmail().equals(usuario.getEmail());

		ValidadorUsuario validador = new ValidadorUsuario(usuario, repository, validaEmailNoBanco);

		if (!validador.validar()) {
			throw new NegocioException(validador.getErros());
		}

		usuarioBanco.setEmail(usuario.getEmail());
		usuarioBanco.setNome(usuario.getNome());

		return repository.save(usuarioBanco);
	}

	@Override
	public void deletar(Integer id) throws NegocioException {
		Usuario usuario = buscarPorId(id)
				.orElseThrow(() -> new NegocioException("Usuário não encontrado para o id informado"));

		usuario.setAtivo(false);

		repository.save(usuario);
	}

	@Override
	public Optional<Usuario> buscarPorId(Integer id) {
		return repository.findById(id);
	}

	@Override
	public Usuario buscarPorEmail(String email) {
		return repository.findByEmail(email).orElse(null);
	}

	@Override
	public Usuario recuperaUsuarioLogado() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		String email = auth.getName();

		return buscarPorEmail(email);
	}

	@Override
	public Usuario editarPerfilEAtivo(Usuario usuario, Integer id) throws NegocioException {
		if (!usuarioJwtService.usuarioLogadoisAdm()) {
			throw new NegocioException("Você não tem permissão para esta ação");
		}

		Usuario usuarioBanco = buscarPorId(id)
				.orElseThrow(() -> new NegocioException("Usuário não encontrado para o id informado"));

		usuarioBanco.setPerfil(usuario.getPerfil());
		usuarioBanco.setAtivo(usuario.getAtivo());

		return repository.save(usuarioBanco);
	}

	@Override
	public String resetaSenha(Integer id) throws NegocioException, NoSuchAlgorithmException {
		Usuario usuario = repository.findById(id).orElseThrow(() -> new NegocioException("Usuário não encontrado"));

		String novaSenha = gerarSenhaAleatoria();
		
		usuario.setSenha(Utils.encodeSenha(novaSenha));
		
		repository.save(usuario);
		
		return novaSenha;
		
	}
	
	private String gerarSenhaAleatoria() {
        SecureRandom random = new SecureRandom();
        StringBuilder senha = new StringBuilder(8);

        for (int i = 0; i < 8; i++) {
            int indice = random.nextInt(CARACTERES_PERMITIDOS.length());
            char caractereAleatorio = CARACTERES_PERMITIDOS.charAt(indice);
            senha.append(caractereAleatorio);
        }

        return senha.toString();
    }

	@Override
	public String resetaSenhaUsuarioLogado() throws NegocioException, NoSuchAlgorithmException {
		Usuario usuario = recuperaUsuarioLogado();
		
		String novaSenha = gerarSenhaAleatoria();
		
		usuario.setSenha(Utils.encodeSenha(novaSenha));
		
		repository.save(usuario);
		
		return novaSenha;
		
	}
}
