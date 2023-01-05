package br.com.poker.controle.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.poker.controle.exceptions.NegocioException;
import br.com.poker.controle.models.Conta;
import br.com.poker.controle.models.Usuario;
import br.com.poker.controle.repository.ContaRepository;
import br.com.poker.controle.service.ContaService;
import br.com.poker.controle.service.UsuarioService;
import br.com.poker.controle.utils.validadores.teste.ValidadorConta;

@Service("ContaService")
public class ContaServiceImpl implements ContaService {

	private ContaRepository repository;
	private UsuarioService usuarioService;
	
	@Autowired
	protected void setRepository(ContaRepository repository) {
		this.repository = repository;
	}

	@Autowired
	protected void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	@Override
	public List<Conta> buscar() {
		Usuario usuario = usuarioService.recuperaUsuarioLogado();
		
		return repository.findByUsuario(usuario);
	}

	@Override
	public Conta cadastrar(Conta conta) throws NegocioException {
		ValidadorConta validador = new ValidadorConta(conta, repository, Boolean.TRUE);

		Usuario usuario = usuarioService.recuperaUsuarioLogado();
		
		conta.setUsuario(usuario);
		
		if (!validador.validar()) {
			throw new NegocioException(validador.getErros());
		}
		
		return repository.save(conta);
	}

	@Override
	public Conta editar(Conta conta, Integer id) throws NegocioException {
		Conta contaParaEditar = buscarPorId(id)
				.orElseThrow(() -> new NegocioException("Conta não encontrada para o id informado"));

		ValidadorConta validador = new ValidadorConta(conta, repository,
				!contaParaEditar.getNick().equals(conta.getNick()));

		if (!validador.validar()) {
			throw new NegocioException(validador.getErros());
		}

		contaParaEditar.setAtivo(conta.getAtivo());
		contaParaEditar.setNick(conta.getNick());
		contaParaEditar.setPlataforma(conta.getPlataforma());
		
		return repository.save(contaParaEditar);
	}

	@Override
	public void deletar(Integer id) {
		repository.deleteById(id);
	}

	@Override
	public Optional<Conta> buscarPorId(Integer id) {
		return repository.findById(id);
	}
}
