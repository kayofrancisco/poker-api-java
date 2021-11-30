package br.com.poker.controle.service.impl;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.poker.controle.exceptions.NegocioException;
import br.com.poker.controle.models.Usuario;
import br.com.poker.controle.repository.UsuarioRepository;
import br.com.poker.controle.service.UsuarioService;
import br.com.poker.controle.utils.Utils;
import br.com.poker.controle.utils.validadores.teste.ValidadorUsuario;

@Service("UsuarioService")
public class UsuarioServiceImpl implements UsuarioService {

	private UsuarioRepository repository;

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
		usuario.setCpf(Utils.sanitizar(usuario.getCpf()).orElse(null));

		ValidadorUsuario validador = new ValidadorUsuario(usuario, repository, Boolean.TRUE, Boolean.TRUE);

		if (!validador.validar()) {
			throw new NegocioException(validador.getErros());
		}

		try {
			usuario.setSenha(Utils.gerarHashParaSenha(usuario.getSenha().getBytes()));
		} catch (NoSuchAlgorithmException e) {
			throw new NegocioException("Senha não pôde ser criptografada");
		}

		return repository.save(usuario);
	}

	@Override
	public Usuario editar(Usuario usuario, Integer id) throws NegocioException {
		Usuario usuarioBanco = buscarPorId(id)
				.orElseThrow(() -> new NegocioException("Usuário não encontrado para o id informado"));

		usuario.setCpf(Utils.sanitizar(usuario.getCpf()).orElse(null));

		Boolean validaCpfNoBanco = !usuario.getCpf().equals(usuarioBanco.getCpf());
		Boolean validaEmailNoBanco = !usuario.getEmail().equals(usuarioBanco.getEmail());
		

		ValidadorUsuario validador = new ValidadorUsuario(usuario, repository, validaCpfNoBanco, validaEmailNoBanco);

		if (!validador.validar()) {
			throw new NegocioException(validador.getErros());
		}
		
		usuarioBanco.setCpf(usuario.getCpf());
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
	public List<Usuario> buscarPorCpfOuEmail(String cpf, String email) {
		return repository.findByCpfOrEmail(cpf, email);
	}
}
