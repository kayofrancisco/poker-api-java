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

	@Column(name = "quantidade_maos_inicio")
	private Integer quantidadeMaosInicio;
	
	@Column(name = "quantidade_maos_fim")
	private Integer quantidadeMaosFim;
	
	@Column(name = "online")
	private Boolean online;
	
	@Column(name = "fichas_iniciais")
	private BigDecimal fichasIniciais;
	
	@Column(name = "fichas_finais")
	private BigDecimal fichasFinais;

	@Column(name = "data_inicio")
	@JsonFormat(pattern = UtilData.PATTERN_DATA)
	private LocalDateTime dataInicio;

	@Column(name = "data_fim")
	@JsonFormat(pattern = UtilData.PATTERN_DATA)
	private LocalDateTime dataFim;

	@ManyToOne
	@JoinColumn(name = "limite_id")
	private Limite limite;

	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;

	public Partida() {
	}

	public Partida(Integer id, BigDecimal valor, BigDecimal quantidadeBigBlind, Integer quantidadeMaosInicio, Integer quantidadeMaosFim,
			LocalDateTime dataInicio, LocalDateTime dataFim, Limite limite, Usuario usuario) {
		super();
		this.id = id;
		this.valor = valor;
		this.quantidadeBigBlind = quantidadeBigBlind;
		this.quantidadeMaosInicio = quantidadeMaosInicio;
		this.quantidadeMaosFim = quantidadeMaosFim;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.limite = limite;
		this.usuario = usuario;
	}

}
