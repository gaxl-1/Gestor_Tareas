package com.proyecto.repositorio;

import com.proyecto.modelo.Cuenta;
import com.proyecto.modelo.Tarea;
import com.proyecto.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
 
public interface TareaRepositorio extends JpaRepository<Tarea, Long> {
    List<Tarea> findByUsuario(Usuario usuario);
    List<Tarea> findByUsuarioId(Long usuarioId);
    List<Tarea> findByTituloContainingIgnoreCase(String titulo);
    List<Tarea> findByCuenta(Cuenta cuenta);
}
