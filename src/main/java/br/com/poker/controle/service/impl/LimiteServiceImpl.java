package br.com.poker.controle.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.poker.controle.models.Limite;
import br.com.poker.controle.repository.LimiteRepository;
import br.com.poker.controle.service.LimiteService;

@Service("LimiteService")
public class LimiteServiceImpl implements LimiteService {

	private LimiteRepository repository;

	@Autowired
	private void setRepository(LimiteRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<Limite> buscar() {
		return repository.findAll();
	}
}
