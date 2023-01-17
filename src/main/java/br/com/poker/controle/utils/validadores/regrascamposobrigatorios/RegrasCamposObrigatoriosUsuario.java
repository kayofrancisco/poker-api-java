package br.com.poker.controle.utils.validadores.regrascamposobrigatorios;

import static br.com.poker.controle.utils.Utils.isNuloOuVazio;
import static br.com.poker.controle.utils.validadores.alertas.AlertasUsuario.*;

import java.util.ArrayList;
import java.util.List;

import br.com.poker.controle.exceptions.NegocioException;
import br.com.poker.controle.models.Usuario;
import br.com.poker.controle.utils.validadores.RegrasValidador;

public class RegrasCamposObrigatoriosUsuario implements RegrasValidador<Usuario> {

	@Override
	public void validar(Usuario usuario) throws NegocioException {
		List<String> erros = new ArrayList<>();

		if (isNuloOuVazio(usuario.getNome())) {
			erros.add(alertaNomeNuloOuVazio());
		}

		if (isNuloOuVazio(usuario.getEmail())) {
			erros.add(alertaEmailNuloOuVazio());
		}

		if (isNuloOuVazio(usuario.getSenha())) {
			erros.add(alertaSenhaNuloOuVazio());
		}

		if (isNuloOuVazio(usuario.getConfirmaSenha())) {
			erros.add(alertaConfirmacaoSenhaNuloOuVazio());
		}
		
		if (erros.size() > 0) {
			throw new NegocioException(erros);
		}
	}
}
