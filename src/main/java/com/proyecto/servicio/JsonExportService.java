package com.proyecto.servicio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.proyecto.modelo.Tarea;
import com.proyecto.repositorio.TareaRepositorio;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Servicio encargado de la exportación de datos a formato JSON.
 * Garantiza la persistencia técnica solicitada por el usuario fuera de la base
 * de datos SQL.
 */
@Service
public class JsonExportService {

    private final TareaRepositorio tareaRepositorio;
    private final ObjectMapper objectMapper;

    @Value("${app.json.export-path}")
    private String exportPath;

    public JsonExportService(TareaRepositorio tareaRepositorio) {
        this.tareaRepositorio = tareaRepositorio;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule()); // Soporte para Java 8 dates
    }

    /**
     * Recupera todas las tareas de la base de datos y las escribe en un archivo
     * JSON.
     * Utiliza Pretty Printer para facilitar la lectura del archivo resultante.
     */
    public void exportarTareasAJson() {
        try {
            List<Tarea> tareas = tareaRepositorio.findAll();
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(exportPath), tareas);
            System.out.println("Tareas exportadas correctamente a: " + exportPath);
        } catch (IOException e) {
            System.err.println("Error al exportar tareas a JSON: " + e.getMessage());
        }
    }
}
