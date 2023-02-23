package br.com.poker.controle.utils.validadores.regrascamposobrigatorios;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.com.poker.controle.exceptions.NegocioException;
import br.com.poker.controle.models.Rake;
import br.com.poker.controle.utils.validadores.RegrasValidador;

public class RegrasCamposObrigatoriosRake implements RegrasValidador<Rake> {

	@Override
	public void validar(Rake rake) throws NegocioException {
		List<String> erros = new ArrayList<>();
		
		if (rake.getValor() == null || rake.getValor().compareTo(BigDecimal.ZERO) <= 0) {
			erros.add("O valor do rake deve ser informado");
		}
		
		if (erros.size() > 0) {
			throw new NegocioException(erros);
		}
	}
}
