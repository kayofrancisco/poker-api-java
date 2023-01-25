package br.com.poker.controle.models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.poker.controle.utils.UtilData;
import lombok.Data;

@Entity
@Data
@Table(name = "clubes", schema = "poker")
public class Clube {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "nome")
	private String nome;

	@Column(name = "criado_em")
	@JsonFormat(pattern =  UtilData.PATTERN_DATA)
	private LocalDateTime criadoEm;

	@Column(name = "ultima_atualizacao")
	@JsonFormat(pattern =  UtilData.PATTERN_DATA)
	private LocalDateTime ultimaAtualizacao;
	
	@ManyToOne
    @JoinColumn(name="usuario_id")
    private Usuario usuario;
	
	@PrePersist
	public void prePersist() {
		setUltimaAtualizacao(LocalDateTime.now());
		setCriadoEm(LocalDateTime.now());
	}
	
	@PreUpdate
	public void preUpdate() {
		setUltimaAtualizacao(LocalDateTime.now());
	}
	
	public Clube() {}

	public Clube(Integer id, String nome, LocalDateTime criadoEm, LocalDateTime ultimaAtualizacao) {
		super();
		this.id = id;
		this.nome = nome;
		this.criadoEm = criadoEm;
		this.ultimaAtualizacao = ultimaAtualizacao;
	}
}
