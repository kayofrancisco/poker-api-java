package br.com.poker.controle.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.poker.controle.models.Perfil;
import br.com.poker.controle.models.dto.ContentDTO;
import br.com.poker.controle.service.PerfilService;
import br.com.poker.controle.utils.ResponseUtils;

@RestController
@RequestMapping(value = "/perfis", produces = "application/json")
@CrossOrigin("*")
public class PerfilController {

	private PerfilService service;
	
	@Autowired
	private void setService(PerfilService service) {
		this.service = service;
	}

	@GetMapping
	public ResponseEntity<ContentDTO<List<Perfil>>> buscar() {
		try {
			return ResponseUtils.sucesso(service.buscar());
		} catch (Exception e) {
			return ResponseUtils.falha(e);
		}
	}
}
