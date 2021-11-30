package br.com.poker.controle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.poker.controle.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
	List<Usuario> findByCpfOrEmail(String cpf, String email);
	List<Usuario> findByCpf(String cpf);
	List<Usuario> findByEmail(String email);
}
