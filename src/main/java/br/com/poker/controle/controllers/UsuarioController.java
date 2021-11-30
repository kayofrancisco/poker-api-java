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

import br.com.poker.controle.models.Usuario;
import br.com.poker.controle.models.dto.ContentDTO;
import br.com.poker.controle.service.UsuarioService;
import br.com.poker.controle.utils.ResponseUtils;

@RestController
@RequestMapping(value = "/usuarios", produces = "application/json")
public class UsuarioController {

	private UsuarioService service;
	
	@Autowired
	private void setUsuarioService(UsuarioService service) {
		this.service = service;
	}

	@GetMapping
	public ResponseEntity<ContentDTO<List<Usuario>>> buscar() {
		try {
			return ResponseUtils.sucesso(service.buscar());
		} catch (Exception e) {
			return ResponseUtils.falha(e);
		}
	}

	@PostMapping
	public ResponseEntity<ContentDTO<Usuario>> cadastrar(@RequestBody Usuario usuario) {
		try {
			return ResponseUtils.sucesso(service.cadastrar(usuario));
		} catch (Exception e) {
			return ResponseUtils.falha(e);
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ContentDTO<Usuario>> editar(@RequestBody Usuario usuario, @PathVariable("id") Integer id) {
		try {
			return ResponseUtils.sucesso(service.editar(usuario, id));
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
