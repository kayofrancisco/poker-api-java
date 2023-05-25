package br.com.poker.controle.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.poker.controle.exceptions.NegocioException;
import br.com.poker.controle.models.Limite;
import br.com.poker.controle.models.Partida;
import br.com.poker.controle.models.Rake;
import br.com.poker.controle.models.Usuario;
import br.com.poker.controle.models.dto.DadosResumidosDTO;
import br.com.poker.controle.repository.PartidaRepository;
import br.com.poker.controle.service.PartidaService;
import br.com.poker.controle.service.RakeService;
import br.com.poker.controle.service.UsuarioService;
import br.com.poker.controle.utils.validadores.teste.ValidadorPartida;

@Service("PartidaService")
public class PartidaServiceImpl implements PartidaService {

	private PartidaRepository repository;
	private UsuarioService usuarioService;
	private RakeService rakeService;

	@Autowired
	protected void setRepository(PartidaRepository repository) {
		this.repository = repository;
	}

	@Autowired
	protected void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	@Autowired
	protected void setRakeService(RakeService rakeService) {
		this.rakeService = rakeService;
	}

	@Override
	public Page<Partida> buscar(Integer page, Integer size, Boolean buscaTotal) {
		Usuario usuario = usuarioService.recuperaUsuarioLogado();

		Integer pagina = (buscaTotal != null && buscaTotal == Boolean.TRUE) ? 0 : page;
		Integer tamanho = (buscaTotal != null && buscaTotal == Boolean.TRUE) ? Integer.MAX_VALUE : size;

		Pageable pageable = PageRequest.of(pagina, tamanho, Sort.by("dataInicio").descending());

		Page<Partida> partidas = repository.findByUsuarioId(usuario.getId(), pageable);

		return partidas;
	}

	@Override
	public Partida cadastrar(Partida partida) throws NegocioException {
		ValidadorPartida validador = new ValidadorPartida(partida, Boolean.FALSE);

		if (!validador.validar()) {
			throw new NegocioException(validador.getErros());
		}

		Usuario usuario = usuarioService.recuperaUsuarioLogado();
		partida.setUsuario(usuario);

		return repository.save(partida);
	}

	@Override
	public Partida editar(Partida partida, Integer id) throws NegocioException {
		Partida partidaBanco = repository.findById(id)
				.orElseThrow(() -> new NegocioException("Partida não encontrada para o id informado"));

		ValidadorPartida validador = new ValidadorPartida(partida, Boolean.TRUE);

		if (!validador.validar()) {
			throw new NegocioException(validador.getErros());
		}

		Usuario usuario = usuarioService.recuperaUsuarioLogado();
		partida.setUsuario(usuario);

		partidaBanco.setDataInicio(partida.getDataInicio());
		partidaBanco.setDataFim(partida.getDataFim());
		partidaBanco.setLimite(partida.getLimite());
		partidaBanco.setQuantidadeMaosInicio(partida.getQuantidadeMaosInicio());
		partidaBanco.setQuantidadeMaosFim(partida.getQuantidadeMaosFim());
		partidaBanco.setFichasIniciais(partida.getFichasIniciais());
		partidaBanco.setFichasFinais(partida.getFichasFinais());

//		return partidaBanco;
		return repository.save(partidaBanco);
	}

	@Override
	public void excluir(Integer id) throws NegocioException {
		this.repository.deleteById(id);
	}

	@Override
	public DadosResumidosDTO buscarPorIntervaloData(LocalDateTime dataMinima, LocalDateTime dataMaxima)
			throws NegocioException {
		Usuario usuario = usuarioService.recuperaUsuarioLogado();
		List<Partida> partidas = repository.findAllByDataInicioBetweenAndUsuarioId(dataMinima, dataMaxima,
				usuario.getId());
		if (partidas.isEmpty()) {
			throw new NegocioException("Não existe partida cadastrada no intervalo de datas informado");
		}

		Limite limite = partidas.get(partidas.size() - 1).getLimite();
		Long segundosJogados = partidas.stream()
				.map(item -> item.getDataFim() != null
						? Duration.between(item.getDataInicio(), item.getDataFim()).getSeconds()
						: 0)
				.reduce(0L, (subtotal, element) -> subtotal + element);
		Integer maosJogadas = partidas.stream()
				.map(item -> item.getQuantidadeMaosFim() != null
						? item.getQuantidadeMaosFim() - item.getQuantidadeMaosInicio()
						: 0)
				.reduce(0, (subtotal, element) -> subtotal + element);
		BigDecimal lucro = partidas.stream()
				.map(item -> item.getFichasFinais() != null ? item.getFichasFinais().subtract(item.getFichasIniciais())
						: BigDecimal.ZERO)
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		List<Rake> rakes = rakeService.buscarRakesPorIntervalo(dataMinima, dataMaxima, usuario.getId());
		BigDecimal rake = rakes.isEmpty() ? BigDecimal.ZERO
				: rakes.stream().map(item -> item.getValor()).reduce(BigDecimal.ZERO, BigDecimal::add);

		BigDecimal buyin = limite.getBigBlind().multiply(BigDecimal.valueOf(100));

		long hh = segundosJogados / 3600;
		long mm = (segundosJogados % 3600) / 60;
		long ss = segundosJogados % 60;

		DadosResumidosDTO dados = new DadosResumidosDTO();
		dados.setLimite(limite);
		dados.setHorasJogadas(String.format("%02d:%02d:%02d", hh, mm, ss));
		dados.setTotalSessoes(partidas.size());
		dados.setMaosJogadas(maosJogadas);
		dados.setLucroSemRake(lucro);
		dados.setRake(rake);
		dados.setBuyinsUp(lucro.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : 
			lucro.divide(buyin).setScale(3, RoundingMode.HALF_UP));
		BigDecimal lucroPorMaos = lucro.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : 
			lucro.divide(BigDecimal.valueOf(maosJogadas), 3, RoundingMode.HALF_UP);
		BigDecimal lucroPorSegundo = lucro.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : 
			lucro.divide(BigDecimal.valueOf(segundosJogados), 3, RoundingMode.HALF_UP);

		dados.setWinrateMaos(lucroPorMaos.multiply(BigDecimal.valueOf(100)).setScale(3, RoundingMode.UP));
		dados.setWinrateHoras(lucroPorSegundo.multiply(BigDecimal.valueOf(3600)));
		dados.setLucro(lucro.add(rake));

		return dados;
	}

	@Override
	public Partida buscarPrimeiraPartidaRegistrada() {
		Usuario usuario = usuarioService.recuperaUsuarioLogado();

		Pageable pageable = PageRequest.of(0, 1, Sort.by("dataInicio").ascending());

		Page<Partida> partidas = repository.findByUsuarioId(usuario.getId(), pageable);

		return partidas.getContent().get(0);
	}
}
