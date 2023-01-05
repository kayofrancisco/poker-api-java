package br.com.poker.controle.utils.validadores.regrascamposobrigatorios;
import static br.com.poker.controle.utils.validadores.alertas.AlertasPartida.alertaContaNulo;
import static br.com.poker.controle.utils.validadores.alertas.AlertasPartida.alertaDataCriacaoNulo;
import static br.com.poker.controle.utils.validadores.alertas.AlertasPartida.alertaFichasIniciaisNulo;
import static br.com.poker.controle.utils.validadores.alertas.AlertasPartida.alertaLimiteNulo;
import static br.com.poker.controle.utils.validadores.alertas.AlertasPartida.alertaQuantidadeMaosNuloOuMenorQueZero;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.com.poker.controle.exceptions.NegocioException;
import br.com.poker.controle.models.Partida;
import br.com.poker.controle.utils.validadores.RegrasValidador;

public class RegrasCamposObrigatoriosPartida implements RegrasValidador<Partida> {
	private Boolean validaValores;
	
	public RegrasCamposObrigatoriosPartida(Boolean validaValores) {
		this.validaValores = validaValores;
	}

	@Override
	public void validar(Partida partida) throws NegocioException {
		List<String> erros = new ArrayList<>();

		if (partida.getQuantidadeMaosInicio() == null || partida.getQuantidadeMaosInicio() <= 0) {
			erros.add(alertaQuantidadeMaosNuloOuMenorQueZero());
		}
		
		if (partida.getFichasIniciais() == null || partida.getFichasIniciais().compareTo(BigDecimal.ZERO) < 1) {
			erros.add(alertaFichasIniciaisNulo());
		}
		
		if (partida.getConta() == null || partida.getConta().getId() == null) {
			erros.add(alertaContaNulo());
		}
		
		if (partida.getLimite() == null || partida.getLimite().getId() == null) {
			erros.add(alertaLimiteNulo());
		}
		
		if (partida.getDataInicio() == null) {
			erros.add(alertaDataCriacaoNulo());
		}
		
		if (validaValores) {
			validarValores(erros, partida);
		}
			
		verificaErros(erros);
		
	}
	
	private void validarValores(List<String> erros, Partida partida) {
		if (partida.getDataFim() == null) {
			erros.add("A data de finalização da partida deve ser informada");
		} else {
			if (partida.getDataFim().isBefore(partida.getDataInicio())) {
				erros.add("A data de finalização da partida deve ser após a data de início");
			}
		}
		
		if (partida.getQuantidadeMaosFim() == null) {
			erros.add("A quantidade de mãos finais deve ser informada");
		} else {
			if (partida.getQuantidadeMaosFim() < partida.getQuantidadeMaosInicio()) {
				erros.add("A quantidade de mãos finais deve ser maior que a quantidade de mãos iniciais");
			}
		}
		
		if (partida.getFichasFinais() == null) {
			erros.add("A quantidade de fichas finais deve ser informada");
		}
	}
	
	private void verificaErros(List<String> erros) throws NegocioException {
		if (erros.size() > 0) {
			throw new NegocioException(erros);
		}
	}
}
