package br.com.poker.controle.utils.validadores.regrasnegociais;

import static br.com.poker.controle.utils.validadores.alertas.AlertasUsuario.alertaEmailInvalido;
import static br.com.poker.controle.utils.validadores.alertas.AlertasUsuario.alertaUsuarioExistenteComEmail;

import java.util.ArrayList;
import java.util.List;

import br.com.poker.controle.exceptions.NegocioException;
import br.com.poker.controle.models.Usuario;
import br.com.poker.controle.repository.UsuarioRepository;
import br.com.poker.controle.utils.Utils;
import br.com.poker.controle.utils.validadores.RegrasValidador;

public class RegraNegocialUsuario implements RegrasValidador<Usuario> {
	private UsuarioRepository usuarioRepository;
	private Boolean validaEmailNoBanco;

	public RegraNegocialUsuario(UsuarioRepository usuarioRepository,
			Boolean validaEmailNoBanco) {
		this.usuarioRepository = usuarioRepository;
		this.validaEmailNoBanco = validaEmailNoBanco;
	}

	@Override
	public void validar(Usuario usuario) throws NegocioException {
		List<String> erros = new ArrayList<>();

		Usuario usuarioBanco = usuarioRepository.findByEmail(usuario.getEmail()).orElse(null);

		if (!Utils.emailValido(usuario.getEmail())) {
			erros.add(alertaEmailInvalido());
		}

		if (validaEmailNoBanco) {
			if (usuarioBanco != null) {
				erros.add(alertaUsuarioExistenteComEmail());
			}
		}

		if (erros.size() > 0) {
			throw new NegocioException(erros);
		}
	}
}
