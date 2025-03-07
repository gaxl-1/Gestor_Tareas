package com.proyecto.servicio;

import com.proyecto.modelo.Cuenta;
import com.proyecto.repositorio.CuentaRepositorio;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CuentaServicio implements UserDetailsService {

    private final CuentaRepositorio cuentaRepositorio;
    private final BCryptPasswordEncoder passwordEncoder;

    public CuentaServicio(CuentaRepositorio cuentaRepositorio, BCryptPasswordEncoder passwordEncoder) {
        this.cuentaRepositorio = cuentaRepositorio;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Cuenta> cuentaOpt = cuentaRepositorio.findByEmail(email);
        if (!cuentaOpt.isPresent()) {
            throw new UsernameNotFoundException("Cuenta no encontrada");
        }
        Cuenta cuenta = cuentaOpt.get();
        return org.springframework.security.core.userdetails.User
                .withUsername(cuenta.getEmail())
                .password(cuenta.getPassword())
                .roles("USER")
                .build();
    }

    public void registrarCuenta(String email, String password) {
        String passwordEncriptada = passwordEncoder.encode(password);
        Cuenta nuevaCuenta = new Cuenta(email, passwordEncriptada);
        cuentaRepositorio.save(nuevaCuenta);
    }
}
