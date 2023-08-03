package br.com.poker.controle.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.com.poker.controle.models.Partida;
import br.com.poker.controle.models.Rake;
import br.com.poker.controle.models.dto.GraficoAcumuladoDTO;
import br.com.poker.controle.models.dto.GraficoDTO;
import br.com.poker.controle.repository.PartidaRepository;
import br.com.poker.controle.service.GraficoService;
import br.com.poker.controle.service.PartidaService;
import br.com.poker.controle.service.RakeService;
import br.com.poker.controle.utils.UtilMes;

@Service("GraficoService")
public class GraficoServiceImpl implements GraficoService {

	private PartidaService partidaService;
	private RakeService rakeService;
	private PartidaRepository partidaRepository;

	@Autowired
	protected void setRakeService(RakeService rakeService) {
		this.rakeService = rakeService;
	}

	@Autowired
	protected void setPartidaService(PartidaService partidaService) {
		this.partidaService = partidaService;
	}

	@Autowired
	protected void setPartidaRepository(PartidaRepository partidaRepository) {
		this.partidaRepository = partidaRepository;
	}

	@Override
	public GraficoDTO buscarDadosPartidas() {
		Page<Partida> partidas = partidaService.buscar(0, 25, Boolean.FALSE);

		List<LocalDateTime> labels = partidas.getContent().stream().map(Partida::getDataInicio)
				.collect(Collectors.toList());

		List<BigDecimal> dados = partidas.getContent().stream()
				.map(item -> item.getFichasFinais() == null || item.getFichasIniciais() == null ? BigDecimal.ZERO
						: item.getFichasFinais().subtract(item.getFichasIniciais()))
				.collect(Collectors.toList());

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

	@Override
	public GraficoAcumuladoDTO buscarDadosAcumulados() {
		LocalDateTime dataAgora = LocalDateTime.now();

		Partida primeiraPartida = partidaService.buscarPrimeiraPartidaRegistrada();

		Integer ano = primeiraPartida.getDataInicio().getYear();
		Integer mes = primeiraPartida.getDataInicio().getMonthValue();

		LocalDateTime dataInicio = LocalDateTime.of(ano, mes, 1, 0, 0, 0);
		LocalDateTime dataFim = dataInicio.plusMonths(1);

		Long diferencaMeses = ChronoUnit.MONTHS.between(dataInicio, dataAgora);

		List<String> labels = new ArrayList<String>();
		List<BigDecimal> valores = new ArrayList<BigDecimal>();

		for (int i = 0; i < diferencaMeses + 1; i++) {
			List<Partida> partidas = partidaRepository
					.findAllByDataInicioBetweenAndUsuarioIdAndFichasFinaisNotNull(dataInicio, dataFim, 1);

			if (!partidas.isEmpty()) {
				BigDecimal lucro = partidas.stream()
						.map(item -> item.getFichasFinais() != null
								? item.getFichasFinais().subtract(item.getFichasIniciais())
								: BigDecimal.ZERO)
						.reduce(BigDecimal.ZERO, BigDecimal::add);
				
				labels.add(
						UtilMes.retornaMesPorNumero(dataInicio.getMonthValue()) + " - " + dataInicio.getYear()
				);
				if (valores.isEmpty()) {
					valores.add(lucro);
				} else {
					valores.add(valores.get(i - 1).add(lucro));
				}
			}

			dataInicio = dataInicio.plusMonths(1);
			dataFim = dataFim.plusMonths(1);
		}

		GraficoAcumuladoDTO dto = new GraficoAcumuladoDTO();
		dto.setLabels(labels);
		dto.setValores(valores);
		
		return dto;
	}

	@Override
	public GraficoDTO buscarDadosAcumuladosComRake() {
		// TODO Auto-generated method stub
		return null;
	}

}
