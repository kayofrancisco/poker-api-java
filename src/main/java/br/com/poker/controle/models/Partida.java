package br.com.poker.controle.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.poker.controle.utils.UtilData;
import lombok.Data;

@Entity
@Data
@Table(name = "partidas", schema = "poker")
public class Partida {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "valor_ganho")
	private BigDecimal valor;

	@Column(name = "quantidade_big_blind")
	private BigDecimal quantidadeBigBlind;

	@Column(name = "quantidade_maos")
	private Integer quantidadeMaos;

	@Column(name = "quantidade_mesas")
	private Integer quantidadeMesas;

	@Column(name = "data_partida")
	@JsonFormat(pattern = UtilData.PATTERN_DATA)
	private LocalDateTime data;

	@ManyToOne
	@JoinColumn(name = "limite_id")
	private Limite limite;

	@ManyToOne
	@JoinColumn(name = "conta_id")
	private Conta conta;

	public Partida() {
	}

	public Partida(Integer id, BigDecimal valor, BigDecimal quantidadeBigBlind, Integer quantidadeMaos,
			LocalDateTime data, Limite limite, Conta conta) {
		super();
		this.id = id;
		this.valor = valor;
		this.quantidadeBigBlind = quantidadeBigBlind;
		this.quantidadeMaos = quantidadeMaos;
		this.data = data;
		this.limite = limite;
		this.conta = conta;
	}

}
