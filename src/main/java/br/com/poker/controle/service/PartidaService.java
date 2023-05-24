package br.com.poker.controle.service;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;

import br.com.poker.controle.exceptions.NegocioException;
import br.com.poker.controle.models.Partida;
import br.com.poker.controle.models.dto.DadosResumidosDTO;

public interface PartidaService {
	Page<Partida> buscar(Integer page, Integer size, Boolean buscaTotal);
	DadosResumidosDTO buscarPorIntervaloData(LocalDateTime dataMinima, LocalDateTime dataMaxima) throws NegocioException;
	Partida cadastrar(Partida partida) throws NegocioException;
	Partida editar(Partida partida, Integer id) throws NegocioException;
	void excluir(Integer id) throws NegocioException;
	Partida buscarPrimeiraPartidaRegistrada();
}
