package com.proyecto.servicio;

import com.proyecto.modelo.Tarea;
import com.proyecto.modelo.Cuenta;
import com.proyecto.repositorio.TareaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TareaServicio {

    @Autowired
    private TareaRepositorio tareaRepositorio;

    public TareaServicio(TareaRepositorio tareaRepositorio) {
        this.tareaRepositorio = tareaRepositorio;
    }

    public List<Tarea> buscarPorTitulo(String titulo) {
        return tareaRepositorio.findByTituloContainingIgnoreCase(titulo);
    }

    public void guardarTarea(Tarea tarea) {
        tareaRepositorio.save(tarea);
    }

    public List<Tarea> listarTareas() {
        return tareaRepositorio.findAll();
    }

    public List<Tarea> obtenerTareasPorCuenta(Cuenta cuenta) {
        return tareaRepositorio.findByCuenta(cuenta);
    }

    public Tarea obtenerTareaPorId(Long id) {
        Optional<Tarea> tarea = tareaRepositorio.findById(id);
        return tarea.orElse(null); // Devuelve null si no se encuentra la tarea
    }

    public void eliminarTarea(Long id) {
        tareaRepositorio.deleteById(id);
    }
}
