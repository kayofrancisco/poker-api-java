package br.com.poker.controle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.poker.controle.models.Partida;

public interface PartidaRepository extends JpaRepository<Partida, Integer> {
	List<Partida> findByContaUsuarioIdOrderByDataInicioDesc(Integer idUsuario);
}
