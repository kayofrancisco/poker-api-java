package br.com.poker.controle.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.com.poker.controle.models.Partida;
import br.com.poker.controle.models.Rake;
import br.com.poker.controle.models.Usuario;
import br.com.poker.controle.models.dto.GraficoAcumuladoDTO;
import br.com.poker.controle.models.dto.GraficoAcumuladoMaosDTO;
import br.com.poker.controle.models.dto.GraficoDTO;
import br.com.poker.controle.repository.PartidaRepository;
import br.com.poker.controle.repository.RakeRepository;
import br.com.poker.controle.service.GraficoService;
import br.com.poker.controle.service.PartidaService;
import br.com.poker.controle.service.RakeService;
import br.com.poker.controle.service.UsuarioService;
import br.com.poker.controle.utils.UtilMes;

@Service("GraficoService")
public class GraficoServiceImpl implements GraficoService {

	private PartidaService partidaService;
	private RakeService rakeService;
	private UsuarioService usuarioService;

	private PartidaRepository partidaRepository;
	private RakeRepository rakeRepository;

	@Autowired
	protected void setRakeService(RakeService rakeService) {
		this.rakeService = rakeService;
	}

	@Autowired
	protected void setPartidaService(PartidaService partidaService) {
		this.partidaService = partidaService;
	}

	@Autowired
	protected void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	@Autowired
	protected void setPartidaRepository(PartidaRepository partidaRepository) {
		this.partidaRepository = partidaRepository;
	}

	@Autowired
	protected void setRakeRepository(RakeRepository rakeRepository) {
		this.rakeRepository = rakeRepository;
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
		Usuario usuario = usuarioService.recuperaUsuarioLogado();

		LocalDateTime dataAgora = LocalDateTime.now();

		Partida primeiraPartida = partidaService.buscarPrimeiraPartidaRegistrada();

		LocalDateTime dataInicio = LocalDateTime.of(primeiraPartida.getDataInicio().getYear(),
				primeiraPartida.getDataInicio().getMonthValue(), 1, 0, 0, 0);
		LocalDateTime dataFim = dataInicio.plusMonths(1);

		Long diferencaMeses = ChronoUnit.MONTHS.between(dataInicio, dataAgora);

		List<String> labels = new ArrayList<String>();
		List<BigDecimal> valoresMensais = new ArrayList<BigDecimal>();
		List<BigDecimal> valoresAcumulados = new ArrayList<BigDecimal>();
		List<BigDecimal> valoresAcumuladosComRake = new ArrayList<BigDecimal>();

		BigDecimal acumulado = BigDecimal.ZERO;
		BigDecimal acumuladoComRake = BigDecimal.ZERO;

		for (int i = 0; i < diferencaMeses + 1; i++) {
			BigDecimal lucroPartidas = BigDecimal.ZERO;
			BigDecimal lucroRakes = BigDecimal.ZERO;

			lucroPartidas = retornaAcumuladoDasPartidas(dataInicio, dataFim, usuario);
			lucroRakes = retornaAcumuladoDosRakes(dataInicio, dataFim, usuario);

			if (valoresMensais.isEmpty()) {
				acumulado = lucroPartidas;
				valoresMensais.add(lucroPartidas);
				valoresAcumulados.add(acumulado);
			} else {
				acumulado = valoresMensais.get(i - 1).add(lucroPartidas);
				valoresMensais.add(lucroPartidas);
				valoresAcumulados.add(valoresMensais.stream().reduce(BigDecimal.ZERO, BigDecimal::add));
			}

			if (valoresAcumuladosComRake.isEmpty()) {
				acumuladoComRake = acumulado;
				valoresAcumuladosComRake.add(acumuladoComRake.add(lucroRakes));
			} else {
				acumuladoComRake = valoresAcumuladosComRake.get(i - 1);
				BigDecimal acumuladoDoMes = lucroPartidas.add(lucroRakes);
				valoresAcumuladosComRake.add(acumuladoComRake.add(acumuladoDoMes));
			}

			labels.add(UtilMes.retornaMesPorNumero(dataInicio.getMonthValue()) + " - " + dataInicio.getYear());
			dataInicio = dataInicio.plusMonths(1);
			dataFim = dataFim.plusMonths(1);
		}

		GraficoAcumuladoDTO dto = new GraficoAcumuladoDTO();
		dto.setLabels(labels);
		dto.setValores(Arrays.asList(valoresMensais, valoresAcumulados, valoresAcumuladosComRake));

		return dto;
	}

