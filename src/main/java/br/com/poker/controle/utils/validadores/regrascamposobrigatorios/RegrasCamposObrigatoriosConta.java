package br.com.poker.controle.utils.validadores.regrascamposobrigatorios;
import static br.com.poker.controle.utils.Utils.isNuloOuVazio;
import static br.com.poker.controle.utils.validadores.alertas.AlertasConta.alertaNickNuloOuVazio;
import static br.com.poker.controle.utils.validadores.alertas.AlertasConta.alertaUsuarioNulo;

import java.util.ArrayList;
import java.util.List;

import br.com.poker.controle.exceptions.NegocioException;
import br.com.poker.controle.models.Conta;
import br.com.poker.controle.utils.validadores.RegrasValidador;

public class RegrasCamposObrigatoriosConta implements RegrasValidador<Conta> {

	@Override
	public void validar(Conta conta) throws NegocioException {
		List<String> erros = new ArrayList<>();

		if (isNuloOuVazio(conta.getNick())) {
			erros.add(alertaNickNuloOuVazio());
		}

		if (conta.getUsuario() == null || conta.getUsuario().getId() == null) {
			erros.add(alertaUsuarioNulo());
		}
		
		
		if (erros.size() > 0) {
			throw new NegocioException(erros);
		}
	}
}
