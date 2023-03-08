package br.com.poker.controle.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.poker.controle.models.Partida;

public interface PartidaRepository extends JpaRepository<Partida, Integer> {
	Page<Partida> findByUsuarioId(Integer idUsuario, Pageable page);

	List<Partida> findAllByDataInicioBetweenAndUsuarioId(LocalDateTime inicio, LocalDateTime fim, Integer idUsuario);
}
