package br.com.poker.controle.controllers;

import java.time.LocalDateTime;

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

import br.com.poker.controle.models.Partida;
import br.com.poker.controle.models.dto.ContentDTO;
import br.com.poker.controle.models.dto.DadosResumidosDTO;
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
	public ResponseEntity<ContentDTO<Page<Partida>>> buscar(
			@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
			@RequestParam(value = "busca-total", required = false) Boolean buscaTotal) {
		try {
			return ResponseUtils.sucesso(service.buscar(page, size, buscaTotal));
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

	@GetMapping()
	public ResponseEntity<ContentDTO<DadosResumidosDTO>> buscarIntervaloData(
			@RequestParam("inicio") LocalDateTime inicio, @RequestParam("fim") LocalDateTime fim) {
		try {
			return ResponseUtils.sucesso(service.buscarPorIntervaloData(inicio, fim));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseUtils.falha(e);
		}
	}
}
