package br.com.poker.controle.models.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GraficoDTO {
	private List<LocalDateTime> labels;
	private List<BigDecimal> valores;
}
