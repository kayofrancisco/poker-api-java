package br.com.poker.controle.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.poker.controle.models.Limite;

public interface LimiteRepository extends JpaRepository<Limite, Integer> {

}
