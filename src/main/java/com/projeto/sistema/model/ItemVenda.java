package com.projeto.sistema.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ItemVenda")
public class ItemVenda implements Serializable{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private Double quantidade;
    private Double valor;
    private Double subTotal;

    @ManyToOne
    private Venda venda;

    @ManyToOne
    private Produto produto;

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public Object getProduto() {
        return produto;
    }

    public Double getQuantidade() {
        return quantidade;
    }

}
