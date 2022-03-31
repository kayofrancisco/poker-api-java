package br.com.poker.controle.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.poker.controle.models.Conta;
import br.com.poker.controle.models.dto.ContentDTO;
import br.com.poker.controle.service.ContaService;
import br.com.poker.controle.utils.ResponseUtils;

@RestController
@RequestMapping(value = "/contas", produces = "application/json")
@CrossOrigin("*")
public class ContaController {

	private ContaService service;

	@Autowired
	private void setContaService(ContaService service) {
		this.service = service;
	}

	@GetMapping
	public ResponseEntity<ContentDTO<List<Conta>>> buscar() {
		try {
			return ResponseUtils.sucesso(service.buscar());
		} catch (Exception e) {
			return ResponseUtils.falha(e);
		}
	}

	@PostMapping
	public ResponseEntity<ContentDTO<Conta>> cadastrar(@RequestBody Conta conta) {
		try {
			return ResponseUtils.sucesso(service.cadastrar(conta));
		} catch (Exception e) {
			return ResponseUtils.falha(e);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<ContentDTO<Conta>> editar(@RequestBody Conta conta, @PathVariable("id") Integer id) {
		try {
			return ResponseUtils.sucesso(service.editar(conta, id));
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
