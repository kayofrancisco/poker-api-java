package br.com.poker.controle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.poker.controle.models.Conta;
import br.com.poker.controle.models.Rake;

public interface RakeRepository extends JpaRepository<Rake, Integer> {
	List<Rake> findByConta(Conta conta);
	List<Rake> findByContaUsuarioIdOrderByCriadoEmDesc(Integer idUsuario);
}
