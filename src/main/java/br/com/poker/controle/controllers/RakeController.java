package br.com.poker.controle.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.poker.controle.models.Rake;
import br.com.poker.controle.models.dto.ContentDTO;
import br.com.poker.controle.service.RakeService;
import br.com.poker.controle.utils.ResponseUtils;

@RestController
@RequestMapping(value = "/rakes", produces = "application/json")
public class RakeController {

	private RakeService service;

	@Autowired
	private void setRakeService(RakeService service) {
		this.service = service;
	}

	@GetMapping
	public ResponseEntity<ContentDTO<Page<Rake>>> buscar(
			@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
			@RequestParam(value = "busca-total", required = false) Boolean buscaTotal) {
		try {
			return ResponseUtils.sucesso(service.buscar(page, size, buscaTotal));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseUtils.falha(e);
		}
	}

	@PostMapping
	public ResponseEntity<ContentDTO<Rake>> cadastrar(@RequestBody Rake Rake) {
		try {
			return ResponseUtils.sucesso(service.cadastrar(Rake));
		} catch (Exception e) {
			return ResponseUtils.falha(e);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<ContentDTO<Rake>> editar(@RequestBody Rake Rake, @PathVariable("id") Integer id) {
		try {
			return ResponseUtils.sucesso(service.editar(Rake, id));
		} catch (Exception e) {
			return ResponseUtils.falha(e);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ContentDTO<Integer>> deletar(@PathVariable("id") Integer id) {
		try {
			service.deletar(id);
			return ResponseUtils.sucesso();
		} catch (Exception e) {
			return ResponseUtils.falha(e);
		}

	}
}
