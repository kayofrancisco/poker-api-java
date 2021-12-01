package br.com.poker.controle.service;

import java.util.List;
import java.util.Optional;

import br.com.poker.controle.exceptions.NegocioException;
import br.com.poker.controle.models.Clube;

public interface ClubeService {
	List<Clube> buscar();
	Optional<Clube> buscarPorId(Integer id);
	Clube cadastrar(Clube clube) throws NegocioException;
	Clube editar(Clube clube, Integer id) throws NegocioException;
	void deletar(Integer id);
}
