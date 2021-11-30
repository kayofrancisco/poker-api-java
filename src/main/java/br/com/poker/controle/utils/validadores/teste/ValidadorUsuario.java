package br.com.poker.controle.utils.validadores.teste;

import br.com.poker.controle.models.Usuario;
import br.com.poker.controle.repository.UsuarioRepository;
import br.com.poker.controle.utils.validadores.Validador;
import br.com.poker.controle.utils.validadores.regrascamposobrigatorios.RegrasCamposObrigatoriosUsuario;
import br.com.poker.controle.utils.validadores.regrasnegociais.RegraNegocialUsuario;

public class ValidadorUsuario extends Validador<Usuario> {

	public ValidadorUsuario(Usuario objetoParaValidar, UsuarioRepository usuarioRepository, Boolean validaCpfNoBanco, Boolean validaEmailNoBanco) {
		super(objetoParaValidar);
		adicionarRegra(new RegrasCamposObrigatoriosUsuario());
		adicionarRegra(new RegraNegocialUsuario(usuarioRepository, validaCpfNoBanco, validaEmailNoBanco));
	}

}
