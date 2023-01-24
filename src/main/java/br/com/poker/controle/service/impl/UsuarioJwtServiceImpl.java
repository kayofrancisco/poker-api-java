package br.com.poker.controle.service.impl;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.poker.controle.models.Usuario;
import br.com.poker.controle.service.UsuarioJwtService;
import br.com.poker.controle.service.UsuarioService;
import br.com.poker.controle.utils.Utils;

@Service("UsuarioJwtService")
public class UsuarioJwtServiceImpl implements UsuarioJwtService {

	private UsuarioService service;
	private static final String SIGLA_ADM = "ADM";

	@Autowired
	private void setUsuarioService(UsuarioService service) {
		this.service = service;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = service.buscarPorEmail(username);

		if (usuario == null) {
			throw new UsernameNotFoundException("Login não encontrado");
		}

		try {
			String senha = Utils.decodeSenha(usuario.getSenha());
			return User.builder().username(usuario.getEmail()).password(senha).roles(usuarioLogadoIsAdm(usuario) ? SIGLA_ADM : "USER").build();
		} catch (NoSuchAlgorithmException e) {
			throw new UsernameNotFoundException("Erro ao buscar usuário");
		}

	}

	@Override
	public Boolean usuarioLogadoisAdm() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		String email =  auth.getName();
		
		Usuario usuario = service.buscarPorEmail(email);
		
		return usuarioLogadoIsAdm(usuario);
	}
	
	private Boolean usuarioLogadoIsAdm(Usuario usuario) {
		return usuario.getPerfil().getSigla().equals(SIGLA_ADM);
	}

}
