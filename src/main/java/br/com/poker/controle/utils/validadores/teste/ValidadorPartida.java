package br.com.poker.controle.utils.validadores.teste;

import br.com.poker.controle.models.Partida;
import br.com.poker.controle.utils.validadores.Validador;
import br.com.poker.controle.utils.validadores.regrascamposobrigatorios.RegrasCamposObrigatoriosPartida;

public class ValidadorPartida extends Validador<Partida> {

	public ValidadorPartida(Partida objetoParaValidar) {
		super(objetoParaValidar);
		adicionarRegra(new RegrasCamposObrigatoriosPartida());
	}

}
