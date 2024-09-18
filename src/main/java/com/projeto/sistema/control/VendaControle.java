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

import com.projeto.sistema.model.ItemVenda;
import com.projeto.sistema.model.Produto;
import com.projeto.sistema.model.Venda;
import com.projeto.sistema.repository.IClienteRepositorio;
import com.projeto.sistema.repository.IFuncionarioRepositorio;
import com.projeto.sistema.repository.IItemVendaRepositorio;
import com.projeto.sistema.repository.IProdutoRespositorio;
import com.projeto.sistema.repository.IVendaRepositorio;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Controller
public class VendaControle {
    
    @Autowired
    private IVendaRepositorio vendaRepositorio;

    @Autowired
    private IItemVendaRepositorio ItemVendaRepositorio;

    @Autowired
    private IProdutoRespositorio produtoRepositorio;

    @Autowired
    private IFuncionarioRepositorio funcionarioRepositorio;

    @Autowired
    private IClienteRepositorio clienteRepositorio;

    private List<ItemVenda> listaItemVenda = new ArrayList<ItemVenda>();

    @GetMapping("/cadastroVenda")
    public ModelAndView cadastrar(Venda venda, ItemVenda itemVenda) {
        ModelAndView mv = new ModelAndView("administrativo/vendas/cadastro");
        mv.addObject("venda",venda);
        mv.addObject("itemVenda", itemVenda);
        mv.addObject("listaItemVendas", this.listaItemVenda);
        mv.addObject("listaFuncionarios", funcionarioRepositorio.findAll());
        mv.addObject("listaFornecedores", clienteRepositorio.findAll());
        mv.addObject("listaProdutos", produtoRepositorio.findAll());
        return mv;
    }

    @GetMapping("/listarVenda")
    public ModelAndView listar() {
        ModelAndView mv = new ModelAndView("/administrativo/vendas/lista");
        mv.addObject("listarVenda", vendaRepositorio.findAll());
        return mv;
    }

    @GetMapping("/editarVenda/{id}")
    public ModelAndView editar(@PathVariable("id") Long id) {
        Optional<Venda> venda = vendaRepositorio.findById(id);
        this.listaItemVenda = ItemVendaRepositorio.buscarPorVenda(id);

        return cadastrar(venda.get(), new ItemVenda());
    }

    // @GetMapping("/removerVenda/{id}")
    // public ModelAndView remover(@PathVariable("id") Long id) {
    //     Optional<Venda> venda = vendaRepositorio.findById(id);
    //     vendaRepositorio.delete(venda.get());
    //     return listar();
    // }

    @PostMapping("/salvarVenda")
    public ModelAndView salvar(String acao, Venda venda, ItemVenda itemVenda,  BindingResult result) {
        if(result.hasErrors()) {
            return cadastrar(venda, itemVenda);
        }

        if(acao.equals("itens")) {
            this.listaItemVenda.add(itemVenda);
            venda.setValorTotal(venda.getValorTotal() + itemVenda.getValor());
            venda.setQuantidadeTotal(venda.getQuantidadeTotal() + itemVenda.getQuantidade());

        } else if(acao.equals("salvar")) {
            vendaRepositorio.saveAndFlush(venda);

            for(ItemVenda it: listaItemVenda) {
                it.setVenda(venda);
                ItemVendaRepositorio.saveAndFlush(it);

                Optional<Produto> prod = produtoRepositorio.findById(((Produto) it.getProduto()).getId());
                Produto produto = prod.get();
                produto.setEstoque(produto.getEstoque() + it.getQuantidade());
                produto.setPrecoVenda(it.getValor());
                // produto.setPrecoCusto(it.getValorCusto());
                produtoRepositorio.saveAndFlush(produto);

                this.listaItemVenda = new ArrayList<>();
            }
        }

        return cadastrar(new Venda(), new ItemVenda());
    }
}