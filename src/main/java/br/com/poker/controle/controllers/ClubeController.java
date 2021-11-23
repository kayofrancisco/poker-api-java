package br.com.poker.controle.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.poker.controle.models.Clube;
import br.com.poker.controle.service.ClubeService;

@RestController
@RequestMapping(value = "/clubes", produces = "application/json")
public class ClubeController {

	@Autowired
	private ClubeService service;

	@GetMapping
	public List<Clube> buscar() {
		return service.buscar();
	}

	@PostMapping
	public Clube cadastrar(@RequestBody Clube clube) {
		return service.cadastrar(clube);
	}
	
	@PutMapping()
	public Clube editar(@RequestBody Clube clube, @RequestParam("id") Integer id) {
		return service.editar(clube, id);
	}

}
