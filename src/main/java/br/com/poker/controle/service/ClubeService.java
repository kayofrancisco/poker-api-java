package br.com.poker.controle.service;

import java.util.List;

import br.com.poker.controle.models.Clube;

public interface ClubeService {
	List<Clube> buscar();
	Clube cadastrar(Clube clube);
	Clube editar(Clube clube, Integer id);
}
