package br.com.poker.controle.service;

import java.util.List;
import java.util.Optional;

import br.com.poker.controle.exceptions.NegocioException;
import br.com.poker.controle.models.Usuario;

public interface UsuarioService {
	List<Usuario> buscar();
	Optional<Usuario> buscarPorId(Integer id);
	List<Usuario> buscarPorEmail(String email);
	Usuario cadastrar(Usuario usuario) throws NegocioException;
	Usuario editar(Usuario usuario, Integer id) throws NegocioException;
	void deletar(Integer id) throws NegocioException;
}
