package br.com.poker.controle.service;

import java.util.List;

import br.com.poker.controle.exceptions.NegocioException;
import br.com.poker.controle.models.Clube;

public interface ClubeService {
	List<Clube> buscar();
	Clube cadastrar(Clube clube) throws NegocioException;
	Clube editar(Clube clube, Integer id) throws NegocioException;
	void deletar(Integer id);
}
