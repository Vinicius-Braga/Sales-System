package com.projeto.sistema.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.sistema.model.Venda;

public interface IVendaRepositorio extends JpaRepository<Venda, Long>{


}