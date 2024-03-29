package br.com.poker.controle.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.poker.controle.models.dto.ContentDTO;
import br.com.poker.controle.models.dto.GraficoAcumuladoDTO;
import br.com.poker.controle.models.dto.GraficoAcumuladoMaosDTO;
import br.com.poker.controle.models.dto.GraficoDTO;
import br.com.poker.controle.service.GraficoService;
import br.com.poker.controle.utils.ResponseUtils;

@RestController
@RequestMapping(value = "/graficos", produces = "application/json")
public class GraficoController {

	private GraficoService service;

	@Autowired
	private void setService(GraficoService service) {
		this.service = service;
	}

	@GetMapping("/partidas")
	public ResponseEntity<ContentDTO<GraficoDTO>> buscarPartidas() {
		try {
			return ResponseUtils.sucesso(service.buscarDadosPartidas());
		} catch (Exception e) {
			return ResponseUtils.falha(e);
		}
	}
	
	@GetMapping("/rakes")
	public ResponseEntity<ContentDTO<GraficoDTO>> buscarRakes() {
		try {
			return ResponseUtils.sucesso(service.buscarDadosRakes());
		} catch (Exception e) {
			return ResponseUtils.falha(e);
		}
	}
	
	@GetMapping("/acumulados")
	public ResponseEntity<ContentDTO<GraficoAcumuladoDTO>> buscarAcumulados() {
		try {
			return ResponseUtils.sucesso(service.buscarDadosAcumulados());
		} catch (Exception e) {
			return ResponseUtils.falha(e);
		}
	}
	
	@GetMapping("/rakes-acumulados")
	public ResponseEntity<ContentDTO<GraficoAcumuladoDTO>> buscarRakesAcumulados() {
		try {
			return ResponseUtils.sucesso(service.buscarRakesAcumuladas());
		} catch (Exception e) {
			return ResponseUtils.falha(e);
		}
	}
	
	@GetMapping("/maos-acumuladas")
	public ResponseEntity<ContentDTO<GraficoAcumuladoMaosDTO>> buscarMaosAcumulados() {
		try {
			return ResponseUtils.sucesso(service.buscarMaosAcumuladas());
		} catch (Exception e) {
			return ResponseUtils.falha(e);
		}
	}
}
