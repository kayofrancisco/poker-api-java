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

import br.com.poker.controle.models.Clube;
import br.com.poker.controle.models.dto.ContentDTO;
import br.com.poker.controle.service.ClubeService;
import br.com.poker.controle.utils.ResponseUtils;

@RestController
@RequestMapping(value = "/clubes", produces = "application/json")
public class ClubeController {

	private ClubeService service;
	
	@Autowired
	private void setClubeService(ClubeService service) {
		this.service = service;
	}

	@GetMapping
	public ResponseEntity<ContentDTO<List<Clube>>> buscar() {
		try {
			return ResponseUtils.sucesso(service.buscar());
		} catch (Exception e) {
			return ResponseUtils.falha(e);
		}
	}

	@PostMapping
	public ResponseEntity<ContentDTO<Clube>> cadastrar(@RequestBody Clube clube) {
		try {
			return ResponseUtils.sucesso(service.cadastrar(clube));
		} catch (Exception e) {
			return ResponseUtils.falha(e);
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ContentDTO<Clube>> editar(@RequestBody Clube clube, @PathVariable("id") Integer id) {
		try {
			return ResponseUtils.sucesso(service.editar(clube, id));
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
