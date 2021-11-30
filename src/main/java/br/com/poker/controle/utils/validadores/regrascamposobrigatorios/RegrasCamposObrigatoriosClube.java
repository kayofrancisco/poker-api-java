package br.com.poker.controle.utils.validadores.regrascamposobrigatorios;
import static br.com.poker.controle.utils.Utils.isNuloOuVazio;
import static br.com.poker.controle.utils.validadores.alertas.AlertasUsuario.alertaNomeNuloOuVazio;

import java.util.ArrayList;
import java.util.List;

import br.com.poker.controle.exceptions.NegocioException;
import br.com.poker.controle.models.Clube;
import br.com.poker.controle.utils.validadores.RegrasValidador;

public class RegrasCamposObrigatoriosClube implements RegrasValidador<Clube> {

	@Override
	public void validar(Clube clube) throws NegocioException {
		List<String> erros = new ArrayList<>();

		if (isNuloOuVazio(clube.getNome())) {
			erros.add(alertaNomeNuloOuVazio());
		}

		if (erros.size() > 0) {
			throw new NegocioException(erros);
		}
	}
}
