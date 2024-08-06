package com.projeto.sistema.control;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.projeto.sistema.model.Cliente;
import com.projeto.sistema.repository.ICidadeRepositorio;
import com.projeto.sistema.repository.IClienteRepositorio;

@Controller
public class ClienteControle {
    
    @Autowired
    private IClienteRepositorio clienteRepositorio;

    @Autowired
    private ICidadeRepositorio cidadeRepositorio;

    @GetMapping("/cadastroCliente")
    public ModelAndView cadastrar(Cliente cliente) {
        ModelAndView mv = new ModelAndView("administrativo/clientes/cadastro");
        mv.addObject("cliente",cliente);
        mv.addObject("listaCidades", cidadeRepositorio.findAll());
        return mv;
    }

    @GetMapping("/listarCliente")
    public ModelAndView listar() {
        ModelAndView mv = new ModelAndView("/administrativo/clientes/lista");
        mv.addObject("listaClientes", clienteRepositorio.findAll());
        return mv;
    }

    @GetMapping("/editarCliente/{id}")
    public ModelAndView editar(@PathVariable("id") Long id) {
        Optional<Cliente> cliente = clienteRepositorio.findById(id);
        return cadastrar(cliente.get());
    }

    @GetMapping("/removerCliente/{id}")
    public ModelAndView remover(@PathVariable("id") Long id) {
        Optional<Cliente> cliente = clienteRepositorio.findById(id);
        clienteRepositorio.delete(cliente.get());
        return listar();
    }

    @PostMapping("/salvarCliente")
    public ModelAndView salvar(Cliente cliente, BindingResult result) {
        if(result.hasErrors()) {
            return cadastrar(cliente);
        }
        clienteRepositorio.saveAndFlush(cliente);
        return cadastrar(new Cliente());
    }
}