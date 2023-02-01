package br.com.poker.controle.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.poker.controle.models.Limite;
import br.com.poker.controle.models.dto.ContentDTO;
import br.com.poker.controle.service.LimiteService;
import br.com.poker.controle.utils.ResponseUtils;

@RestController
@RequestMapping(value = "/api/limites", produces = "application/json")
public class LimiteController {

	private LimiteService service;
	
	@Autowired
	private void setClubeService(LimiteService service) {
		this.service = service;
	}

	@GetMapping
	public ResponseEntity<ContentDTO<List<Limite>>> buscar() {
		try {
			return ResponseUtils.sucesso(service.buscar());
		} catch (Exception e) {
			return ResponseUtils.falha(e);
		}
	}
}
