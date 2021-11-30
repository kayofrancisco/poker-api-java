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
@Table(name = "usuarios", schema = "poker")
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "nome")
	private String nome;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "cpf")
	private String cpf;
	
	@Column(name = "senha")
	private String senha;
	
	@Column(name = "ativo")
	private Boolean ativo;

	@Column(name = "criado_em", nullable = false)
	@JsonFormat(pattern =  UtilData.PATTERN_DATA)
	private LocalDateTime criadoEm;

	@Column(name = "ultima_atualizacao", nullable = false)
	@JsonFormat(pattern =  UtilData.PATTERN_DATA)
	private LocalDateTime ultimaAtualizacao;
	
	@ManyToOne
    @JoinColumn(name="id_perfil")
    private Perfil perfil;
	
	@PrePersist
	public void prePersist() {
		setUltimaAtualizacao(LocalDateTime.now());
		setCriadoEm(LocalDateTime.now());
		setAtivo(Boolean.TRUE);
	}
	
	@PreUpdate
	public void preUpdate() {
		setUltimaAtualizacao(LocalDateTime.now());
	}
}
