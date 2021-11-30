package br.com.poker.controle.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.poker.controle.models.Partida;

public interface PartidaRepository extends JpaRepository<Partida, Integer> {
}
