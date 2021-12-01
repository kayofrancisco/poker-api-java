package br.com.poker.controle.service;

import java.util.List;
import java.util.Optional;

import br.com.poker.controle.exceptions.NegocioException;
import br.com.poker.controle.models.Conta;

public interface ContaService {
	List<Conta> buscar();
	Optional<Conta> buscarPorId(Integer id);
	Conta cadastrar(Conta conta) throws NegocioException;
	Conta editar(Conta conta, Integer id) throws NegocioException;
	void deletar(Integer id);
}
