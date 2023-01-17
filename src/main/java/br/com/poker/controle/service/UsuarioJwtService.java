package br.com.poker.controle.service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UsuarioJwtService extends UserDetailsService {
	public Boolean usuarioLogadoisAdm();
}
