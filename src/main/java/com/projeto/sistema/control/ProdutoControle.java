package com.projeto.sistema.control;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.projeto.sistema.model.Produto;
import com.projeto.sistema.repository.IProdutoRespositorio;

@Controller
public class ProdutoControle {
    
    @Autowired
    private IProdutoRespositorio produtoRepositorio;

    @GetMapping("/cadastroProduto")
    public ModelAndView cadastrar(Produto produto) {
        ModelAndView mv = new ModelAndView("administrativo/produtos/cadastro");
        mv.addObject("produto",produto);
        return mv;
    }

    @GetMapping("/listarProduto")
    public ModelAndView listar() {
        ModelAndView mv = new ModelAndView("/administrativo/produtos/lista");
        mv.addObject("listaProdutos", produtoRepositorio.findAll());
        return mv;
    }

    @GetMapping("/editarProduto/{id}")
    public ModelAndView editar(@PathVariable("id") Long id) {
        Optional<Produto> produto = produtoRepositorio.findById(id);
        return cadastrar(produto.get());
    }

    @GetMapping("/removerProduto/{id}")
    public ModelAndView remover(@PathVariable("id") Long id) {
        Optional<Produto> produto = produtoRepositorio.findById(id);
        produtoRepositorio.delete(produto.get());
        return listar();
    }

    @PostMapping("/salvarProduto")
    public ModelAndView salvar(Produto produto, BindingResult result) {
        if(result.hasErrors()) {
            return cadastrar(produto);
        }
        produtoRepositorio.saveAndFlush(produto);
        return cadastrar(new Produto());
    }
}