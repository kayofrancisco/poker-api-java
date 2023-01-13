package br.com.poker.controle.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;

import br.com.poker.controle.exceptions.NegocioException;
import br.com.poker.controle.models.Partida;

public interface PartidaService {
	Page<Partida> buscar(Integer page, Integer size, Boolean buscaTotal);
	List<Partida> buscarPorIntervaloData(LocalDateTime dataMinima, LocalDateTime dataMaxima);
	Partida cadastrar(Partida partida) throws NegocioException;
	Partida editar(Partida partida, Integer id) throws NegocioException;
	void excluir(Integer id) throws NegocioException;
}
