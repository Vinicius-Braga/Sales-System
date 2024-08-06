package com.projeto.sistema.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.sistema.model.Cliente;

public interface IClienteRepositorio extends JpaRepository<Cliente, Long>{


}