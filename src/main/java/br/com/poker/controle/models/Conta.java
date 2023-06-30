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
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.poker.controle.utils.UtilData;
import lombok.Data;

@Entity
@Data
@Table(name = "contas")
public class Conta {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "nick")
	private String nick;
	
	@Column(name = "ativo")
	private Boolean ativo;
	
	@Column(name = "plataforma")
	private String plataforma;

	@Column(name = "criado_em")
	@JsonFormat(pattern =  UtilData.PATTERN_DATA)
	private LocalDateTime criadoEm;
	
	@ManyToOne
    @JoinColumn(name="usuario_id")
    private Usuario usuario;

	@PrePersist
	public void prePersist() {
		setCriadoEm(LocalDateTime.now());
		setAtivo(Boolean.TRUE);
	}
	
	public Conta() {}

	public Conta(Integer id, String nick, Usuario usuario) {
		super();
		this.id = id;
		this.nick = nick;
		this.usuario = usuario;
	}
}
