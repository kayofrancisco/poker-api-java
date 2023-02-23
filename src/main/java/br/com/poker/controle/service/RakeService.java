package br.com.poker.controle.service;

import java.util.Optional;

import org.springframework.data.domain.Page;

import br.com.poker.controle.exceptions.NegocioException;
import br.com.poker.controle.models.Rake;

public interface RakeService {
	Page<Rake> buscar(Integer page, Integer size, Boolean buscaTotal);
	Optional<Rake> buscarPorId(Integer id);
	Rake cadastrar(Rake rake) throws NegocioException;
	Rake editar(Rake rake, Integer id) throws NegocioException;
	void deletar(Integer id);
}
