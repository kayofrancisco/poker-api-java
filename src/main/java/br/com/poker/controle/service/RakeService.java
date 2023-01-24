package br.com.poker.controle.service;

import java.util.List;
import java.util.Optional;

import br.com.poker.controle.exceptions.NegocioException;
import br.com.poker.controle.models.Rake;

public interface RakeService {
	List<Rake> buscar();
	Optional<Rake> buscarPorId(Integer id);
	Rake cadastrar(Rake rake) throws NegocioException;
	Rake editar(Rake rake, Integer id) throws NegocioException;
	void deletar(Integer id);
}
