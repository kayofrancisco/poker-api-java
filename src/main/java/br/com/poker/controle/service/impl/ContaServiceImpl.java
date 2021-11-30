package br.com.poker.controle.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.poker.controle.exceptions.NegocioException;
import br.com.poker.controle.models.Conta;
import br.com.poker.controle.repository.ContaRepository;
import br.com.poker.controle.service.ContaService;
import br.com.poker.controle.utils.validadores.teste.ValidadorConta;

@Service("ContaService")
public class ContaServiceImpl implements ContaService {

	private ContaRepository repository;

	@Autowired
	private void setRepository(ContaRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<Conta> buscar() {
		return repository.findAll();
	}

	@Override
	public Conta cadastrar(Conta conta) throws NegocioException {
		ValidadorConta validador = new ValidadorConta(conta, repository, Boolean.TRUE);

		if (!validador.validar()) {
			throw new NegocioException(validador.getErros());
		}

		return repository.save(conta);
	}

	@Override
	public Conta editar(Conta conta, Integer id) throws NegocioException {
		Conta contaParaEditar = repository.findById(id)
				.orElseThrow(() -> new NegocioException("Conta não encontrada para o id informado"));

		ValidadorConta validador = new ValidadorConta(conta, repository,
				!contaParaEditar.getNick().equals(conta.getNick()));

		if (!validador.validar()) {
			throw new NegocioException(validador.getErros());
		}

		contaParaEditar.setAtivo(conta.getAtivo());
		contaParaEditar.setNick(conta.getNick());
		
		return repository.save(contaParaEditar);
	}

	@Override
	public void deletar(Integer id) {
		repository.deleteById(id);
	}
}
