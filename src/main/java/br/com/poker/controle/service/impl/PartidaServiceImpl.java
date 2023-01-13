package br.com.poker.controle.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.poker.controle.exceptions.NegocioException;
import br.com.poker.controle.models.Partida;
import br.com.poker.controle.models.Usuario;
import br.com.poker.controle.repository.PartidaRepository;
import br.com.poker.controle.service.PartidaService;
import br.com.poker.controle.service.UsuarioService;
import br.com.poker.controle.utils.validadores.teste.ValidadorPartida;

@Service("PartidaService")
public class PartidaServiceImpl implements PartidaService {

	private PartidaRepository repository;
	private UsuarioService usuarioService;

	@Autowired
	protected void setRepository(PartidaRepository repository) {
		this.repository = repository;
	}
	
	@Autowired
	protected void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	@Override
	public Page<Partida> buscar(Integer page, Integer size, Boolean buscaTotal) {
		Usuario usuario = usuarioService.recuperaUsuarioLogado();
		
		Integer pagina = (buscaTotal != null && buscaTotal == Boolean.TRUE) ? 0 : page;
		Integer tamanho = (buscaTotal != null && buscaTotal == Boolean.TRUE) ? Integer.MAX_VALUE : size;
		
		Pageable pageable = PageRequest.of(pagina, tamanho, Sort.by("dataInicio").descending());
		
		
		Page<Partida> partidas = repository.findByContaUsuarioId(usuario.getId(), pageable);
		
		return partidas;
	}

	@Override
	public Partida cadastrar(Partida partida) throws NegocioException {
		ValidadorPartida validador = new ValidadorPartida(partida, Boolean.FALSE);

		if (!validador.validar()) {
			throw new NegocioException(validador.getErros());
		}

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

		partidaBanco.setConta(partida.getConta());
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
	public List<Partida> buscarPorIntervaloData(LocalDateTime dataMinima, LocalDateTime dataMaxima) {
		// TODO Auto-generated method stub
		return null;
	}
}
