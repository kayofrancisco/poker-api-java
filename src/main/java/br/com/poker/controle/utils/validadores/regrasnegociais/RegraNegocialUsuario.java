package br.com.poker.controle.utils.validadores.regrasnegociais;

import static br.com.poker.controle.utils.validadores.alertas.AlertasUsuario.*;

import java.util.ArrayList;
import java.util.List;

import br.com.poker.controle.exceptions.NegocioException;
import br.com.poker.controle.models.Usuario;
import br.com.poker.controle.repository.UsuarioRepository;
import br.com.poker.controle.utils.Utils;
import br.com.poker.controle.utils.validadores.RegrasValidador;

public class RegraNegocialUsuario implements RegrasValidador<Usuario> {
	private UsuarioRepository usuarioRepository;
	private Boolean validaCpfNoBanco;
	private Boolean validaEmailNoBanco;

	public RegraNegocialUsuario(UsuarioRepository usuarioRepository, Boolean validaCpfNoBanco,
			Boolean validaEmailNoBanco) {
		this.usuarioRepository = usuarioRepository;
		this.validaCpfNoBanco = validaCpfNoBanco;
		this.validaEmailNoBanco = validaEmailNoBanco;
	}

	@Override
	public void validar(Usuario usuario) throws NegocioException {
		List<String> erros = new ArrayList<>();

		List<Usuario> usuariosPorCpf = usuarioRepository.findByCpf(usuario.getCpf());
		List<Usuario> usuariosPorEmail = usuarioRepository.findByEmail(usuario.getEmail());

		if (!Utils.emailValido(usuario.getEmail())) {
			erros.add(alertaEmailInvalido());
		}

		if (!Utils.cpfValido(usuario.getCpf())) {
			erros.add(alertaCpfInvalido());
		}

		if (validaCpfNoBanco) {
			if (usuariosPorCpf.size() > 0) {
				erros.add(alertaUsuarioExistenteComCpf());
			}
		}

		if (validaEmailNoBanco) {
			if (usuariosPorEmail.size() > 0) {
				erros.add(alertaUsuarioExistenteComEmail());
			}
		}

		if (erros.size() > 0) {
			throw new NegocioException(erros);
		}
	}
}
