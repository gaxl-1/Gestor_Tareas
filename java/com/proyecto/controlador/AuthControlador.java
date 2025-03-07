package com.proyecto.controlador;

import com.proyecto.servicio.CuentaServicio;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthControlador {

    private final CuentaServicio cuentaServicio;

    public AuthControlador(CuentaServicio cuentaServicio) {
        this.cuentaServicio = cuentaServicio;
    }

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    @GetMapping("/registro")
    public String mostrarRegistro() {
        return "registro";
    }

    @PostMapping("/registro")
    public String registrarCuenta(@RequestParam String email, @RequestParam String password, Model model) {
        cuentaServicio.registrarCuenta(email, password);
        model.addAttribute("mensaje", "Cuenta creada con éxito. Inicia sesión.");
        return "login";
    }
}
