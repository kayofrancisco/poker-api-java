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
		partidaBanco.setQuantidadeBigBlind(partida.getQuantidadeBigBlind());
		partidaBanco.setQuantidadeMaosInicio(partida.getQuantidadeMaosInicio());
		partidaBanco.setQuantidadeMaosFim(partida.getQuantidadeMaosFim());
		partidaBanco.setFichasIniciais(partida.getFichasIniciais());
		partidaBanco.setFichasFinais(partida.getFichasFinais());

		partida.setValor(partida.getFichasFinais().subtract(partida.getFichasIniciais()));

		partidaBanco.setValor(partida.getValor());

		BigDecimal quantidade = partida.getValor().divide(partida.getLimite().getBigBlind(), 2, RoundingMode.HALF_UP);
		partidaBanco.setQuantidadeBigBlind(quantidade);

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
		List<Partida> partidas = repository.findAllByDataInicioBetween(dataMinima, dataMaxima);
		if (partidas.isEmpty()) {
			throw new NegocioException("Não existe partida cadastrada no intervalo de datas informado");
		}

		Limite limite = partidas.get(partidas.size()).getLimite();
		Long horasJogadas = 0L;
		Integer maosJogadas = 0;
		BigDecimal lucro = new BigDecimal(0);

		for (int i = 0; i < partidas.size(); i++) {
			Long horasJogadasAtual = Duration.between(partidas.get(i).getDataFim(), partidas.get(i).getDataInicio())
					.getSeconds();
			Integer maosJogadasAtual = partidas.get(i).getQuantidadeMaosFim()
					- partidas.get(i).getQuantidadeMaosInicio();
			BigDecimal lucroAtual = partidas.get(i).getFichasFinais().subtract(partidas.get(i).getFichasIniciais());

			if (i == 0) {
				horasJogadas = horasJogadasAtual;
				maosJogadas = maosJogadasAtual;
				lucro = lucroAtual;
			} else {
				horasJogadas += horasJogadasAtual;
				maosJogadas += maosJogadasAtual;
				lucro.add(lucroAtual);
			}
		}

		List<Rake> rakes = rakeService.buscarRakesPorIntervalo(dataMinima, dataMaxima);
		BigDecimal rake = rakes.isEmpty() ? BigDecimal.ZERO
				: rakes.stream().map(item -> item.getValor()).reduce(BigDecimal.ZERO, BigDecimal::add);

		BigDecimal buyin = limite.getBigBlind().multiply(BigDecimal.valueOf(100));

		DadosResumidosDTO dados = new DadosResumidosDTO();
		dados.setLimite(limite);
		dados.setHorasJogadas(horasJogadas);
		dados.setMaosJogadas(maosJogadas);
		dados.setGanhoSemRake(lucro);
		dados.setRake(rake);
		dados.setBuyinsUp(lucro.divide(buyin));
		dados.setWinrateMaos(lucro.divide(BigDecimal.valueOf(maosJogadas)).multiply(BigDecimal.valueOf(100)));
		dados.setWinrateHoras(lucro.divide(BigDecimal.valueOf(maosJogadas)).multiply(BigDecimal.valueOf(60)));
		dados.setTotal(lucro.add(rake));

		return dados;
	}
}
