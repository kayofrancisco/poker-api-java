package br.com.poker.controle.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.poker.controle.models.Conta;
import br.com.poker.controle.models.Usuario;

public interface ContaRepository extends JpaRepository<Conta, Integer> {
	Optional<Conta> findByNick(String nick);
	List<Conta> findByUsuario(Usuario usuario);
}
