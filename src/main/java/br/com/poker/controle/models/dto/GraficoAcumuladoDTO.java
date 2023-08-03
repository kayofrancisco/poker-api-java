package br.com.poker.controle.models.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GraficoAcumuladoDTO {
	private List<String> labels;
	private List<List<BigDecimal>> valores;
}
