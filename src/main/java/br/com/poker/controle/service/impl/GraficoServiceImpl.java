package br.com.poker.controle.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.com.poker.controle.models.Partida;
import br.com.poker.controle.models.Rake;
import br.com.poker.controle.models.dto.GraficoDTO;
import br.com.poker.controle.service.GraficoService;
import br.com.poker.controle.service.PartidaService;
import br.com.poker.controle.service.RakeService;

@Service("GraficoService")
public class GraficoServiceImpl implements GraficoService {

	private PartidaService partidaService;
	private RakeService rakeService;

	@Autowired
	protected void setRakeService(RakeService rakeService) {
		this.rakeService = rakeService;
	}

	@Autowired
	protected void setPartidaService(PartidaService partidaService) {
		this.partidaService = partidaService;
	}

	@Override
	public GraficoDTO buscarDadosPartidas() {
		Page<Partida> partidas = partidaService.buscar(0, 25, Boolean.FALSE);
		
		List<LocalDateTime> labels = partidas.getContent().stream().map(Partida::getDataInicio).collect(Collectors.toList());
		List<BigDecimal> dados = partidas.getContent().stream().map(item -> {
			BigDecimal fichasFinais = item.getFichasFinais().multiply(item.getLimite().getBigBlind());
			BigDecimal fichasIniciais = item.getFichasIniciais().multiply(item.getLimite().getBigBlind());
			
			return fichasFinais.subtract(fichasIniciais);
		}).collect(Collectors.toList());
		
		GraficoDTO dto = new GraficoDTO();
		dto.setLabels(labels);
		dto.setValores(dados);
		
		return dto;
	}

	@Override
	public GraficoDTO buscarDadosRakes() {
		Page<Rake> rakes = rakeService.buscar(0, 25, Boolean.FALSE);
		
		List<LocalDateTime> labels = rakes.getContent().stream().map(Rake::getCriadoEm).collect(Collectors.toList());
		List<BigDecimal> dados = rakes.getContent().stream().map(Rake::getValor).collect(Collectors.toList());
		
		GraficoDTO dto = new GraficoDTO();
		dto.setLabels(labels);
		dto.setValores(dados);
		
		return dto;
	}
	
}
