package br.com.poker.controle.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.poker.controle.models.Perfil;

public interface PerfilRepository extends JpaRepository<Perfil, Integer> {

}
