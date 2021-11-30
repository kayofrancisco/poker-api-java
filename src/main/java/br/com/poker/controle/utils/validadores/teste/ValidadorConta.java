package br.com.poker.controle.utils.validadores.teste;

import br.com.poker.controle.models.Conta;
import br.com.poker.controle.repository.ContaRepository;
import br.com.poker.controle.utils.validadores.Validador;
import br.com.poker.controle.utils.validadores.regrascamposobrigatorios.RegrasCamposObrigatoriosConta;
import br.com.poker.controle.utils.validadores.regrasnegociais.RegraNegocialConta;

public class ValidadorConta extends Validador<Conta> {

	public ValidadorConta(Conta objetoParaValidar, ContaRepository repository, Boolean validaBanco) {
		super(objetoParaValidar);
		adicionarRegra(new RegrasCamposObrigatoriosConta());
		adicionarRegra(new RegraNegocialConta(repository, validaBanco));
	}

}
