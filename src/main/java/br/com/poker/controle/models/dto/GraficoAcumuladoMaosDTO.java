package br.com.poker.controle.models.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GraficoAcumuladoMaosDTO {
	private List<String> labels;
	private List<List<Integer>> valores;
}
