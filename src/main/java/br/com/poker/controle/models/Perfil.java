package br.com.poker.controle.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.poker.controle.utils.UtilConstantes;
import lombok.Data;

@Entity
@Data
@Table(name = "perfis")
public class Perfil {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "descricao")
	private String descricao;

	@Column(name = "sigla")
	private String sigla;
	
	public Perfil() {}

	public Perfil(Integer id, String descricao, String sigla) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.sigla = sigla;
	}
	
	public static Perfil perfilComum() {
		Perfil perfil = new Perfil();
		perfil.setId(UtilConstantes.ID_PERFIL_COMUM);
		
		return perfil;
	}
	
	public static Perfil perfilAdm() {
		Perfil perfil = new Perfil();
		perfil.setId(UtilConstantes.ID_PERFIL_ADM);
		
		return perfil;
	}
}
