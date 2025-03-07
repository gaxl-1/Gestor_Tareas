package com.proyecto.repositorio;

import com.proyecto.modelo.Usuario;
import com.proyecto.modelo.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByNombre(String nombre);
    List<Usuario> findByCuenta(Cuenta cuenta);
}
