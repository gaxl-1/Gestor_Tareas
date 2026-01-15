package com.proyecto.controlador;

import com.proyecto.modelo.Cuenta;
import com.proyecto.modelo.Tarea;
import com.proyecto.modelo.Usuario;
import com.proyecto.repositorio.CuentaRepositorio;
import com.proyecto.repositorio.TareaRepositorio;
import com.proyecto.repositorio.UsuarioRepositorio;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class BuscarControlador {

    private final UsuarioRepositorio usuarioRepositorio;
    private final TareaRepositorio tareaRepositorio;
    private final CuentaRepositorio cuentaRepositorio;

    public BuscarControlador(UsuarioRepositorio usuarioRepositorio,
            TareaRepositorio tareaRepositorio,
            CuentaRepositorio cuentaRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.tareaRepositorio = tareaRepositorio;
        this.cuentaRepositorio = cuentaRepositorio;
    }

    @GetMapping("/buscar")
    public String mostrarBusqueda() {
        return "buscar";
    }

    @GetMapping("/buscar/usuario")
    public String buscarUsuario(@RequestParam String nombre,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model) {
        Cuenta cuenta = cuentaRepositorio.findByEmail(userDetails.getUsername()).orElseThrow();

        Optional<Usuario> usuarioOpt = usuarioRepositorio.findByNombre(nombre);

        if (usuarioOpt.isPresent() && usuarioOpt.get().getCuenta().equals(cuenta)) {
            Usuario usuario = usuarioOpt.get();
            List<Tarea> tareas = tareaRepositorio.findByUsuario(usuario);
            model.addAttribute("usuario", usuario);
            model.addAttribute("tareas", tareas);
        } else {
            model.addAttribute("mensaje", "Usuario no encontrado");
        }

        return "buscar";
    }

    @GetMapping("/buscar/tarea")
    public String buscarTarea(@RequestParam String titulo,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model) {
        Cuenta cuenta = cuentaRepositorio.findByEmail(userDetails.getUsername()).orElseThrow();

        List<Tarea> tareas = tareaRepositorio.findByTituloContainingIgnoreCase(titulo);
        List<Tarea> tareasFiltradas = tareas.stream()
                .filter(t -> t.getCuenta().equals(cuenta))
                .toList();

        model.addAttribute("tareas", tareasFiltradas);
        return "buscar";
    }
}
