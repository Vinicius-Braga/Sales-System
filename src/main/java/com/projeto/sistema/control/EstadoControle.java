package com.projeto.sistema.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.projeto.sistema.model.Estado;
import com.projeto.sistema.repository.IEstadoRepositorio;

@Controller
public class EstadoControle {
    
    @Autowired
    private IEstadoRepositorio estadoRepositorio;

    @GetMapping("/cadastroEstado")
    public ModelAndView cadastrar(Estado estado) {
        ModelAndView mv = new ModelAndView("administrativo/estados/cadastro");
        mv.addObject("estado",estado);
        return mv;
    }

    @PostMapping("/salvarEstado")
    public ModelAndView salvar(Estado estado, BindingResult result) {
        if(result.hasErrors()) {
            return cadastrar(estado);
        }
        estadoRepositorio.saveAndFlush(estado);
        return cadastrar(new Estado());
    }
}
