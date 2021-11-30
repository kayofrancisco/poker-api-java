package br.com.poker.controle.utils.validadores.regrasnegociais;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.com.poker.controle.exceptions.NegocioException;
import br.com.poker.controle.models.Conta;
import br.com.poker.controle.repository.ContaRepository;
import br.com.poker.controle.utils.validadores.RegrasValidador;

import static br.com.poker.controle.utils.validadores.alertas.AlertasConta.alertaNickExistente;

public class RegraNegocialConta implements RegrasValidador<Conta> {
	private ContaRepository repository;
	private Boolean validaNickNoBanco;

	public RegraNegocialConta(ContaRepository repository, Boolean validaNickNoBanco) {
		this.repository = repository;
		this.validaNickNoBanco = validaNickNoBanco;
	}

	@Override
	public void validar(Conta conta) throws NegocioException {
		List<String> erros = new ArrayList<>();

		if(validaNickNoBanco) {
			Optional<Conta> contaBanco = repository.findByNick(conta.getNick());
			
			if (contaBanco.isPresent()) {
				erros.add(alertaNickExistente());
			}
		}
		
		if (erros.size() > 0) {
			throw new NegocioException(erros);
		}
	}
}
