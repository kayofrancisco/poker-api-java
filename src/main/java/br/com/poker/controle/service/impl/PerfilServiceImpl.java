package br.com.poker.controle.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.poker.controle.models.Perfil;
import br.com.poker.controle.repository.PerfilRepository;
import br.com.poker.controle.service.PerfilService;

@Service("PerfilService")
public class PerfilServiceImpl implements PerfilService {

	private PerfilRepository repository;

	@Autowired
	protected void setRepository(PerfilRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<Perfil> buscar() {
		return repository.findAll();
	}
}
