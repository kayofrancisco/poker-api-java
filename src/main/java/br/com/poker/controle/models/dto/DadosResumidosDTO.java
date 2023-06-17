package br.com.poker.controle.models.dto;

import java.math.BigDecimal;

import br.com.poker.controle.models.Limite;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DadosResumidosDTO {
	private Limite limite;
	private Long secundosJogados;
	private Integer maosJogadas;
	private Integer totalSessoes;
	private BigDecimal lucroSemRake;
	private BigDecimal rake;
	private BigDecimal buyinsUp;
	private BigDecimal winrateMaos;
	private BigDecimal winrateHoras;
	private BigDecimal lucro;
}
