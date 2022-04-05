package br.com.poker.controle.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.poker.controle.models.Usuario;
import br.com.poker.controle.service.UsuarioJwtService;
import br.com.poker.controle.service.UsuarioService;

@Service("UsuarioJwtService")
public class UsuarioJwtServiceImpl implements UsuarioJwtService {

	private UsuarioService service;

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

		return User.builder().username(usuario.getEmail()).password(usuario.getSenha()).roles("USER").build();
	}

}
