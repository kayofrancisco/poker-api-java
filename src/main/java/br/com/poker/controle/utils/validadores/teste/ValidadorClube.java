package br.com.poker.controle.utils.validadores.teste;

import br.com.poker.controle.models.Clube;
import br.com.poker.controle.utils.validadores.Validador;
import br.com.poker.controle.utils.validadores.regrascamposobrigatorios.RegrasCamposObrigatoriosClube;

public class ValidadorClube extends Validador<Clube> {

	public ValidadorClube(Clube objetoParaValidar) {
		super(objetoParaValidar);
		adicionarRegra(new RegrasCamposObrigatoriosClube());
	}

}
