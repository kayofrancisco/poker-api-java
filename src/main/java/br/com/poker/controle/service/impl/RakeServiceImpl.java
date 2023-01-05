package br.com.poker.controle.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.poker.controle.exceptions.NegocioException;
import br.com.poker.controle.models.Conta;
import br.com.poker.controle.models.Rake;
import br.com.poker.controle.models.Usuario;
import br.com.poker.controle.repository.RakeRepository;
import br.com.poker.controle.service.ContaService;
import br.com.poker.controle.service.RakeService;
import br.com.poker.controle.service.UsuarioService;
import br.com.poker.controle.utils.validadores.teste.ValidadorRake;

@Service("RakeService")
public class RakeServiceImpl implements RakeService {

	private RakeRepository repository;
	private ContaService contaService;
	private UsuarioService usuarioService;
	
	@Autowired
	protected void setRepository(RakeRepository repository) {
		this.repository = repository;
	}

	@Autowired
	protected void setContaService(ContaService service) {
		this.contaService = service;
	}
	
	@Autowired
	protected void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	@Override
	public List<Rake> buscar() {
		Usuario usuario = usuarioService.recuperaUsuarioLogado();
		
		return repository.findByContaUsuarioId(usuario.getId());
	}

	@Override
	public Optional<Rake> buscarPorId(Integer id) {
		return repository.findById(id);
	}

	@Override
	public List<Rake> buscarPorConta(Integer idConta) throws NegocioException {
		Conta conta = contaService.buscarPorId(idConta).orElseThrow(() -> new NegocioException("Conta não encontrada"));

		return repository.findByConta(conta);
	}

	@Override
	public Rake cadastrar(Rake rake) throws NegocioException {
		ValidadorRake validador = new ValidadorRake(rake);

		if (!validador.validar()) {
			throw new NegocioException(validador.getErros());
		}

		return repository.save(rake);
	}

	@Override
	public Rake editar(Rake rake, Integer id) throws NegocioException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deletar(Integer id) {
		repository.deleteById(id);
	}
}
