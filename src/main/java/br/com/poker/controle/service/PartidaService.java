package br.com.poker.controle.service;

import java.time.LocalDateTime;
import java.util.List;

import br.com.poker.controle.exceptions.NegocioException;
import br.com.poker.controle.models.Partida;

public interface PartidaService {
	List<Partida> buscar();
	List<Partida> buscarPorIntervaloData(LocalDateTime dataMinima, LocalDateTime dataMaxima);
	Partida cadastrar(Partida partida) throws NegocioException;
	Partida editar(Partida partida, Integer id) throws NegocioException;
	void excluir(Integer id) throws NegocioException;
}
