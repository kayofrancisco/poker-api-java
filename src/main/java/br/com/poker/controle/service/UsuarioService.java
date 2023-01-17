package br.com.poker.controle.service;

import java.util.Optional;

import org.springframework.data.domain.Page;

import br.com.poker.controle.exceptions.NegocioException;
import br.com.poker.controle.models.Usuario;

public interface UsuarioService {
	Page<Usuario> buscar(Integer page, Integer size) throws NegocioException;

	Optional<Usuario> buscarPorId(Integer id);

	Usuario buscarPorEmail(String email);

	Usuario cadastrar(Usuario usuario) throws NegocioException;

	Usuario editar(Usuario usuario, Integer id) throws NegocioException;

	Usuario editarPerfilEAtivo(Usuario usuario, Integer id) throws NegocioException;

	void deletar(Integer id) throws NegocioException;

	Usuario recuperaUsuarioLogado();
}
