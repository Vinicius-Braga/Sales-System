package com.projeto.sistema.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class principalControle {

    @GetMapping("administrativo")
    public String acessarPrincipal() {
        return "administrativo/home";
    }

}
