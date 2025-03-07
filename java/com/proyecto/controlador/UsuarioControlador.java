package com.proyecto.controlador;

import com.proyecto.modelo.Cuenta;
import com.proyecto.modelo.Usuario;
import com.proyecto.repositorio.UsuarioRepositorio;
import com.proyecto.repositorio.CuentaRepositorio;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/usuarios")
public class UsuarioControlador {

    private final UsuarioRepositorio usuarioRepositorio;
    private final CuentaRepositorio cuentaRepositorio;

    public UsuarioControlador(UsuarioRepositorio usuarioRepositorio, CuentaRepositorio cuentaRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.cuentaRepositorio = cuentaRepositorio;
    }

    @GetMapping
    public String listarUsuarios(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        Cuenta cuenta = cuentaRepositorio.findByEmail(userDetails.getUsername()).orElseThrow();
        List<Usuario> usuarios = usuarioRepositorio.findByCuenta(cuenta);
        model.addAttribute("usuarios", usuarios);
        return "usuarios/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuarios/formulario";
    }

    @PostMapping("/guardar")
    public String guardarUsuario(@AuthenticationPrincipal UserDetails userDetails, @ModelAttribute Usuario usuario) {
        Cuenta cuenta = cuentaRepositorio.findByEmail(userDetails.getUsername()).orElseThrow();
        usuario.setCuenta(cuenta);
        usuarioRepositorio.save(usuario);
        return "redirect:/usuarios";
    }

    @GetMapping("/editar/{id}")
    public String editarUsuario(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioRepositorio.findById(id).orElseThrow(() -> new IllegalArgumentException("ID no válido"));
        model.addAttribute("usuario", usuario);
        return "usuarios/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id) {
        usuarioRepositorio.deleteById(id);
        return "redirect:/usuarios";
    }
}
