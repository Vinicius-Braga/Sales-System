package com.projeto.sistema.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.sistema.model.Produto;

public interface IProdutoRespositorio extends JpaRepository<Produto, Long>{


}