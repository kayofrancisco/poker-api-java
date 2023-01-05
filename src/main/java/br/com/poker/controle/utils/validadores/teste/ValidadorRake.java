package br.com.poker.controle.utils.validadores.teste;

import br.com.poker.controle.models.Rake;
import br.com.poker.controle.utils.validadores.Validador;
import br.com.poker.controle.utils.validadores.regrascamposobrigatorios.RegrasCamposObrigatoriosRake;

public class ValidadorRake extends Validador<Rake> {

	public ValidadorRake(Rake objetoParaValidar) {
		super(objetoParaValidar);
		adicionarRegra(new RegrasCamposObrigatoriosRake());
	}

}
