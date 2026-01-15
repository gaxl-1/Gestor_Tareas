package com.proyecto.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PrincipalControlador {

    @GetMapping("/")
    public String paginaPrincipal() {
        return "index";
    }
}
