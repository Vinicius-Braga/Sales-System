package com.projeto.sistema.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.projeto.sistema.model.Entrada;
import com.projeto.sistema.model.ItemEntrada;
import com.projeto.sistema.model.Produto;
import com.projeto.sistema.repository.IEntradaRepositorio;
import com.projeto.sistema.repository.IFornecedorRepositorio;
import com.projeto.sistema.repository.IFuncionarioRepositorio;
import com.projeto.sistema.repository.IItemEntradaRepositorio;
import com.projeto.sistema.repository.IProdutoRespositorio;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Controller
public class EntradaControle {
    
    @Autowired
    private IEntradaRepositorio entradaRepositorio;

    @Autowired
    private IItemEntradaRepositorio ItemEntradaRepositorio;

    @Autowired
    private IProdutoRespositorio produtoRepositorio;

    @Autowired
    private IFuncionarioRepositorio funcionarioRepositorio;

    @Autowired
    private IFornecedorRepositorio fornecedorRepositorio;

    private List<ItemEntrada> listaItemEntrada = new ArrayList<ItemEntrada>();

    @GetMapping("/cadastroEntrada")
    public ModelAndView cadastrar(Entrada entrada, ItemEntrada itemEntrada) {
        ModelAndView mv = new ModelAndView("administrativo/entradas/cadastro");
        mv.addObject("entrada",entrada);
        mv.addObject("itemEntrada", itemEntrada);
        mv.addObject("listaItemEntradas", this.listaItemEntrada);
        mv.addObject("listaFuncionarios", funcionarioRepositorio.findAll());
        mv.addObject("listaFornecedores", fornecedorRepositorio.findAll());
        mv.addObject("listaProdutos", produtoRepositorio.findAll());
        return mv;
    }

    @GetMapping("/listarEntrada")
    public ModelAndView listar() {
        ModelAndView mv = new ModelAndView("/administrativo/entradas/lista");
        mv.addObject("listarEntrada", entradaRepositorio.findAll());
        return mv;
    }

    @GetMapping("/editarEntrada/{id}")
    public ModelAndView editar(@PathVariable("id") Long id) {
        Optional<Entrada> entrada = entradaRepositorio.findById(id);
        this.listaItemEntrada = ItemEntradaRepositorio.buscarPorEntrada(id);

        return cadastrar(entrada.get(), new ItemEntrada());
    }

    // @GetMapping("/removerEntrada/{id}")
    // public ModelAndView remover(@PathVariable("id") Long id) {
    //     Optional<Entrada> entrada = entradaRepositorio.findById(id);
    //     entradaRepositorio.delete(entrada.get());
    //     return listar();
    // }

    @PostMapping("/salvarEntrada")
    public ModelAndView salvar(String acao, Entrada entrada, ItemEntrada itemEntrada,  BindingResult result) {
        if(result.hasErrors()) {
            return cadastrar(entrada, itemEntrada);
        }

        if(acao.equals("itens")) {
            this.listaItemEntrada.add(itemEntrada);
            entrada.setValorTotal(entrada.getValorTotal() + itemEntrada.getValor());
            entrada.setQuantidadeTotal(entrada.getQuantidadeTotal() + itemEntrada.getQuantidade());

        } else if(acao.equals("salvar")) {
            entradaRepositorio.saveAndFlush(entrada);

            for(ItemEntrada it: listaItemEntrada) {
                it.setEntrada(entrada);
                ItemEntradaRepositorio.saveAndFlush(it);

                Optional<Produto> prod = produtoRepositorio.findById(((Produto) it.getProduto()).getId());
                Produto produto = prod.get();
                produto.setEstoque(produto.getEstoque() + it.getQuantidade());
                produto.setPrecoVenda(it.getValor());
                produto.setPrecoCusto(it.getValorCusto());
                produtoRepositorio.saveAndFlush(produto);

                this.listaItemEntrada = new ArrayList<>();
            }
        }

        return cadastrar(new Entrada(), new ItemEntrada());
    }
}