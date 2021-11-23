package br.com.poker.controle.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.poker.controle.models.Clube;
import br.com.poker.controle.repository.ClubeRepository;
import br.com.poker.controle.service.ClubeService;

@Service("ClubeService")
public class ClubeServiceImpl implements ClubeService {

	@Autowired
	private ClubeRepository repository;

	@Override
	public List<Clube> buscar() {
		return repository.findAll();
	}

	@Override
	public Clube cadastrar(Clube clube) {
		clube.setCriadoEm(LocalDateTime.now());
		return repository.save(clube);
	}

	@Override
	public Clube editar(Clube clube, Integer id) {
		Clube clubeParaEditar = repository.findById(id).orElse(null);
		
		clubeParaEditar.setNome(clube.getNome());
		clubeParaEditar.setCriadoEm(LocalDateTime.now());
		
		return repository.save(clubeParaEditar);
	}
}
