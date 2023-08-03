package br.com.poker.controle.service;

import br.com.poker.controle.models.dto.GraficoAcumuladoDTO;
import br.com.poker.controle.models.dto.GraficoDTO;

public interface GraficoService {
	GraficoDTO buscarDadosPartidas();
	GraficoDTO buscarDadosRakes();
	GraficoAcumuladoDTO buscarDadosAcumulados();
}
