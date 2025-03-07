package com.proyecto.servicio;

import com.proyecto.modelo.Usuario;
import com.proyecto.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    public List<Usuario> obtenerTodosUsuarios() {
        return usuarioRepositorio.findAll();
    }

    public Usuario obtenerUsuarioPorId(Long id) {
        Optional<Usuario> usuario = usuarioRepositorio.findById(id);
        return usuario.orElse(null); // Devuelve null si no se encuentra el usuario
    }

    public void agregarUsuario(Usuario usuario) {
        usuarioRepositorio.save(usuario);
    }

    public void actualizarUsuario(Usuario usuario) {
        usuarioRepositorio.save(usuario);
    }

    public void eliminarUsuario(Long id) {
        usuarioRepositorio.deleteById(id);
    }
}
