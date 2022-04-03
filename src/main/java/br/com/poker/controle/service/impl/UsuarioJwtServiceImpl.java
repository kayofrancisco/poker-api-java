package br.com.poker.controle.service.impl;

import java.util.List;

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
		List<Usuario> listUsuarios = service.buscarPorEmail(username);

		if (listUsuarios.isEmpty()) {
			throw new UsernameNotFoundException("Login não encontrado");
		}

		Usuario usuario = listUsuarios.get(0);

		return User.builder().username(usuario.getNome()).password(usuario.getSenha()).roles("USER").build();
	}

}