	private BigDecimal retornaAcumuladoDasPartidas(LocalDateTime inicio, LocalDateTime fim, Usuario usuario) {
		List<Partida> partidas = partidaRepository.findAllByDataInicioBetweenAndUsuarioIdAndFichasFinaisNotNull(inicio,
				fim, usuario.getId());

		if (partidas.isEmpty()) {
			return BigDecimal.ZERO;
		}

		return partidas.stream()
				.map(item -> item.getFichasFinais() != null ? item.getFichasFinais().subtract(item.getFichasIniciais())
						: BigDecimal.ZERO)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	private BigDecimal retornaAcumuladoDosRakes(LocalDateTime inicio, LocalDateTime fim, Usuario usuario) {
		List<Rake> rakes = rakeRepository.findAllByCriadoEmBetweenAndUsuarioId(inicio, fim, usuario.getId());

		if (rakes.isEmpty()) {
			return BigDecimal.ZERO;
		}

		return rakes.stream().map(item -> item.getValor()).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	private Integer retornaAcumuladoMaosDasPartidas(LocalDateTime inicio, LocalDateTime fim, Usuario usuario) {
		List<Partida> partidas = partidaRepository.findAllByDataInicioBetweenAndUsuarioIdAndFichasFinaisNotNull(inicio,
				fim, usuario.getId());

		if (partidas.isEmpty()) {
			return 0;
		}

		return partidas.stream()
				.map(item -> item.getQuantidadeMaosFim() != null
						? item.getQuantidadeMaosFim() - item.getQuantidadeMaosInicio()
						: 0)
				.reduce(0, Integer::sum);
	}

	@Override
	public GraficoAcumuladoMaosDTO buscarMaosAcumuladas() {
		Usuario usuario = usuarioService.recuperaUsuarioLogado();

		LocalDateTime dataAgora = LocalDateTime.now();

		Partida primeiraPartida = partidaService.buscarPrimeiraPartidaRegistrada();

		LocalDateTime dataInicio = LocalDateTime.of(primeiraPartida.getDataInicio().getYear(),
				primeiraPartida.getDataInicio().getMonthValue(), 1, 0, 0, 0);
		LocalDateTime dataFim = dataInicio.plusMonths(1);

		Long diferencaMeses = ChronoUnit.MONTHS.between(dataInicio, dataAgora);

		List<String> labels = new ArrayList<String>();
		List<Integer> maosMensais = new ArrayList<Integer>();
		List<Integer> maosAcumuladas = new ArrayList<Integer>();

		Integer acumulado = 0;

		for (int i = 0; i < diferencaMeses + 1; i++) {
			Integer maos = retornaAcumuladoMaosDasPartidas(dataInicio, dataFim, usuario);

			labels.add(UtilMes.retornaMesPorNumero(dataInicio.getMonthValue()) + " - " + dataInicio.getYear());
			maosMensais.add(maos);
			acumulado = acumulado + maos;
			maosAcumuladas.add(acumulado);

			dataInicio = dataInicio.plusMonths(1);
			dataFim = dataFim.plusMonths(1);
		}

		GraficoAcumuladoMaosDTO dto = new GraficoAcumuladoMaosDTO();
		dto.setLabels(labels);
		dto.setValores(Arrays.asList(maosMensais, maosAcumuladas));

		return dto;
	}

	@Override
	public GraficoAcumuladoDTO buscarRakesAcumuladas() {
		Usuario usuario = usuarioService.recuperaUsuarioLogado();

		LocalDateTime dataAgora = LocalDateTime.now();

		Partida primeiraPartida = partidaService.buscarPrimeiraPartidaRegistrada();

		LocalDateTime dataInicio = LocalDateTime.of(primeiraPartida.getDataInicio().getYear(),
				primeiraPartida.getDataInicio().getMonthValue(), 1, 0, 0, 0);
		LocalDateTime dataFim = dataInicio.plusMonths(1);

		Long diferencaMeses = ChronoUnit.MONTHS.between(dataInicio, dataAgora);

		List<String> labels = new ArrayList<String>();
		List<BigDecimal> rakesMensais = new ArrayList<BigDecimal>();
		List<BigDecimal> rakesAcumulados = new ArrayList<BigDecimal>();

		BigDecimal acumulado = BigDecimal.ZERO;

		for (int i = 0; i < diferencaMeses + 1; i++) {
			BigDecimal lucroRakes = retornaAcumuladoDosRakes(dataInicio, dataFim, usuario);

			labels.add(UtilMes.retornaMesPorNumero(dataInicio.getMonthValue()) + " - " + dataInicio.getYear());
			rakesMensais.add(lucroRakes);
			acumulado = acumulado.add(lucroRakes);
			rakesAcumulados.add(acumulado);

			dataInicio = dataInicio.plusMonths(1);
			dataFim = dataFim.plusMonths(1);
		}

		GraficoAcumuladoDTO dto = new GraficoAcumuladoDTO();
		dto.setLabels(labels);
		dto.setValores(Arrays.asList(rakesMensais, rakesAcumulados));

		return dto;
	}
}
