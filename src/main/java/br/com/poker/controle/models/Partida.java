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
	private Integer quantidadeBigBlind;

	@Column(name = "quantidade_maos")
	private Integer quantidadeMaos;
	
	@Column(name = "criado_em")
	@JsonFormat(pattern =  UtilData.PATTERN_DATA)
	private LocalDateTime criadoEm;

	@ManyToOne
    @JoinColumn(name="limite_id")
    private Limite limite;

	@ManyToOne
	@JoinColumn(name="conta_id")
	private Conta conta;
}
