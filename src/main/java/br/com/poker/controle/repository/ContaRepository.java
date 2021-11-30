package br.com.poker.controle.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.poker.controle.models.Conta;

public interface ContaRepository extends JpaRepository<Conta, Integer> {
	Optional<Conta> findByNick(String nick);
}
