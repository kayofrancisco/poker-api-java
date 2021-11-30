package br.com.poker.controle.models;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "limites", schema = "poker")
public class Limite {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "small_blind")
	private BigDecimal smallBlind;

	@Column(name = "big_blind")
	private BigDecimal bigBlind;

	public Limite() {
	}

	public Limite(Integer id, BigDecimal smallBlind, BigDecimal bigBlind) {
		super();
		this.id = id;
		this.smallBlind = smallBlind;
		this.bigBlind = bigBlind;
	}
}
