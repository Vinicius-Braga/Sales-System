package com.projeto.sistema.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.sistema.model.Funcionario;

public interface IFuncionarioRepositorio extends JpaRepository<Funcionario, Long>{


}