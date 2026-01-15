package com.proyecto.servicio;

import com.proyecto.modelo.Tarea;
import com.proyecto.modelo.Cuenta;
import com.proyecto.repositorio.TareaRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio de lógica de negocio para la gestión de tareas.
 * Coordina la persistencia en base de datos y la exportación sincronizada en
 * JSON.
 */
@Service
public class TareaServicio {

    private final TareaRepositorio tareaRepositorio;
    private final JsonExportService jsonExportService;

    public TareaServicio(TareaRepositorio tareaRepositorio, JsonExportService jsonExportService) {
        this.tareaRepositorio = tareaRepositorio;
        this.jsonExportService = jsonExportService;
    }

    public List<Tarea> buscarPorTitulo(String titulo) {
        return tareaRepositorio.findByTituloContainingIgnoreCase(titulo);
    }

    /**
     * Guarda una tarea y lanza la sincronización con el archivo JSON.
     */
    public void guardarTarea(Tarea tarea) {
        tareaRepositorio.save(tarea);
        jsonExportService.exportarTareasAJson();
    }

    public List<Tarea> listarTareas() {
        return tareaRepositorio.findAll();
    }

    public List<Tarea> obtenerTareasPorCuenta(Cuenta cuenta) {
        return tareaRepositorio.findByCuenta(cuenta);
    }

    public Tarea obtenerTareaPorId(Long id) {
        Optional<Tarea> tarea = tareaRepositorio.findById(id);
        return tarea.orElse(null);
    }

    /**
     * Elimina una tarea y lanza la sincronización con el archivo JSON.
     */
    public void eliminarTarea(Long id) {
        tareaRepositorio.deleteById(id);
        jsonExportService.exportarTareasAJson();
    }
}
