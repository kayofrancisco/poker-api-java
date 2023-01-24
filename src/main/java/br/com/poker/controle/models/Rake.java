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
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.poker.controle.utils.UtilData;
import lombok.Data;

@Entity
@Data
@Table(name = "rakes", schema = "poker")
public class Rake {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "valor")
	private BigDecimal valor;

	@Column(name = "criado_em")
	@JsonFormat(pattern =  UtilData.PATTERN_DATA)
	private LocalDateTime criadoEm;
	
	@ManyToOne
    @JoinColumn(name="clube_id")
    private Clube clube;

	@PrePersist
	public void prePersist() {
		setCriadoEm(LocalDateTime.now());
	}
	
	public Rake() {}

	public Rake(Integer id, String nome, LocalDateTime criadoEm, LocalDateTime ultimaAtualizacao) {
		super();
		this.id = id;
		this.criadoEm = criadoEm;
	}
}
