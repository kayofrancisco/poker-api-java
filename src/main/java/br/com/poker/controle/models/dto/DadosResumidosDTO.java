package br.com.poker.controle.models.dto;

import java.math.BigDecimal;

import br.com.poker.controle.models.Limite;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DadosResumidosDTO {
	private Limite limite;
	private Long horasJogadas;
	private Integer maosJogadas;
	private BigDecimal ganhoSemRake;
	private BigDecimal rake;
	private BigDecimal buyinsUp;
	private BigDecimal winrateMaos;
	private BigDecimal winrateHoras;
	private BigDecimal total;
}
