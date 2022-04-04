package br.com.poker.controle.utils.validadores.regrascamposobrigatorios;
import static br.com.poker.controle.utils.validadores.alertas.AlertasPartida.alertaContaNulo;
import static br.com.poker.controle.utils.validadores.alertas.AlertasPartida.alertaDataCriacaoNulo;
import static br.com.poker.controle.utils.validadores.alertas.AlertasPartida.alertaLimiteNulo;
import static br.com.poker.controle.utils.validadores.alertas.AlertasPartida.alertaQuantidadeMaosNuloOuMenorQueZero;
import static br.com.poker.controle.utils.validadores.alertas.AlertasPartida.alertaValorNulo;
import static br.com.poker.controle.utils.validadores.alertas.AlertasPartida.alertaQuantidadeMesasNuloOuMenorQueZero;

import java.util.ArrayList;
import java.util.List;

import br.com.poker.controle.exceptions.NegocioException;
import br.com.poker.controle.models.Partida;
import br.com.poker.controle.utils.validadores.RegrasValidador;

public class RegrasCamposObrigatoriosPartida implements RegrasValidador<Partida> {

	@Override
	public void validar(Partida partida) throws NegocioException {
		List<String> erros = new ArrayList<>();

		if(partida.getValor() == null) {
			erros.add(alertaValorNulo());
		}
		
		if (partida.getQuantidadeMaos() == null || partida.getQuantidadeMaos() <= 0) {
			erros.add(alertaQuantidadeMaosNuloOuMenorQueZero());
		}
		
		if (partida.getQuantidadeMesas() == null || partida.getQuantidadeMesas() <= 0) {
			erros.add(alertaQuantidadeMesasNuloOuMenorQueZero());
		}
		
		if (partida.getConta() == null || partida.getConta().getId() == null) {
			erros.add(alertaContaNulo());
		}
		
		if (partida.getLimite() == null || partida.getLimite().getId() == null) {
			erros.add(alertaLimiteNulo());
		}
		
		if (partida.getData() == null) {
			erros.add(alertaDataCriacaoNulo());
		}

		if (erros.size() > 0) {
			throw new NegocioException(erros);
		}
	}
}
