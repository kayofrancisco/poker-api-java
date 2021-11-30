package br.com.poker.controle.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.poker.controle.exceptions.NegocioException;
import br.com.poker.controle.models.Clube;
import br.com.poker.controle.repository.ClubeRepository;
import br.com.poker.controle.service.ClubeService;
import br.com.poker.controle.utils.validadores.teste.ValidadorClube;

@Service("ClubeService")
public class ClubeServiceImpl implements ClubeService {

	private ClubeRepository repository;

	@Autowired
	private void setRepository(ClubeRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<Clube> buscar() {
		return repository.findAll();
	}

	@Override
	public Clube cadastrar(Clube clube) throws NegocioException {
		ValidadorClube validador = new ValidadorClube(clube);

		if (!validador.validar()) {
			throw new NegocioException(validador.getErros());
		}

		return repository.save(clube);
	}

	@Override
	public Clube editar(Clube clube, Integer id) throws NegocioException {
		Clube clubeParaEditar = repository.findById(id)
				.orElseThrow(() -> new NegocioException("Clube não encontrado para o id informado"));

		ValidadorClube validador = new ValidadorClube(clube);

		if (!validador.validar()) {
			throw new NegocioException(validador.getErros());
		}

		clubeParaEditar.setNome(clube.getNome());

		return repository.save(clubeParaEditar);
	}

	@Override
	public void deletar(Integer id) {
		repository.deleteById(id);
	}
}
