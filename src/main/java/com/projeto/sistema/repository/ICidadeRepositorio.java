package com.projeto.sistema.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.sistema.model.Cidade;

public interface ICidadeRepositorio extends JpaRepository<Cidade, Long>{


}