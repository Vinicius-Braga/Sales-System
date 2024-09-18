package com.projeto.sistema.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.projeto.sistema.model.ItemEntrada;

public interface IItemEntradaRepositorio extends JpaRepository<ItemEntrada, Long>{
      @Query("SELECT E FROM ItemEntrada E WHERE E.entrada.id = ?1")
      List<ItemEntrada>buscarPorEntrada(Long id);

}