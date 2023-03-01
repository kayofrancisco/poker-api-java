package br.com.poker.controle.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.poker.controle.models.Rake;

public interface RakeRepository extends JpaRepository<Rake, Integer> {
	Page<Rake> findByUsuarioId(Integer idUsuario, Pageable page);
	List<Rake> findAllByCriadoEmBetween(LocalDateTime inicio, LocalDateTime fim);
}
