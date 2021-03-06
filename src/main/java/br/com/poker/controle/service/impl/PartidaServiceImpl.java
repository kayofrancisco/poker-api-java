package br.com.poker.controle.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.poker.controle.exceptions.NegocioException;
import br.com.poker.controle.models.Partida;
import br.com.poker.controle.repository.PartidaRepository;
import br.com.poker.controle.service.PartidaService;
import br.com.poker.controle.utils.validadores.teste.ValidadorPartida;

@Service("PartidaService")
public class PartidaServiceImpl implements PartidaService {

	private PartidaRepository repository;

	@Autowired
	protected void setRepository(PartidaRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<Partida> buscar() {
		return repository.findAll();
	}

	@Override
	public Partida cadastrar(Partida partida) throws NegocioException {
		ValidadorPartida validador = new ValidadorPartida(partida);

		if (!validador.validar()) {
			throw new NegocioException(validador.getErros());
		}

		BigDecimal quantidade = partida.getValor().divide(partida.getLimite().getBigBlind(), 2, RoundingMode.HALF_UP);

		partida.setQuantidadeBigBlind(quantidade);

		return repository.save(partida);
	}

	@Override
	public Partida editar(Partida partida, Integer id) throws NegocioException {
		Partida partidaBanco = repository.findById(id)
				.orElseThrow(() -> new NegocioException("Partida não encontrada para o id informado"));

		ValidadorPartida validador = new ValidadorPartida(partida);

		if (!validador.validar()) {
			throw new NegocioException(validador.getErros());
		}

		partidaBanco.setConta(partida.getConta());
		partidaBanco.setData(partida.getData());
		partidaBanco.setLimite(partida.getLimite());
		partidaBanco.setQuantidadeBigBlind(partida.getQuantidadeBigBlind());
		partidaBanco.setQuantidadeMaos(partida.getQuantidadeMaos());
		partidaBanco.setValor(partida.getValor());

		return repository.save(partidaBanco);
	}

	@Override
	public void excluir(Integer id) throws NegocioException {
		this.repository.deleteById(id);
	}
}
