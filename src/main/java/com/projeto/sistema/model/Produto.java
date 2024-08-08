package com.projeto.sistema.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Produto")
public class Produto implements Serializable{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String nome;
    private String codigoBarras;
    private String unidadeMedida;
    private Double estoque;
    private Double precoCusto;
    private Double precoVenda;
    private Double lucro;
    private Double margemLucro;


}
