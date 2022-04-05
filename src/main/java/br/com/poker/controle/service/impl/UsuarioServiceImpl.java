package br.com.poker.controle.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.poker.controle.exceptions.NegocioException;
import br.com.poker.controle.models.Usuario;
import br.com.poker.controle.repository.UsuarioRepository;
import br.com.poker.controle.service.UsuarioService;
import br.com.poker.controle.utils.validadores.teste.ValidadorUsuario;

@Service("UsuarioService")
public class UsuarioServiceImpl implements UsuarioService {

	private UsuarioRepository repository;

	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private void setRepository(UsuarioRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<Usuario> buscar() {
		return repository.findAll();
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
		
		String email =  auth.getName();
		
		
		return buscarPorEmail(email);
	}
}
