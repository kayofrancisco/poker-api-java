package br.com.poker.controle.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.poker.controle.models.Clube;

public interface ClubeRepository extends JpaRepository<Clube, Integer> {

}
