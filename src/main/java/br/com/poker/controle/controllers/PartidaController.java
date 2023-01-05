package br.com.poker.controle.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.poker.controle.models.Partida;
import br.com.poker.controle.models.dto.ContentDTO;
import br.com.poker.controle.service.PartidaService;
import br.com.poker.controle.utils.ResponseUtils;

@RestController
@RequestMapping(value = "/partidas", produces = "application/json")
public class PartidaController {

	private PartidaService service;

	@Autowired
	private void setPartidaService(PartidaService service) {
		this.service = service;
	}

	@GetMapping
	public ResponseEntity<ContentDTO<List<Partida>>> buscar() {
		try {
			return ResponseUtils.sucesso(service.buscar());
		} catch (Exception e) {
			return ResponseUtils.falha(e);
		}
	}

	@PostMapping
	public ResponseEntity<ContentDTO<Partida>> cadastrar(@RequestBody Partida partida) {
		try {
			return ResponseUtils.sucesso(service.cadastrar(partida));
		} catch (Exception e) {
			return ResponseUtils.falha(e);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ContentDTO<Object>> excluir(@PathVariable("id") Integer id) {
		try {
			service.excluir(id);
			return ResponseUtils.sucesso();
		} catch (Exception e) {
			return ResponseUtils.falha(e);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<ContentDTO<Partida>> editar(@RequestBody Partida partida, @PathVariable("id") Integer id) {
		try {
			return ResponseUtils.sucesso(service.editar(partida, id));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseUtils.falha(e);
		}
	}
}
