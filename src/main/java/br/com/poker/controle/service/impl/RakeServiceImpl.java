package br.com.poker.controle.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.poker.controle.exceptions.NegocioException;
import br.com.poker.controle.models.Rake;
import br.com.poker.controle.models.Usuario;
import br.com.poker.controle.repository.RakeRepository;
import br.com.poker.controle.service.RakeService;
import br.com.poker.controle.service.UsuarioService;
import br.com.poker.controle.utils.validadores.teste.ValidadorRake;

@Service("RakeService")
public class RakeServiceImpl implements RakeService {

	private RakeRepository repository;
	private UsuarioService usuarioService;

	@Autowired
	protected void setRepository(RakeRepository repository) {
		this.repository = repository;
	}

	@Autowired
	protected void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	@Override
	public Page<Rake> buscar(Integer page, Integer size,  Boolean buscaTotal) {
		Usuario usuario = usuarioService.recuperaUsuarioLogado();
		
		Integer pagina = (buscaTotal != null && buscaTotal == Boolean.TRUE) ? 0 : page;
		Integer tamanho = (buscaTotal != null && buscaTotal == Boolean.TRUE) ? Integer.MAX_VALUE : size;
		
		Pageable pageable = PageRequest.of(pagina, tamanho, Sort.by("criadoEm").descending());

		return repository.findByUsuarioId(usuario.getId(), pageable);
	}

	@Override
	public Optional<Rake> buscarPorId(Integer id) {
		return repository.findById(id);
	}

	@Override
	public Rake cadastrar(Rake rake) throws NegocioException {
		ValidadorRake validador = new ValidadorRake(rake);
		
		if (!validador.validar()) {
			throw new NegocioException(validador.getErros());
		}
		
		Usuario usuario = usuarioService.recuperaUsuarioLogado();
		rake.setUsuario(usuario);
		
		return repository.save(rake);
	}

	@Override
	public Rake editar(Rake rake, Integer id) throws NegocioException {
		Rake rakeBanco = buscarPorId(id).orElseThrow(() -> new NegocioException("Rake não encontrado"));
		
		ValidadorRake validador = new ValidadorRake(rake);

		if (!validador.validar()) {
			throw new NegocioException(validador.getErros());
		}
		
		rakeBanco.setValor(rake.getValor());
		rakeBanco.setCriadoEm(rake.getCriadoEm());

		return repository.save(rake);
	}

	@Override
	public void deletar(Integer id) {
		repository.deleteById(id);
	}

	@Override
	public List<Rake> buscarRakesPorIntervalo(LocalDateTime inicio, LocalDateTime fim) {
		return repository.findAllByCriadoEmBetween(inicio, fim);
	}
}
