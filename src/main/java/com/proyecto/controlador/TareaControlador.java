package com.proyecto.controlador;

import com.proyecto.modelo.Cuenta;
import com.proyecto.modelo.Tarea;
import com.proyecto.modelo.Usuario;
import com.proyecto.servicio.TareaServicio;
import com.proyecto.repositorio.CuentaRepositorio;
import com.proyecto.repositorio.UsuarioRepositorio;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tareas")
public class TareaControlador {

    private final TareaServicio tareaServicio;
    private final CuentaRepositorio cuentaRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;

    public TareaControlador(TareaServicio tareaServicio, CuentaRepositorio cuentaRepositorio,
            UsuarioRepositorio usuarioRepositorio) {
        this.tareaServicio = tareaServicio;
        this.cuentaRepositorio = cuentaRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @GetMapping
    public String listarTareas(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        Cuenta cuenta = cuentaRepositorio.findByEmail(userDetails.getUsername()).orElseThrow();
        List<Tarea> tareas = tareaServicio.obtenerTareasPorCuenta(cuenta);
        model.addAttribute("tareas", tareas);
        return "tareas/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioTarea(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        Cuenta cuenta = cuentaRepositorio.findByEmail(userDetails.getUsername()).orElseThrow();
        List<Usuario> usuarios = usuarioRepositorio.findByCuenta(cuenta);

        model.addAttribute("tarea", new Tarea());
        model.addAttribute("usuarios", usuarios);
        return "tareas/formulario";
    }

    @PostMapping("/guardar")
    public String guardarTarea(@AuthenticationPrincipal UserDetails userDetails,
            @ModelAttribute Tarea tarea,
            @RequestParam Long usuarioId) {
        Cuenta cuenta = cuentaRepositorio.findByEmail(userDetails.getUsername()).orElseThrow();
        Usuario usuario = usuarioRepositorio.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        tarea.setCuenta(cuenta);
        tarea.setUsuario(usuario);
        tareaServicio.guardarTarea(tarea);

        return "redirect:/tareas";
    }

    @GetMapping("/editar/{id}")
    public String editarTarea(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Cuenta cuenta = cuentaRepositorio.findByEmail(userDetails.getUsername()).orElseThrow();
        Tarea tarea = tareaServicio.obtenerTareaPorId(id);

        if (tarea == null || !tarea.getCuenta().getId().equals(cuenta.getId())) {
            throw new IllegalArgumentException("Acceso denegado o tarea no encontrada");
        }

        List<Usuario> usuarios = usuarioRepositorio.findByCuenta(cuenta);
        model.addAttribute("tarea", tarea);
        model.addAttribute("usuarios", usuarios);
        return "tareas/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarTarea(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        Cuenta cuenta = cuentaRepositorio.findByEmail(userDetails.getUsername()).orElseThrow();
        Tarea tarea = tareaServicio.obtenerTareaPorId(id);

        if (tarea == null || !tarea.getCuenta().getId().equals(cuenta.getId())) {
            throw new IllegalArgumentException("Acceso denegado o tarea no encontrada");
        }

        tareaServicio.eliminarTarea(id);
        return "redirect:/tareas";
    }
}
