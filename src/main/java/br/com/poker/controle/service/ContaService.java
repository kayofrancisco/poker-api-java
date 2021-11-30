package br.com.poker.controle.service;

import java.util.List;

import br.com.poker.controle.exceptions.NegocioException;
import br.com.poker.controle.models.Conta;

public interface ContaService {
	List<Conta> buscar();
	Conta cadastrar(Conta conta) throws NegocioException;
	Conta editar(Conta conta, Integer id) throws NegocioException;
	void deletar(Integer id);
}
